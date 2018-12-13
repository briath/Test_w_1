package ru.nsu.fit.endpoint.service.manager;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import ru.nsu.fit.endpoint.service.database.DBService;
import ru.nsu.fit.endpoint.service.database.data.Plan;

import java.util.List;
import java.util.UUID;

public class PlanManager extends ParentManager {
    public PlanManager(DBService dbService, Logger flowLog) {
        super(dbService, flowLog);
    }

    /**
     * Метод создает новый объект типа Plan. Ограничения:
     * name - длина не больше 128 символов и не меньше 2 включительно не содержит спец символов. Имена не пересекаются друг с другом;
    /* details - длина не больше 1024 символов и не меньше 1 включительно;
    /* fee - больше либо равно 0 но меньше либо равно 999999.
     */
    public Plan createPlan(Plan plan) {
        return dbService.createPlan(plan);
    }

    public Plan updatePlan(Plan plan) {
        throw new NotImplementedException("Please implement the method.");
    }

    public void removePlan(UUID id) {
        throw new NotImplementedException("Please implement the method.");
    }

    /**
     * Метод возвращает список планов доступных для покупки.
     */
    public List<Plan> getPlans(UUID customerId) {
        throw new NotImplementedException("Please implement the method.");
    }
}
