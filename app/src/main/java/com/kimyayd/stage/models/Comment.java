package com.kimyayd.stage.models;

public class Comment {
    private String id;
    private String user_id;
    private String event_id;

    @Override
    public String
    toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", event_id='" + event_id + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    private String content;
    private String date;

    public Comment() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Comment(String id, String user_id, String event_id, String content, String date) {
        this.id = id;
        this.user_id = user_id;
        this.event_id = event_id;
        this.content = content;
        this.date = date;
    }
}
