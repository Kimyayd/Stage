package com.kimyayd.stage.models;

import java.util.List;

public class Event {
    private String id;
    private String name;
    private String description;
    private String date;
    private String place;
    private String photo;
    private String organisation_id;
    private List<Comment> comments;
    private List<Participant> participants;
    private List<Evaluation> evaluations;

    public Event() {
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", place='" + place + '\'' +
                ", profile_photo='" + photo + '\'' +
                ", organisation_id='" + organisation_id + '\'' +
                ", comments=" + comments +
                ", participants=" + participants +
                ", evaluations=" + evaluations +
                '}';
    }

    public Event(String id, String name, String description, String date, String place, String profile_photo, String organisation_id, List<Comment> comments, List<Participant> participants, List<Evaluation> evaluations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.place = place;
        this.photo = profile_photo;
        this.organisation_id = organisation_id;
        this.comments = comments;
        this.participants = participants;
        this.evaluations = evaluations;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOrganisation_id() {
        return organisation_id;
    }

    public void setOrganisation_id(String organisation_id) {
        this.organisation_id = organisation_id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

}
