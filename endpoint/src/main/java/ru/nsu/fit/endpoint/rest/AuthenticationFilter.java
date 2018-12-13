package ru.nsu.fit.endpoint.rest;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.internal.util.Base64;
import ru.nsu.fit.endpoint.shared.Globals;


/**
 * This filter verify the access permissions for a user
 * based on username and password provided in request.
 */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
    public static final String ADMIN = "ADMIN";
    public static final String UNKNOWN = "UNKNOWN";

    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();
        // access allowed for all
        if (!method.isAnnotationPresent(PermitAll.class)) {
            // access denied for all
            if (method.isAnnotationPresent(DenyAll.class)) {
                Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).entity("Access blocked for all users !!").build();
                requestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }

            // get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            // fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
            String username = null;
            String password = null;

            if (authorization != null && authorization.size() == 1) {
                // get encoded username and password
                final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

                // decode username and password
                String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));

                // split username and password tokens
                final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
                username = tokenizer.nextToken();
                password = tokenizer.nextToken();
            }

            // verifying Username and password
            System.out.println("User: " + username);
            System.out.println("Pass: " + password);

            //Verify user access
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

                //Is user valid?
                if (!isUserAllowed(username, password, rolesSet, requestContext)) {
                    Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).entity("You cannot access this resource").build();
                    requestContext.abortWith(ACCESS_DENIED);
                    return;
                }
            }
        }
    }

    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet, ContainerRequestContext requestContext) {
        String userRole = UNKNOWN;

        if (StringUtils.equals(username, Globals.ADMIN_LOGIN) && StringUtils.equals(password, Globals.ADMIN_PASS)) {
            System.out.println("Role is admin");
            userRole = ADMIN;
        }

        requestContext.setProperty("ROLE", userRole);
        return rolesSet.contains(userRole);
    }
}