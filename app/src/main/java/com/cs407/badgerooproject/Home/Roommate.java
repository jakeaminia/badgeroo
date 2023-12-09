package com.cs407.badgerooproject.Home;

import java.util.HashMap;

public class Roommate {
    private String email;
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

    public Roommate(String email, String fullName, String profilePicture, String gender,
                    String bio, double minRent, double maxRent, int numRoommates, String roommateGender,
                    String desiredLocation, String housingStyle, String startDate, String endDate) {
        this.email = email;
        this.fullName = fullName;
        this.profilePicture = profilePicture;
        this.gender = gender;
        this.bio = bio;
        this.minRent = String.valueOf(minRent);
        this.maxRent = String.valueOf(maxRent);
        this.numRoommates = String.valueOf(numRoommates);
        this.roommateGender = roommateGender;
        this.desiredLocation = desiredLocation;
        this.housingStyle = housingStyle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Roommate() {
        this.email = "email";
        this.fullName = "fullName";
        this.profilePicture = "profilePicture.jpg";
        this.gender = "gender";
        this.bio = "bio";
        this.minRent = String.valueOf(1000);
        this.maxRent = String.valueOf(1500);
        this.numRoommates = String.valueOf(2);
        this.roommateGender = "roommateGender";
        this.desiredLocation = "desiredLocation";
        this.housingStyle = "housingStyle";
        this.startDate = "startDate";
        this.endDate = "endDate";
    }

    public Roommate(HashMap<String, Object> map) {
        this.email = String.valueOf(map.get("email")); // *
        this.fullName = String.valueOf(map.get("Name"));
        this.profilePicture = String.valueOf(map.get("imageUrl")); // *
        this.gender = String.valueOf(map.get("Gender"));
        this.bio = String.valueOf(map.get("bio")); // *
        this.minRent = String.valueOf(map.get("minBudget"));
        this.maxRent = String.valueOf(map.get("maxBudget"));
        this.numRoommates = String.valueOf(map.get("roommateNum"));
        this.roommateGender = String.valueOf(map.get("genderPreference"));
        this.desiredLocation = String.valueOf(map.get("location")); // *
        this.housingStyle = String.valueOf(map.get("housingStyle"));
        this.startDate = String.valueOf(map.get("startDate"));
        this.endDate = String.valueOf(map.get("endDate"));
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }

    @Override
    public String toString() {
        return String.format("%s looking for %s %s roommates for a(n) %s in %s from %s to %s with a price range from $%s to $%s.\n%s",
                gender, numRoommates, roommateGender, housingStyle, desiredLocation, startDate, endDate, minRent, maxRent, bio);
    }
}