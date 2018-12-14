package com.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	public static void sendEmail(final String from,final String password,String to,String sub,String msg,String isDebug) throws NoSuchProviderException{  
        //Get properties object    
        Properties props = new Properties();  
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.gmail.com");   
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.port", "587");    
        props.put("mail.smtp.debug", "true");
        props.put("mail.debug", isDebug);
        props.put("mail.smtp.starttls.required", "true");
        //get Session   
        Session session = Session.getDefaultInstance(props,    
         new javax.mail.Authenticator() {    
         protected PasswordAuthentication getPasswordAuthentication() {    
        	 return new PasswordAuthentication(from,password);  
         }    
        });   
        Transport transport = session.getTransport();
        //compose message    
        try {    
         MimeMessage message = new MimeMessage(session);    
         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
         message.setSubject(sub); 
         
        // message.setText(msg);  //send simple text message  
         //send message  
         transport.connect();
         message.setContent(msg, "text/html; charset=utf-8");
         transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
         transport.close();
         System.out.println("message sent successfully");    
        } catch (MessagingException e) {
        	throw new RuntimeException(e);
        }          
  }  
	public static void receiveEmail(final String user,final String password,String isDebug) throws MessagingException, IOException {
		  
		   String host="smtp.gmail.com";  
		   Properties properties = System.getProperties();  
		   properties.setProperty("mail.smtp.host",host );  
		   properties.put("mail.smtp.auth", "true");  
		   properties.put("mail.debug",isDebug);
		  
		   Session session = Session.getDefaultInstance(properties,  
		    new javax.mail.Authenticator() {  
		    protected PasswordAuthentication getPasswordAuthentication() {  
		     return new PasswordAuthentication(user,password);  
		    }  
		   });  
			URLName url = new URLName("imaps", host, 993, "Inbox", user, password);

		     Store store = session.getStore(url);  
		     store.connect(host,user,password);  
		  
		     Folder folder = store.getFolder(url);  
		     folder.open(Folder.READ_WRITE);  
		  
		     Message[] message = folder.getMessages();  
		  
		  
		  for (int a = 0; a < message.length; a++) {  
		    System.out.println("-------------" + (a + 1) + "-----------"); 
		    
		    System.out.println("Date :"+message[a].getSentDate());  
		    System.out.println("subject "+message[a].getSubject());
		    System.out.println("messageNo :"+message[a].getMessageNumber());
		    
		    Multipart multipart = (Multipart) message[a].getContent();  
		   
		    for (int i = 0; i < multipart.getCount(); i++) {  
		     BodyPart bodyPart = multipart.getBodyPart(i);  
		     InputStream stream = bodyPart.getInputStream();  
		     BufferedReader br = new BufferedReader(new InputStreamReader(stream));  
		  
		      while (br.ready()) {  
		         System.out.println(br.readLine());  
		      }  
		     System.out.println();  
		    }  
		   System.out.println();  
		  }  
		  
		  folder.close(true);  
		  store.close();  
    }
}
