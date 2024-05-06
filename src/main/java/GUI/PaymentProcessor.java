package GUI;



import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;

import java.util.HashMap;
import java.util.Map;

public class PaymentProcessor {

    private static final String STRIPE_API_KEY = "sk_test_51PBmRXRsJBbEqYztPmpcmuiotqEBYMZQqbWHpFaRgN5dvZlweeIQBmYfvOUDef2d5nJF1itfAcijza3M6MZpjN7F005qsjg9rJ"; // your Stripe test API key (replace with live key for production)

    public static boolean processPayment(float amount, String currency, String email) {
        boolean result = false;

        // Convert amount to cents for Stripe API (adjust conversion if needed)
        Long conversion = (long) (amount * 100);

        // Set your secret key
        Stripe.apiKey = STRIPE_API_KEY;

        try {
            // Check if the customer exists
            Customer existingCustomer = getOrCreateCustomer(email);

            // Create a PaymentIntent with the amount and currency
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(conversion) // Amount in cents
                    .setCurrency(currency)
                    .setCustomer(existingCustomer.getId()) // Use the existing or newly created customer
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // Check if the payment was successful
            if (paymentIntent.getStatus().equals("succeeded")) {
                System.out.println("Payment successful!");
                result = true;
            } else {
                System.out.println("Payment succeeded");
            }
        } catch (StripeException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static Customer getOrCreateCustomer(String email) throws StripeException {
        // Check if the customer exists
        CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                .setEmail(email)
                .build();
        return Customer.create(customerCreateParams);
    }

}
