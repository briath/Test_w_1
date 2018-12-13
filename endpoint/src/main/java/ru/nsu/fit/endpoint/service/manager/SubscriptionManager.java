package ru.nsu.fit.endpoint.service.manager;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import ru.nsu.fit.endpoint.service.database.DBService;
import ru.nsu.fit.endpoint.service.database.data.Subscription;

import java.util.List;
import java.util.UUID;

public class SubscriptionManager extends ParentManager {
    public SubscriptionManager(DBService dbService, Logger flowLog) {
        super(dbService, flowLog);
    }

    /**
     * Метод создает подписку. Ограничения:
     * 1. Подписки с таким планом пользователь не имеет.
     * 2. Стоймость подписки не превышает текущего баланса кастомера и после покупки вычитается из его баласа.
     */
    public Subscription createSubscription(Subscription subscription) {
        throw new NotImplementedException("Please implement the method.");
    }

    public void removeSubscription(UUID subscriptionId) {
        throw new NotImplementedException("Please implement the method.");
    }

    /**
     * Возвращает список подписок указанного customer'а.
     */
    public List<Subscription> getSubscriptions(UUID customerId) {
        throw new NotImplementedException("Please implement the method.");
    }
}
