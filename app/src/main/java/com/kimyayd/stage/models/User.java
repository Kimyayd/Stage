package com.kimyayd.stage.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{

    private String id;
    private String email;
    private String fullname;
    private String date;
    private String type;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User(String id, String email, String fullname, String date, String type) {
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.date = date;
        this.type = type;
    }

    protected User(Parcel in) {
        id = in.readString();
        email = in.readString();
        fullname = in.readString();
        date = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(fullname);
        dest.writeString(date);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
