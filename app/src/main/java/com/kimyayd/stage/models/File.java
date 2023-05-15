package com.kimyayd.stage.models;

public class File {
    private String id;
    private String user_id;
    private String title;
    private String emplacement;
    private String type_file;
    private String description;
    private String date;

    @Override
    public String toString() {
        return "File{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", title='" + title + '\'' +
                ", emplacement='" + emplacement + '\'' +
                ", type_file='" + type_file + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getType_file() {
        return type_file;
    }

    public void setType_file(String type_file) {
        this.type_file = type_file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public File() {
    }

    public File(String id, String user_id, String title, String emplacement, String type_file, String description, String date) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.emplacement = emplacement;
        this.type_file = type_file;
        this.description = description;
        this.date = date;
    }
}
