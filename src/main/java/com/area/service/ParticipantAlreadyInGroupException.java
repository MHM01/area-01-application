package com.area.service;

public class ParticipantAlreadyInGroupException extends RuntimeException {

    public ParticipantAlreadyInGroupException(String cin) {
        super("The participant with cin " + cin + " is already in a group");
    }
}
