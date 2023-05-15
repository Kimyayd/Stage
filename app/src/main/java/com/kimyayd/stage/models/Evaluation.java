package com.kimyayd.stage.models;

public class Evaluation {
    private String id;
    private String user_id;
    private String event_id;
    private int value;

    @Override
    public String toString() {
        return "Evaluation{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", event_id='" + event_id + '\'' +
                ", value=" + value +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Evaluation() {
    }

    public Evaluation(String id, String user_id, String event_id, int value) {
        this.id = id;
        this.user_id = user_id;
        this.event_id = event_id;
        this.value = value;
    }
}
