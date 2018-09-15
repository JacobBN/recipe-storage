/**
 * @author Jacob B. N.
 * @version 07/27/2018
 */
package application;

import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

/**
 * This class represents an email sent from scripts@jacobbn.com.
 */
public class Email {
	// Fields
	private static String USERNAME = "scripts@jacobbn.com";
	private static String PASSWORD = "";
	private InternetAddress[] recipients;
	private String message, subject;
	
	/**
	 * Constructs an email object with the given recipients, subject, and message.
	 * 
	 * @param recipients
	 * @param subject
	 * @param message
	 */
	public Email(List<String> recipients, String subject, String message)
	{
		this.recipients = new InternetAddress[recipients.size()];
		try 
		{
			for (int index = 0; index < this.recipients.length; index++)
			{
					this.recipients[index] = InternetAddress.parse(recipients.get(index))[0];
			}
		} 
		catch (AddressException e) 
		{
			e.printStackTrace();
		}
		this.message = message;
		this.subject = subject;
	}
	
	/**
	 * Sends the email. Returns 1 on success, and 0 on failure.
	 */
	public int sendEmail()
	{
		String host = "asmtp.unoeuro.com";
		int port = 587;
		
		Properties properties = System.getProperties();
	    properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", USERNAME);
        properties.put("mail.smtp.password", PASSWORD);
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		
		Session session = Session.getInstance(properties);

		try 
		{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME));
			message.addRecipients(Message.RecipientType.TO, recipients);
			
			message.setSubject(subject);
			message.setText(this.message);
			
			Transport.send(message, USERNAME, PASSWORD);
		} 
		catch (MessagingException e) 
		{
			return 0;
		}
		
		return 1;
	}
}
