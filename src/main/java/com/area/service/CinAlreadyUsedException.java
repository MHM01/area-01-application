package com.area.service;

public class CinAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CinAlreadyUsedException() {
        super("The participant CIN already used!");
    }
}
