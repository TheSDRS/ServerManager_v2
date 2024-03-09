package de.sdrs.servermanager_v2.api.util;

public enum MessagingLevel {
    DEBUG,
    NORMAL,
    INFO,
    LOW;

    public MessagingLevel getMessagingLevel(String level) {
        return MessagingLevel.valueOf(level);
    }
}
