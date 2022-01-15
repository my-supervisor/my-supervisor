package com.minlessika.supervisor.server;

import java.util.Arrays;
import javax.sql.DataSource;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtCli;
import com.minlessika.core.takes.TkApp;
import com.minlessika.sdk.app.info.AppInfo;
import com.minlessika.sdk.app.info.AppInfoImpl;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.BaseCredentials;
import com.minlessika.sdk.datasource.DataSources;
import com.minlessika.sdk.pgsql.PgBase;
import com.minlessika.sdk.pgsql.PgBaseCredentials;
import com.minlessika.sdk.pgsql.PgHikariDataSourceFactory;
import com.minlessika.sdk.websockets.WebSocketNettosphere;
import com.minlessika.sdk.websockets.WebSocketServer;
import com.minlessika.supervisor.domain.Supervisor;

public final class Main {
	
    public static void main(final String... args) throws Exception {
    	    	    
    	AppInfo appInfo = new AppInfoImpl(Supervisor.NAME, args);
    	BaseCredentials credentials = new PgBaseCredentials(args);
		DataSources dataSourceFactory = new PgHikariDataSourceFactory(credentials);
		DataSource dataSource = dataSourceFactory.create();
		
		WebSocketServer wsServer = new WebSocketNettosphere(
										Arrays.asList(ActivityRealtime.class), 
										args
								   );
		wsServer.start();
		
		Base base = new PgBase(dataSource, appInfo, wsServer);
    	
        new FtCli(
			new TkApp(
				new FkRegex(
					".+",
					new TkMySupervisor(
						base,
						new TkFork(new FkMySupervisor(base))
					)
				),
				base
			),
			args
        ).start(Exit.NEVER);
	}
}
