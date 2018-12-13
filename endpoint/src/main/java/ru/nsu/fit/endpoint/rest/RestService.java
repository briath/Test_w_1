package ru.nsu.fit.endpoint.rest;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.exception.ExceptionUtils;
import ru.nsu.fit.endpoint.service.MainFactory;
import ru.nsu.fit.endpoint.service.database.data.Customer;
import ru.nsu.fit.endpoint.shared.JsonMapper;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("")
public class RestService {
    @RolesAllowed({AuthenticationFilter.UNKNOWN, AuthenticationFilter.ADMIN})
    @GET
    @Path("/health_check")
    public Response healthCheck() {
        return Response.ok().entity("{\"status\": \"OK\"}").build();
    }

    @RolesAllowed({AuthenticationFilter.UNKNOWN , AuthenticationFilter.ADMIN})
    @GET
    @Path("/get_role")
    public Response getRole(@Context ContainerRequestContext crc) {
        return Response.ok().entity(String.format("{\"role\": \"%s\"}", crc.getProperty("ROLE"))).build();
    }

    @RolesAllowed(AuthenticationFilter.ADMIN)
    @GET
    @Path("/get_customers")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCustomers() {
        try {
            List<Customer> customers = MainFactory.getInstance().getCustomerManager().getCustomers();

            return Response.ok().entity(JsonMapper.toJson(customers, true)).build();
        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage() + "\n" + ExceptionUtils.getFullStackTrace(ex)).build();
        }
    }

    @RolesAllowed(AuthenticationFilter.ADMIN)
    @POST
    @Path("/create_customer")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCustomer(String customerDataJson) {
        try {
            // convert json to object
            Customer customerData = JsonMapper.fromJson(customerDataJson, Customer.class);

            // create new customer
            Customer customer = MainFactory.getInstance().getCustomerManager().createCustomer(customerData);

            // send the answer
            return Response.ok().entity(JsonMapper.toJson(customer, true)).build();
        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage() + "\n" + ExceptionUtils.getFullStackTrace(ex)).build();
        }
    }

    @RolesAllowed(AuthenticationFilter.ADMIN)
    @GET
    @Path("/get_customer_id/{customer_login}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerId(@PathParam("customer_login") String customerLogin) {
        try {
            List<Customer> customers = MainFactory
                    .getInstance()
                    .getCustomerManager()
                    .getCustomers()
                    .stream()
                    .filter(x -> x.getLogin().equals(customerLogin))
                    .collect(Collectors.toList());

            Validate.isTrue(customers.size() == 1);

            return Response.ok().entity(JsonMapper.toJson(customers.get(0), true)).build();
        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage() + "\n" + ExceptionUtils.getFullStackTrace(ex)).build();
        }
    }
}