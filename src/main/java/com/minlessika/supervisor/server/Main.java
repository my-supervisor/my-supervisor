package com.minlessika.supervisor.server;

import com.minlessika.core.takes.TkApp;
import com.minlessika.sdk.app.info.AppInfoImpl;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.pgsql.PgBase;
import com.minlessika.sdk.websockets.WebSocketNettosphere;
import com.minlessika.sdk.websockets.WebSocketServer;
import com.minlessika.db.BasicDatabase;
import com.minlessika.db.DatabaseLiquibaseUpdate;
import com.minlessika.membership.domain.Membership;
import com.minlessika.utils.ConsoleArgs;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Arrays;
import java.util.Map;
import javax.sql.DataSource;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtCli;

public final class Main {
	
    public static void main(final String... args) throws Exception {
		final Map<String, String> map = new ConsoleArgs("--", args).asMap();
		final HikariConfig config = new HikariConfig();
		config.setDriverClassName("org.postgresql.Driver");
		config.setJdbcUrl(
			String.format(
				"jdbc:postgresql://%s:%s/%s",
				map.get("db-host"),
				map.get("db-port"),
				map.get("database")
			)
		);
		config.setUsername(map.get("db-user"));
		config.setPassword(map.get("db-password"));
		int mps = 25;
		if (map.containsKey("db-max-pool-size")) {
			mps = Integer.parseInt(map.get("db-max-pool-size"));
		}
		config.setMaximumPoolSize(mps);
		final DataSource source = new HikariDataSource(config);
		new DatabaseLiquibaseUpdate(
			new BasicDatabase(source),
			"liquibase/db.changelog-master.xml"
		).start();
		final WebSocketServer wss = new WebSocketNettosphere(
			Arrays.asList(ActivityRealtime.class),
			args
	    );
		wss.start();
		final Base base = new PgBase(
			source,
			new AppInfoImpl(Membership.NAME, args),
			wss
		);
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
