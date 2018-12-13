package ru.nsu.fit.endpoint.service.database;

import jersey.repackaged.com.google.common.collect.Lists;
import org.slf4j.Logger;
import ru.nsu.fit.endpoint.service.database.data.Customer;
import ru.nsu.fit.endpoint.service.database.data.Plan;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class DBService {
    // Constants
    private static final String INSERT_CUSTOMER = "INSERT INTO CUSTOMER(id, first_name, last_name, login, pass, balance) values ('%s', '%s', '%s', '%s', '%s', %s)";
    private static final String INSERT_SUBSCRIPTION = "INSERT INTO SUBSCRIPTION(id, customer_id, plan_id) values ('%s', '%s', '%s')";
    private static final String INSERT_PLAN = "INSERT INTO PLAN(id, name, details, fee) values ('%s', '%s', '%s', %s)";

    private static final String SELECT_CUSTOMER = "SELECT id FROM CUSTOMER WHERE login='%s'";
    private static final String SELECT_CUSTOMERS = "SELECT * FROM CUSTOMER";

    private Logger logger;
    private static final Object generalMutex = new Object();
    private Connection connection;

    public DBService(Logger logger) {
        this.logger = logger;
        init();
    }

    public Customer createCustomer(Customer customerData) {
        synchronized (generalMutex) {
            logger.info(String.format("Method 'createCustomer' was called with data: '%s'", customerData));

            customerData.setId(UUID.randomUUID());
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(
                        String.format(
                                INSERT_CUSTOMER,
                                customerData.getId(),
                                customerData.getFirstName(),
                                customerData.getLastName(),
                                customerData.getLogin(),
                                customerData.getPass(),
                                customerData.getBalance()));
                return customerData;
            } catch (SQLException ex) {
                logger.error(ex.getMessage(), ex);
                throw new RuntimeException(ex);
            }
        }
    }

    public List<Customer> getCustomers() {
        synchronized (generalMutex) {
            logger.info("Method 'getCustomers' was called.");

            try {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(SELECT_CUSTOMERS);
                List<Customer> result = Lists.newArrayList();
                while (rs.next()) {
                    Customer customerData = new Customer()
                            .setFirstName(rs.getString(2))
                            .setLastName(rs.getString(3))
                            .setLogin(rs.getString(4))
                            .setPass(rs.getString(5))
                            .setBalance(rs.getInt(6));

                    result.add(customerData);
                }
                return result;
            } catch (SQLException ex) {
                logger.error(ex.getMessage(), ex);
                throw new RuntimeException(ex);
            }
        }
    }

    public UUID getCustomerIdByLogin(String customerLogin) {
        synchronized (generalMutex) {
            logger.info(String.format("Method 'getCustomerIdByLogin' was called with data '%s'.", customerLogin));

            try {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(
                        String.format(
                                SELECT_CUSTOMER,
                                customerLogin));
                if (rs.next()) {
                    return UUID.fromString(rs.getString(1));
                } else {
                    throw new IllegalArgumentException("Customer with login '" + customerLogin + " was not found");
                }
            } catch (SQLException ex) {
                logger.debug(ex.getMessage(), ex);
                throw new RuntimeException(ex);
            }
        }
    }

    public Plan createPlan(Plan plan) {
        synchronized (generalMutex) {
            logger.info(String.format("Method 'createPlan' was called with data '%s'.", plan));

            plan.setId(UUID.randomUUID());
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(
                        String.format(
                                INSERT_PLAN,
                                plan.getId(),
                                plan.getName(),
                                plan.getDetails(),
                                plan.getFee()));
                return plan;
            } catch (SQLException ex) {
                logger.error(ex.getMessage(), ex);
                throw new RuntimeException(ex);
            }
        }
    }

    private void init() {
        logger.debug("-------- MySQL JDBC Connection Testing ------------");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            logger.debug("Where is your MySQL JDBC Driver?", ex);
            throw new RuntimeException(ex);
        }

        logger.debug("MySQL JDBC Driver Registered!");

        try {
            connection = DriverManager
                    .getConnection(
                            "jdbc:mysql://localhost:3306/testmethods?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false",
                            "user",
                            "user");
        } catch (SQLException ex) {
            logger.error("Connection Failed! Check output console", ex);
            throw new RuntimeException(ex);
        }

        if (connection != null) {
            logger.debug("You made it, take control your database now!");
        } else {
            logger.error("Failed to make connection!");
        }
    }
}
