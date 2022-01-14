package com.minlessika.sdk.app.info;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AppInfoImpl implements AppInfo {

	private final Map<String, String> map;
	private final String currentModule;
	
	public AppInfoImpl(final String currentModule, final String ...args) {
		this(currentModule, Arrays.asList(args));
	}
	
	public AppInfoImpl(final String currentModule, final Iterable<String> args) {
		this.map = asMap(args);
		this.currentModule = currentModule;
	}
	
	@Override
	public boolean activeRecaptcha() {
		boolean active = false;
		
		if(map.get("recaptcha-active") != null)
			active = Boolean.parseBoolean(map.get("recaptcha-active"));
		
		return active;
	}

	@Override
	public String recaptchaSiteKey() {
		
		if(map.get("recaptcha-site-key") != null)
			return map.get("recaptcha-site-key");
		else
			return StringUtils.EMPTY; 
	}

	@Override
	public String recaptchaSecretKey() {
		if(map.get("recaptcha-secret-key") != null)
			return map.get("recaptcha-secret-key");
		else
			return StringUtils.EMPTY;
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

	@Override
	public boolean activeMailChimp() {
		boolean active = false;
		
		if(map.get("mailchimp-active") != null)
			active = Boolean.parseBoolean(map.get("mailchimp-active"));
		
		return active;
	}

	@Override
	public Map<String, String> moduleLinks() {
		
		final Map<String, String> links = new HashMap<>();
		for (Entry<String, String> entry : map.entrySet()) {
			if(entry.getKey().startsWith("link")) {
				links.put(entry.getKey().split("-")[1], entry.getValue());
			}
		}
		
		return links;
	}

	@Override
	public String sharedDomain() {
		if(map.get("shared-domain") != null)
			return map.get("shared-domain");
		else
			return "localhost";
	}

	@Override
	public String minlessikaDomain() {
		if(moduleLinks().containsKey("minlessika")) {
			return moduleLinks().get("minlessika");
		} else {
			return "";
		}
	}

	@Override
	public String currentModule() {
		return currentModule;
	}

	@Override
	public String home() {
		return String.format("%s/login", minlessikaDomain());
	}
}
