package ru.nsu.fit.endpoint.service.manager;

import org.slf4j.Logger;
import ru.nsu.fit.endpoint.service.database.DBService;

public class ParentManager {
    protected DBService dbService;
    protected Logger flowLog;

    public ParentManager(DBService dbService, Logger flowLog) {
        this.dbService = dbService;
        this.flowLog = flowLog;
    }
}
