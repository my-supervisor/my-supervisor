package com.supervisor.sdk.secure;

import com.supervisor.sdk.utils.logging.Logger;
import com.supervisor.sdk.utils.logging.MLogger;
import org.apache.commons.lang.StringUtils;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public final class GRecaptcha implements Recaptcha {

	private static final Logger logger = new MLogger(GRecaptcha.class);
	
	private final String siteKey;
	private final String secretKey;
	private final boolean isActive;

	public GRecaptcha(final boolean isActive, final String siteKey, final String secretKey) {
		this.isActive = isActive;
		this.siteKey = siteKey;
		this.secretKey = secretKey;
	}
	
	@Override
	public String siteKey() {
		return siteKey;
	}

	@Override
	public String secretKey() {
		return secretKey;
	}

	@Override
	public boolean isValid(String recaptchaResponse) {
		
		if(!isActive)
			return true;
		
		if(StringUtils.isBlank(recaptchaResponse))
			return false;
		
		try {
            URL verifyUrl = new URL("https://www.google.com/recaptcha/api/siteverify");
 
            // Ouvrez une connexion (Connection) à l'URL ci-dessus.
            HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();
 
            // Ajoutez les informations d'en-tête à la demande pour préparer l'envoi au serveur.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
            // Les données seront envoyées au serveur.
            String postParams = String.format("secret=%s&response=%s", secretKey, recaptchaResponse);
 
            // Send Request
            conn.setDoOutput(true);
 
            // Obtenez Output Stream (le flux de sortie) de la connexion au Server.
            // Écrivez des données dans ce flux, ce qui signifie envoyer des données au serveur.
            OutputStream outStream = conn.getOutputStream();
            outStream.write(postParams.getBytes());
 
            outStream.flush();
            outStream.close();
 
            // Obtenez Input Stream (Le flux de l'entrée) de la Connexion
            // pour lire des données envoyées par Server.
            InputStream is = conn.getInputStream();
 
            JsonReader jsonReader = null;
            try {
            	jsonReader = Json.createReader(is);
                JsonObject jsonObject = jsonReader.readObject();
                return jsonObject.getBoolean("success");
			} finally {
				if(jsonReader != null)
					jsonReader.close();
			}
            
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
	}

	@Override
	public void validate(String recaptchaResponse) {
		if(StringUtils.isBlank(secretKey) || StringUtils.isBlank(siteKey))
			return;
		
		if(!isValid(recaptchaResponse))
			throw new IllegalArgumentException("Captcha invalid !");
	}

}
