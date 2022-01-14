package com.minlessika.sdk.pgsql;

import com.minlessika.sdk.datasource.BaseCredentials;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PgBaseCredentials implements BaseCredentials {

	private final Map<String, String> map;
	
	public PgBaseCredentials(final String ...args) {
		this(Arrays.asList(args));
	}
	
	public PgBaseCredentials(final Iterable<String> args) {
		this.map = asMap(args);
	}
	
	@Override
	public String basename() {
		String name = "db_minlessika";
		
		if(map.get("database") != null)
			name = map.get("database");
		
		return name;
	}

	@Override
	public String username() {
		String username = "openpg";
		
		if(map.get("db-user") != null)
			username = map.get("db-user");
		
		return username;
	}

	@Override
	public String password() {
		String password = "openpgpwd";
		
		if(map.get("db-password") != null)
			password = map.get("db-password");
		
		return password;
	}

	@Override
	public int port() {
		int port = 5432;
		
		if(map.get("db-port") != null)
			port = Integer.parseInt(map.get("db-port"));
		
		return port;
	}
	
	@Override
	public String host() {
		String host = "localhost";
		
		if(map.get("db-host") != null)
			host = map.get("db-host");
		
		return host;
	}
	
	private static Map<String, String> asMap(final Iterable<String> args) {
        final Map<String, String> map = new HashMap<>(0);
        final Pattern ptn = Pattern.compile("--([a-z\\-]+)(=.+)?");
        for (final String arg : args) {
            final Matcher matcher = ptn.matcher(arg);
            if (!matcher.matches()) {
                throw new IllegalStateException(
                    String.format("can't parse this argument: '%s'", arg)
                );
            }
            final String value = matcher.group(2);
            if (value == null) {
                map.put(matcher.group(1), "");
            } else {
                map.put(matcher.group(1), value.substring(1));
            }
        }
        return map;
    }	

}
