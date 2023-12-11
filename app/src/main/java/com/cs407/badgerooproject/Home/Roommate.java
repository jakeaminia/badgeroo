package com.cs407.badgerooproject.Home;

import java.util.HashMap;

public class Roommate {
    private String id;
    private String fullName;
    private String profilePicture;
    private String gender;
    private String bio;
    private String minRent;
    private String maxRent;
    private String numRoommates;
    private String roommateGender;
    private String desiredLocation;
    private String housingStyle;
    private String startDate;
    private String endDate;

    public Roommate(HashMap<String, Object> map) {
        this.id = String.valueOf(map.get("id"));
        this.fullName = String.valueOf(map.get("Name"));
        this.profilePicture = String.valueOf(map.get("imageUrl"));
        this.gender = String.valueOf(map.get("Gender"));
        this.bio = (map.get("bio") == null) ? null : String.valueOf(map.get("bio")); // *
        this.minRent = String.valueOf(map.get("minBudget"));
        this.maxRent = String.valueOf(map.get("maxBudget"));
        this.numRoommates = String.valueOf(map.get("roommateNum"));
        this.roommateGender = String.valueOf(map.get("genderPreference"));
        this.desiredLocation = String.valueOf(map.get("location"));
        this.housingStyle = String.valueOf(map.get("housingStyle"));
        this.startDate = String.valueOf(map.get("startDate"));
        this.endDate = String.valueOf(map.get("endDate"));
    }

    public String getFullName() {
        return fullName;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }

    public String getGender() {
        return gender;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format(
                "<b>Gender:</b> %s<br>" +
                "<b>Number of Roommates:</b> %s<br>" +
                "<b>Roommate Gender:</b> %s<br>" +
                "<b>Housing Style:</b> %s<br>" +
                "<b>Location:</b> %s<br>" +
                "<b>Dates looking for:</b> %s to %s<br>" +
                "<b>Rent range:</b> $%s to $%s<br>" +
                "<b>Bio:</b> %s<br>", gender, numRoommates, roommateGender, housingStyle, desiredLocation,
                startDate, endDate, minRent, maxRent, (bio != null) ? bio : "");
    }
}