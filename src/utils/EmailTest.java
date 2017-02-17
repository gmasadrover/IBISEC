package utils;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailTest {

	public void test(){
	    Properties props = System.getProperties();
	    props.put("mail.smtp.starttls.enable", true); // added this line
	    props.put("mail.smtp.host", "10.215.5.1");
	    props.put("mail.smtp.user", "gmas@ibisec.caib.es");
	    props.put("mail.smtp.password", "ib7003r");
	    props.put("mail.smtp.port", "25");
	    props.put("mail.smtp.auth", true);
	
	
	
	    Session session = Session.getInstance(props,null);
	    MimeMessage message = new MimeMessage(session);
	
	    System.out.println("Port: "+session.getProperty("mail.smtp.port"));
	    System.out.println("aaaaaaaaaaaa");
	
	    // Create the email addresses involved
	    try {
	        InternetAddress from = new InternetAddress("gmas@ibisec.caib.es");
	        message.setSubject("Yes we can");
	        message.setFrom(from);
	        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse("gmas@ibisec.caib.es"));
	
	        // Create a multi-part to combine the parts
	        Multipart multipart = new MimeMultipart("alternative");
	
	        // Create your text message part
	        BodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setText("some text to send");
	
	        // Add the text part to the multipart
	        multipart.addBodyPart(messageBodyPart);
	
	        // Create the html part
	        messageBodyPart = new MimeBodyPart();
	        String htmlMessage = "Our html text";
	        messageBodyPart.setContent(htmlMessage, "text/html");
	
	
	        // Add html part to multi part
	        multipart.addBodyPart(messageBodyPart);
	
	        // Associate multi-part with message
	        message.setContent(multipart);
	
	        // Send message
	        Transport transport = session.getTransport("smtp");
	        transport.connect("10.215.5.1", "gmas@ibisec.caib.es", "ib7003r");
	        System.out.println("Transport: "+transport.toString());
	        transport.sendMessage(message, message.getAllRecipients());
	
	
	    } catch (AddressException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (MessagingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
}