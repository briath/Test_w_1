package ru.nsu.fit.tests.api;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.testng.annotations.Test;
import ru.nsu.fit.services.log.Logger;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Timur Zolotuhin (tzolotuhin@gmail.com)
 */
public class BuildVerificationTest {
    @Test(description = "Create customer via API.")
    public void createCustomer() {
        ClientConfig clientConfig = new ClientConfig();

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "setup");
        clientConfig.register( feature) ;

        clientConfig.register(JacksonFeature.class);

        Client client = ClientBuilder.newClient( clientConfig );

        WebTarget webTarget = client.target("http://localhost:8080/endpoint/rest").path("create_customer");

        Invocation.Builder invocationBuilder =	webTarget.request(MediaType.APPLICATION_JSON);
        Logger.debug("Try to make POST...");
        Response response = invocationBuilder.post(Entity.entity("{\n" +
                "\t\"firstName\":\"Johnds\",\n" +
                "    \"lastName\":\"Weak\",\n" +
                "    \"login\":\"helloworld123@login.com\",\n" +
                "    \"pass\":\"password123\",\n" +
                "    \"balance\":\"100\"\n" +
                "}", MediaType.APPLICATION_JSON));
        Logger.info("Response: " + response.readEntity(String.class));
    }

    @Test(description = "Try to log into the system.", dependsOnMethods = "createCustomer")
    public void login() {
        // TODO: попробовать получить данные для созданного на предыдущем шаге customer.
    }
}
