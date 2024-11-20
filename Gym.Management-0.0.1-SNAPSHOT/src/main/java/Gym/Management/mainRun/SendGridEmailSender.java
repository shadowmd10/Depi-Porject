package Gym.Management.mainRun;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;

public class SendGridEmailSender {

	// Method to send email
    public static void sendEmail(String recipientEmail, String subject, String messageBody) {
    	
    	String[] config = getConfig();
        // SendGrid API Key
        String sendGridApiKey = config[0];

        // Create a SendGrid object
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        // Create an email message
        Email from = new Email(config[1]);
        Email to = new Email(recipientEmail);
        Content content = new Content("text/plain", messageBody);
        Mail mail = new Mail(from, subject, to, content);

        try {
            // Create a request to SendGrid API
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            // Send the request
            Response response = sg.api(request);
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            System.out.println("Headers: " + response.getHeaders());

        } catch (IOException ex) {
            System.out.println("Error sending email: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    
    public static String[] getConfig() {
    	
    	String apiKey = "";
    	String senderEmail = "";
    	
    	Properties properties = new Properties();
    	
    	try (InputStream input = new FileInputStream(System.getProperty("user.dir") +"\\src\\main\\java\\Gym\\Management\\mainRun\\SendGridConfig.properties")) {
            
            // Load the properties file
            properties.load(input);

            // Access the property values
            apiKey = properties.getProperty("apiKey");
            senderEmail = properties.getProperty("senderEmail");


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    	
    	return new String[] {apiKey, senderEmail};
    }
    
    
    public static void main(String[] args) {
        // Example usage:
//        sendEmail("amr.saber.fathy@gmail.com", "Subscription Test", "Hello! This is a test message using SendGrid.");
    }
	
}
