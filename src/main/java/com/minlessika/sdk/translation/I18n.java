package com.minlessika.sdk.translation;

import org.apache.commons.io.FilenameUtils;
import org.takes.Request;
import org.takes.facets.cookies.RqCookies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public final class I18n {
	
	private static final Locale defaultLocale = new Locale("en", "US");
	
	private I18n() {}
	
	private static final ThreadLocal<Locale> locales = new ThreadLocal<>();
	
	public static void register(Locale locale) {
		
		final String country;
		if(locale.getLanguage().equals("fr"))
			country = "FR";
		else if(locale.getLanguage().equals("en"))
			country = "US";
		else
			country = locale.getCountry();
		
		locales.set(new Locale(locale.getLanguage(), country));
	}
	
	public static void registerDefault() {
		locales.set(defaultLocale);
	}
	
	public static void register(Request req) throws IOException {
		Iterable<String> cookies = new RqCookies.Base(req).cookie("lang");
		List<String> cookiesList = new ArrayList<>();
		cookies.forEach(cookiesList::add);
		
		if(cookiesList.isEmpty())
			I18n.registerDefault();
		else
			I18n.register(new Locale(cookiesList.get(0)));
	}
	
	public static Locale locale() {
		if(locales.get() == null)
			return defaultLocale;
		else
			return locales.get();
	}
	
	public static String localizeXslt(String absoluteXsltFilePath) {
		
		final Locale locale = locale();
		return String.format("%s_%s_%s.xsl", FilenameUtils.removeExtension(absoluteXsltFilePath), locale.getLanguage(), locale.getCountry());
	}
	
	public static String localizeText(String key) {
		final ResourceBundle messages = ResourceBundle.getBundle("i18n/MessagesBundle", locale());
		if(messages.containsKey(key))
			return messages.getString(key);
		else
			return key;
	}
}
