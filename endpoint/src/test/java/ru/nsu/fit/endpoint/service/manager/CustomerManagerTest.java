package ru.nsu.fit.endpoint.service.manager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import ru.nsu.fit.endpoint.service.database.DBService;
import ru.nsu.fit.endpoint.service.database.data.Customer;

import java.util.UUID;

public class CustomerManagerTest {
    private DBService dbService;
    private Logger logger;
    private CustomerManager customerManager;

    private Customer customerBeforeCreateMethod;
    private Customer customerAfterCreateMethod;

    @Before
    public void before() {
        // create stubs for the test's class
        dbService = Mockito.mock(DBService.class);
        logger = Mockito.mock(Logger.class);

        customerBeforeCreateMethod = new Customer()
                .setId(null)
                .setFirstName("John")
                .setLastName("Wick")
                .setLogin("john_wick@gmail.com")
                .setPass("Baba_Jaga")
                .setBalance(0);
        customerAfterCreateMethod = customerBeforeCreateMethod.clone();
        customerAfterCreateMethod.setId(UUID.randomUUID());

        Mockito.when(dbService.createCustomer(customerBeforeCreateMethod)).thenReturn(customerAfterCreateMethod);

        // create the test's class
        customerManager = new CustomerManager(dbService, logger);
    }

    @Test
    public void testCreateNewCustomer() {
        // Вызываем метод, который хотим протестировать
        Customer customer = customerManager.createCustomer(customerBeforeCreateMethod);

        // Проверяем результат выполенния метода
        Assert.assertEquals(customer.getId(), customerAfterCreateMethod.getId());

        // Проверяем, что метод мока базы данных был вызван 1 раз
        Assert.assertEquals(1, Mockito.mockingDetails(dbService).getInvocations().size());
    }

    @Test
    public void testCreateCustomerWithNullArgument() {
        try {
            customerManager.createCustomer(null);
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Argument 'customerData' is null.", ex.getMessage());
        }
    }

    @Test
    public void testCreateCustomerWithEasyPassword() {
        try {
            customerBeforeCreateMethod.setPass("123qwe");
            customerManager.createCustomer(customerBeforeCreateMethod);
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Password is easy.", ex.getMessage());
        }
    }
}
