package com.minlessika.sdk.pgsql;

import com.minlessika.sdk.datasource.BaseCredentials;
import com.minlessika.sdk.datasource.DataSources;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PgHikariDataSourceFactory implements DataSources {

	private final String host;
	private final String databaseName;
	private final String username;
	private final String password;
	private final int port;
	
	public PgHikariDataSourceFactory(final String username, final String password, final String databaseName, final int port, final String host) {
		this.databaseName = databaseName;
		this.username = username;
		this.password = password;
		this.port = port;
		this.host = host;
	}
	
	public PgHikariDataSourceFactory(final BaseCredentials credentials) {
		this(credentials.username(), credentials.password(), credentials.basename(), credentials.port(), credentials.host());
	}
	
	@Override
	public DataSource create() {
		return createDateSource(username, password, databaseName, port, host);
	}

	@Override
	public DataSource create(String name) {
		return createDateSource(username, password, name, port, host);
	}
	
	public DataSource createDateSource() {
		return createDateSource(username, password, databaseName, port, host);
	}
	
	private DataSource createDateSource(String username, String password, String databaseName, int port, String host) {
		
		Properties config = new Properties();

    	try(InputStream inputStreamConfig = PgBase.class.getClassLoader().getResourceAsStream("db_settings.properties")) {
			if(inputStreamConfig == null)
				throw new FileNotFoundException("Settings file not found !");
			
			config.load(inputStreamConfig);			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    	
    	String url = String.format("jdbc:postgresql://%s:%d/%s", host, port, databaseName);
    	String driver = config.getProperty("driver");
    	String cachePrepStmts = config.getProperty("cachePrepStmts");
    	String prepStmtCacheSize = config.getProperty("prepStmtCacheSize");
    	String prepStmtCacheSqlLimit = config.getProperty("prepStmtCacheSqlLimit");
		int maximumPoolSize = Integer.parseInt(config.getProperty("maximumPoolSize"));
		long connectionTimeout = Long.parseLong(config.getProperty("connectionTimeout"));
		long maxLifetime = Long.parseLong(config.getProperty("maxLifetime"));
		
		HikariConfig configDb = new HikariConfig();
		configDb.setJdbcUrl(url);
		configDb.setUsername(username);
		configDb.setPassword(password);
		configDb.setDriverClassName(driver);
		configDb.addDataSourceProperty("cachePrepStmts", cachePrepStmts);
		configDb.addDataSourceProperty("prepStmtCacheSize", prepStmtCacheSize);
		configDb.addDataSourceProperty("prepStmtCacheSqlLimit", prepStmtCacheSqlLimit);
		configDb.setMaximumPoolSize(maximumPoolSize);
		configDb.setConnectionTimeout(connectionTimeout);
		configDb.setMaxLifetime(maxLifetime);
		configDb.setIsolateInternalQueries(true); 

        return new HikariDataSource(configDb);				
	}
}
