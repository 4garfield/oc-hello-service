package com.sample.helloservice.model;

public class Greeting {
    private final String content;

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }
}
