package com.kimyayd.stage.models;

public class Category {
    private String id ;
    private String type;
    private String description;
    private String value;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Category() {
    }

    public Category(String id, String type, String description, String value) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.value = value;
    }
}
