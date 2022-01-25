package com.supervisor.sdk.mailing;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class BasicMailing implements Mailing {

	private final Properties props;
	
	public BasicMailing() throws IOException {
		this("mailing.properties");
	}
	
	public BasicMailing(final String configFilename) throws IOException {
		this(
			readMailServerParameters(configFilename)	
		);
	}
	
	public BasicMailing(final Properties props) {
		this.props = props;
	}
	
	private static Properties readMailServerParameters(final String fileName) throws IOException {
		try (InputStream input = BasicMailing.class.getClassLoader().getResourceAsStream(fileName)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            return prop;
        }
	}
	
	@Override
	public void send(String to, String objet, String message) throws IOException {
		
		Authenticator authenticator = new Authenticator() {
			private PasswordAuthentication authentication;
			
			{
				authentication = new PasswordAuthentication(props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.password"));
			}
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return authentication;
			}
		};
		
		/* Session encapsule pour un client donné sa connexion avec le serveur de mails.*/
		Session session = Session.getDefaultInstance(props, authenticator);

		/* Création du message*/
		Message msg = new MimeMessage(session);		
        
		try {
		      msg.setFrom(new InternetAddress(props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.userName")));
		      msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to, false));
		      msg.setSubject(objet);
		      msg.setHeader("X-Mailer", "Minlessika Mail Server");
		      msg.setContent(message, "text/html");
		      
		      Transport.send(msg);
		}
		catch (AddressException e) {
		      e.printStackTrace();
		      throw new IOException(e);
		} 
		catch (MessagingException e) {
		      e.printStackTrace();
		      throw new IOException(e);
		}
	}
}
