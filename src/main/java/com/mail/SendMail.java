package com.mail;

import java.io.IOException;

import javax.mail.MessagingException;

public class SendMail {

public static void main(String[] args) throws MessagingException, IOException {
		
		final String isDebug = "false";
	    final String fromEmail = "xxx@gmail.com";
	    final String toEmail = "xxx@gmail.com";
	    final String pwd = "xxx";
	    final String subject = "Test Email";
	    final String content = "<h2>Testing Email</h2><br>How r u? Good Morning";
	    
	    EmailUtil.sendEmail(fromEmail,pwd,toEmail,subject,content,isDebug);  
        EmailUtil.receiveEmail(fromEmail, pwd,isDebug);
	}
}
