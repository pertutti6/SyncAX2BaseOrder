package Model;

import com.sun.mail.smtp.SMTPTransport;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class SendEmailOffice365 {

    private static final String SMTP_SERVER = "sirocco-ch.mail.protection.outlook.com";
    private static final String USERNAME = "sbaiz@sirocco.ch";
    private static final String PASSWORD = "Dani6&sirocco";

   /* private static final String EMAIL_FROM = "sbaiz@sirocco.ch";
    private static final String EMAIL_TO = "sbaiz@sirocco.ch";
    private static final String EMAIL_TO_CC = "";

    private static final String EMAIL_SUBJECT = "Fehler beim Order Sync AX an Base";
    private static final String EMAIL_TEXT = "Hello Java Mail \n ABC123";*/

    public void sendEmail(String eMailFrom, String emailTo,String emailCC,String eMailSubject,String emailText,String sFilepath) {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "25"); // default port 25

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

            // from
            msg.setFrom(new InternetAddress(eMailFrom));

            // to
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailTo, false));

            // cc
            msg.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(emailCC, false));

            // subject
            msg.setSubject(eMailSubject);

            if (sFilepath.equals("")) {
                // content
                msg.setText(emailText);
            }
            else {
                // Create the message part
                BodyPart messageBodyPart = new MimeBodyPart();
                // Now set the actual message
                messageBodyPart.setText(emailText);

                // Create a multipar message
                Multipart multipart = new MimeMultipart();

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                // Part two is attachment
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(sFilepath);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(sFilepath);
                multipart.addBodyPart(messageBodyPart);

                // Send the complete message parts
                msg.setContent(multipart);
            }

            msg.setSentDate(new Date());


            // Get SMTPTransport
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");


            // connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }


}