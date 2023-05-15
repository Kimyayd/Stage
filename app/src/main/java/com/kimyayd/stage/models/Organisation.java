package com.kimyayd.stage.models;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Organisation implements Parcelable {
    private String user_id;
    private String email;
    private String fullname;
    private String description;
    private String website;
    private int ifunumber;
    private String whatsapp;
    private String facebook;
    private String profile_photo;
    private String adress;
    private List<Participant> participants;

    protected Organisation(Parcel in) {
        user_id = in.readString();
        email = in.readString();
        fullname = in.readString();
        description = in.readString();
        website = in.readString();
        ifunumber = in.readInt();
        whatsapp = in.readString();
        facebook = in.readString();
        profile_photo = in.readString();
        adress = in.readString();
    }

    public static final Creator<Organisation> CREATOR = new Creator<Organisation>() {
        @Override
        public Organisation createFromParcel(Parcel in) {
            return new Organisation(in);
        }

        @Override
        public Organisation[] newArray(int size) {
            return new Organisation[size];
        }
    };

    @Override
    public String toString() {
        return "Organisation{" +
                "user_id='" + user_id + '\'' +
                "email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", ifunumber=" + ifunumber +
                ", whatsapp='" + whatsapp + '\'' +
                ", facebook='" + facebook + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                ", adress='" + adress + '\'' +
                ", participants=" + participants +
                '}';
    }

    public Organisation() {

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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public int getIfunumber() {
        return ifunumber;
    }
    public void setIfunumber(int ifunumber) {
        this.ifunumber = ifunumber;
    }
    public String getWhatsapp() {
        return whatsapp;
    }
    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
    public String getFacebook() {
        return facebook;
    }
    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProfile_photo() {
        return profile_photo;
    }
    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }
    public String getAdress() {
        return adress;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }
    public List<Participant> getParticipants() {
        return participants;
    }
    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
    public Organisation(String user_id,String email, String fullname, String description, String website, int ifunumber, String whatsapp, String facebook, String profile_photo, String adress, List<Participant> participants) {
        this.user_id = user_id;
        this.email = email;
        this.fullname = fullname;
        this.description = description;
        this.website = website;
        this.ifunumber = ifunumber;
        this.whatsapp = whatsapp;
        this.facebook = facebook;
        this.profile_photo = profile_photo;
        this.adress = adress;
        this.participants = participants;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(email);
        dest.writeString(fullname);
        dest.writeString(description);
        dest.writeString(website);
        dest.writeInt(ifunumber);
        dest.writeString(whatsapp);
        dest.writeString(facebook);
        dest.writeString(profile_photo);
        dest.writeString(adress);
    }
}
