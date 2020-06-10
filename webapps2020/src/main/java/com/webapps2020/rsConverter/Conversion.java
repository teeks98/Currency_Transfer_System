package com.webapps2020.rsConverter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Class that contains the logic for currency conversion.
 *
 */
@Singleton
@Path("/conversion")
public class Conversion {

    private final double GBP_TO_USD = 1.24; //Pounds to Dollar conversion. 
    private final double GBP_TO_EUR = 1.15; //Pounds to Euro conversion.
    private final double EUR_TO_USD = 1.08; //Euro to Dollar conversion.

    private final String GBP = "GBP";
    private final String EUR = "EUR";
    private final String USD = "USD";

    /**
     * Method that preforms the currency conversion.
     *
     * @param currency1 The senders currency.
     * @param currency2 The receivers currency.
     * @param amount The amount being sent by the sender.
     * @return The amount being received by the receiver.
     */
    @GET
    @Path("{currency1}/{currency2}/{amount_of_currency1}")
    public Response getConversion(
            @PathParam("currency1") String currency1,
            @PathParam("currency2") String currency2,
            @PathParam("amount_of_currency1") double amount) {
        Amount amt = new Amount();
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);

        switch (currency1) {
            case GBP:
                switch (currency2) {
                    case GBP:
                        amt.setAmount(Double.valueOf(df.format(amount)));
                        break;
                    case EUR:
                        amt.setAmount(Double.valueOf(df.format(amount)) * GBP_TO_EUR);
                        break;
                    case USD:
                        amt.setAmount(Double.valueOf(df.format(amount)) * GBP_TO_USD);
                        break;
                    default:
                        break;
                }
                break;
            case EUR:
                switch (currency2) {
                    case GBP:
                        amt.setAmount(Double.valueOf(df.format(amount)) / GBP_TO_EUR);
                        break;
                    case EUR:
                        amt.setAmount(Double.valueOf(df.format(amount)));
                        break;
                    case USD:
                        amt.setAmount(Double.valueOf(df.format(amount)) * EUR_TO_USD);
                        break;
                    default:
                        break;
                }
                break;
            case USD:
                switch (currency2) {
                    case GBP:
                        amt.setAmount(Double.valueOf(df.format(amount)) / GBP_TO_USD);
                        break;
                    case EUR:
                        amt.setAmount(Double.valueOf(df.format(amount)) / EUR_TO_USD);
                        break;
                    case USD:
                        amt.setAmount(Double.valueOf(df.format(amount)));
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return Response.ok(amt).build();
    }

}
