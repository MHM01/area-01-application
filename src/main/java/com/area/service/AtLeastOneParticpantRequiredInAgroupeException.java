package com.area.service;

public class AtLeastOneParticpantRequiredInAgroupeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AtLeastOneParticpantRequiredInAgroupeException() {
        super("At Least one participant required in a group!");
    }
}
