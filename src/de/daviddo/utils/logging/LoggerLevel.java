package de.daviddo.utils.logging;

/**
 *
 * @author  Daviddo3399
 * @since   8. Januar 2015
 */
public enum LoggerLevel {

    INFO("INFO"),
    LOADED("LOADED"),
    FINISHED("FINISHED"),
    WARNING("WARN"),
    ERROR("ERROR"),
    SYNTAX_ERROR("Syntax Error");

    private String string;

    LoggerLevel(String name) {
        this.string = name;
    }

    @Override
    public String toString() {
        return string;
    }
}