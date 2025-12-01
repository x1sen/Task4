package com.conference.Task4.util;

import com.conference.Task4.entity.Application;
import com.conference.Task4.service.ApplicationService;

public class SubmitApplicationCommand implements Command {
    private final Application app;
    private final ApplicationService service;

    public SubmitApplicationCommand(Application app, ApplicationService service) {
        this.app = app;
        this.service = service;
    }

    @Override
    public void execute() {
        service.create(app);
    }
}