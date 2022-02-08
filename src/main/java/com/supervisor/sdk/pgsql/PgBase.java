package com.supervisor.sdk.pgsql;

import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.BaseScheme;
import com.supervisor.sdk.datasource.BaseStatementQueryable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.datasource.ResultStatement;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.sdk.metadata.FieldMetadata;
import com.supervisor.sdk.pgsql.statement.PgColumnExistsStatement;
import com.supervisor.sdk.pgsql.statement.PgCreateColumnStatement;
import com.supervisor.sdk.pgsql.statement.PgStatementUpdatable;
import com.supervisor.sdk.pgsql.statement.PgTableExistsStatement;
import com.supervisor.sdk.pgsql.statement.PgUpdateSchemaStatement;
import com.supervisor.sdk.utils.logging.Logger;
import com.supervisor.sdk.utils.logging.MLogger;
import com.supervisor.sdk.websockets.WebSocketServer;
import org.takes.Request;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class PgBase implements Base {
	
	private static final Logger logger = new MLogger(PgBase.class);
	
	private final DataSource source;
	private final List<Class<? extends Recordable>> clazzsRegistered;
	private final BaseScheme scheme;
	private final WebSocketServer wsServer;
	private static final ThreadLocal<Connection> connectionContext = new ThreadLocal<>();
	private static final ThreadLocal<UUID> userIdContext = new ThreadLocal<>();
	private static final ThreadLocal<Boolean> inTransactionContext = new ThreadLocal<>();

	public PgBase(final DataSource source, final WebSocketServer wsServer) {
		this.source = source;
		this.clazzsRegistered = new ArrayList<>();
		this.scheme = new PgBaseScheme();
		this.wsServer = wsServer;
	}
	
	@Override
	public void start(boolean inTransaction, Request request) throws IOException {
		
		final UUID userId;
        
		final Identity identity = new RqAuth(request).identity();
        if (identity.equals(Identity.ANONYMOUS)) {
        	userId = User.ANONYMOUS_ID;
        }else {
        	Map<String, String> props = identity.properties();
        	userId = UUID.fromString(props.get("id"));
        }

        start(inTransaction, userId);
	}
	
	@Override
	public void start(boolean inTransaction, UUID currentUserId) throws IOException {
		
		changeUser(currentUserId);
		inTransactionContext.set(inTransaction);	
		
		if(inTransaction) {
			
			if(connectionContext.get() != null)
				throw new IllegalArgumentException("New database transaction : an connection is currently opened for this thread !");
			
			newConnection();
			
			String msg = String.format("------------------------------ Start new transaction ID thread (%d) ---------------------------------", Thread.currentThread().getId());
			logger.debug(msg);
		}		
	}
	
	private void newConnection() throws IOException {
		try {
			Connection connection = source.getConnection();
			
			if(withinTransaction()) {
				connection.setAutoCommit(false);
			}
			
			connectionContext.set(connection);			
		} catch (SQLException e) {
			logger.error(e); 
			throw new IOException("Le temps de réponse maximum a anormalement été dépassé. Veuillez ressayer dans quelques instants SVP.");
		}
	}

	@Override
	public <A1 extends Recordable> void createSchemeOf(final Class<A1> clazz) throws IOException {
		
		final String domainName = scheme.nameOf(clazz); 
	    
		if(PgBaseScheme.isPartial(clazz)) {
	    	Class<?> coClazz = PgBaseScheme.comodel(clazz);
	    	if(! new PgTableExistsStatement(this, domainName).exists()) {
				// 1 - Générer la table
				new PgUpdateSchemaStatement(this, scheme.scriptOf(coClazz)).execute();
			}
	    	
	    	List<FieldMetadata> fields = scheme.ownFieldsOf(clazz);
			for (FieldMetadata f : fields) {
				if(! new PgColumnExistsStatement(this, f).exists()) {
					// 1 - Créer la colonne
					new PgCreateColumnStatement(this, f).execute();
				}				
			}
	    } else
	    {
	    	if(! new PgTableExistsStatement(this, domainName).exists()) {
				// 1 - Générer la table
				new PgUpdateSchemaStatement(this, scheme.scriptOf(clazz)).execute();					
			}
	    	
			// 2 - Générer les données de configuration
			new SettingData(scheme, clazz, this).execute();
			
			// 3 - Générer les données de demonstration
			new DemoData(scheme, clazz, this).execute();
	    }		
	}
	
	@Override
	public void commit() throws IOException {
		
		if(!withinTransaction())
			throw new IllegalArgumentException("Commit : you are not in transaction !");
			
		try {
			Connection connection = connection();				
			connection.commit();
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void createScheme() throws IOException {
		for (Class<? extends Recordable> clazz : clazzsRegistered) {
			createSchemeOf(clazz);
		}
		
		clazzsRegistered.clear();
	}

	@Override
	public <A1 extends Recordable> void register(final Class<A1> clazz) throws IOException {
		clazzsRegistered.add(clazz);	
	}

	@Override
	public <A1 extends Recordable> RecordSet<A1> select(Class<A1> clazz) throws IOException {
		return select(clazz, new TableImpl(clazz).name()); 
	}

	@Override
	public <A1 extends Recordable> Record<A1> select(Class<A1> clazz, UUID id) throws IOException {
		return new PgRecord<>(this, scheme, clazz, id);
	}

	@Override
	public List<ResultStatement> query(String statement, List<Object> parameters) throws IOException {
		return new BaseStatementQueryable(this, statement, parameters).execute();
	}

	@Override
	public <A1 extends Recordable> RecordSet<A1> select(Class<A1> clazz, String viewScript) throws IOException {
		return new PgRecordSet<>(this, scheme, clazz, new TableImpl(viewScript));
	}

	@Override
	public WebSocketServer wsServer() {
		return wsServer;
	}

	@Override
	public void update(String statement, List<Object> parameters) throws IOException {
		new PgStatementUpdatable(this, statement, parameters).execute();
	}

	@Override
	public Connection connection() throws IOException {		
			
		if(connectionContext.get() == null) {
			if(withinTransaction())
				throw new IllegalArgumentException("Connection has been closed !");
			else {
				newConnection();
			}
		}

		return connectionContext.get();
	}

	@Override
	public void rollback() throws IOException {
		
		if(!withinTransaction())
			throw new IllegalArgumentException("Rollback : you are not in transaction !");
		
		try {
			Connection connection = connection();
			connection.rollback();
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void close() throws IOException {
		try {
			connection().close();		
			connectionContext.remove();	
			inTransactionContext.remove();
			String msg = String.format("------------------------------ Close transaction ID thread (%d) ---------------------------------", Thread.currentThread().getId());
			logger.debug(msg);			
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	@Override
	public UUID currentUserId() {
		if(userIdContext.get() == null)
			throw new IllegalArgumentException("Current user Id must be started before get it !");
		
		return userIdContext.get();
	}
	
	@Override
	public boolean withinTransaction() {
		if(inTransactionContext.get() == null )
			return false;
		
		return inTransactionContext.get();
	}

	@Override
	public void start(boolean inTransaction) throws IOException {
		start(inTransaction, User.ANONYMOUS_ID);
	}

	@Override
	public void changeUser(UUID userId) throws IOException {
		userIdContext.set(userId);
	}
}
