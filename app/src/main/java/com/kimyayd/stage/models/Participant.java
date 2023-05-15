package com.kimyayd.stage.models;

import android.os.Parcel;

import java.util.List;

public class Participant{
    private String email;
    private String fullname;
    private String sexe;
    private String phonenumber;
    private String locality;
    private String profile_photo;
    private List<Organisation> organisations;

    @Override
    public String toString() {
        return "Participant{" +
                "email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                ", sexe='" + sexe + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", locality='" + locality + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                ", organisations=" + organisations +
                '}';
    }

    public Participant() {
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

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public List<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<Organisation> organisations) {
        this.organisations = organisations;
    }

    public Participant(String email, String fullname, String sexe, String phonenumber, String locality, String profile_photo, List<Organisation> organisations) {
        this.email = email;
        this.fullname = fullname;
        this.sexe = sexe;
        this.phonenumber = phonenumber;
        this.locality = locality;
        this.profile_photo = profile_photo;
        this.organisations = organisations;
    }
}
