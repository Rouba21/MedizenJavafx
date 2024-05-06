package tn.MediZen.controllers;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMS {


    private static final String ACCOUNT_SID = "AC2d0667e7b2c66cb9e7bade3584b4a5b7";
    private static final String AUTH_TOKEN = "c721549e2145be390ed5fbaea374bfca";


    private static final String TWILIO_PHONE_NUMBER = "+19284400493";


    public static void sendSMS(String toPhoneNumber, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);


        Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                message
        ).create();
    }
}