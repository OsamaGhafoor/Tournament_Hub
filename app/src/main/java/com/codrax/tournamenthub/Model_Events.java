package com.codrax.tournamenthub;

public class Model_Events {
    String event , time ;

    public Model_Events() {
    }

    public Model_Events(String event, String time) {
        this.event = event;
        this.time = time;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
