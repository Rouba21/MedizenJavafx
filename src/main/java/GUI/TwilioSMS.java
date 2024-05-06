package GUI;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSMS {

    // Twilio credentials
    private static final String ACCOUNT_SID = "AC6d186099300b3a45102fae0ed93f025b";
    private static final String AUTH_TOKEN = "cfb8114591702f0a0f7ba70e627825f4";

    // Twilio phone number (assigned to you)
    private static final String TWILIO_PHONE_NUMBER = "+12176353039";

    // Method to send SMS notification
    public static void sendSMS(String toPhoneNumber, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Send the SMS message
        Message.creator(
                new PhoneNumber(toPhoneNumber), // Recipient's phone number
                new PhoneNumber(TWILIO_PHONE_NUMBER), // Twilio phone number
                message // Message content
        ).create();
    }
}