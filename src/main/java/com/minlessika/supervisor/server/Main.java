/*
 * Copyright (c) 2018-2022 Minlessika
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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

/**
 * Entrance.
 * @since 0.1
 */
public final class Main {

	/**
	 * We start all things here.
	 * @param args Arguments.
	 * @throws Exception If fails
	 */
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
