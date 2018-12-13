package ru.nsu.fit.endpoint.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.fit.endpoint.service.database.DBService;
import ru.nsu.fit.endpoint.service.manager.CustomerManager;
import ru.nsu.fit.endpoint.service.manager.PlanManager;
import ru.nsu.fit.endpoint.service.manager.SubscriptionManager;

public class MainFactory {
    private static MainFactory instance;

    private Logger logger;
    private DBService dbService;
    private CustomerManager customerManager;
    private PlanManager planManager;
    private SubscriptionManager subscriptionManager;

    public MainFactory() {
        logger = LoggerFactory.getLogger("FLOW_LOG");

        dbService = new DBService(logger);

        customerManager = new CustomerManager(dbService, logger);
        planManager = new PlanManager(dbService, logger);
        subscriptionManager = new SubscriptionManager(dbService, logger);
    }

    public static MainFactory getInstance() {
        synchronized (MainFactory.class) {
            if (instance == null)
                instance = new MainFactory();
            return instance;
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public DBService getDbService() {
        return dbService;
    }

    public CustomerManager getCustomerManager() {
        return customerManager;
    }

    public PlanManager getPlanManager() {
        return planManager;
    }

    public SubscriptionManager getSubscriptionManager() {
        return subscriptionManager;
    }
}
