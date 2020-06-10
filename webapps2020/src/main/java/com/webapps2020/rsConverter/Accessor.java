package com.webapps2020.rsConverter;

import javax.ejb.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 * Class to create an object for the REST client.
 *
 */
@Singleton
public class Accessor {

    /**
     * Method creating an instance of the REST client.
     *
     * @param senderCurrency The currency of the sender.
     * @param recepientCurrency The currency of the receiver.
     * @param amount The amount being sent by the sender.
     * @return The new amount in the receivers currency.
     */
    public double newAmount(String senderCurrency, String recepientCurrency, String amount) {
        Client client = ClientBuilder.newClient();
        WebTarget conversionResource = client.target("http://localhost:8080/webapps2020/resources/conversion")
                .path("{currency1}")
                .resolveTemplate("currency1", senderCurrency)
                .path("{currency2}")
                .resolveTemplate("currency2", recepientCurrency)
                .path("{amount_of_currency1}")
                .resolveTemplate("amount_of_currency1", String.valueOf(amount));
        Invocation.Builder builder = conversionResource.request(MediaType.APPLICATION_JSON);
        String response = builder.get(String.class);
        JSONObject jsonObject = new JSONObject(response);
        client.close();
        return jsonObject.getDouble("amount");
    }
}
