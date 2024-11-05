package dev.jakapaw.giftcard.seriesmanager.application.event;

import org.springframework.context.ApplicationEvent;

public class IssueEventBase extends ApplicationEvent {
    public IssueEventBase(Object source) {
        super(source);
    }
}
