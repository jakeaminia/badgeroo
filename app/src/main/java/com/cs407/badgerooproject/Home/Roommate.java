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
        /*return String.format(
                "<b>Gender:</b> %s<br>" +
                "<b>Number of Roommates:</b> %s<br>" +
                "<b>Roommate Gender:</b> %s<br>" +
                "<b>Housing Style:</b> %s<br>" +
                "<b>Location:</b> %s<br>" +
                "<b>Dates looking for:</b> %s to %s<br>" +
                "<b>Rent range:</b> %s to %s<br>" +
                "<b>Bio:</b> %s<br>",
                (gender != null && !gender.isEmpty() && !gender.equals("null")) ? gender : "Not specified",
                (numRoommates != null && !numRoommates.isEmpty() && !numRoommates.equals("null")) ? numRoommates : "Not specified",
                (roommateGender != null && !roommateGender.isEmpty() && !roommateGender.equals("null")) ? roommateGender : "Not specified",
                (housingStyle != null && !housingStyle.isEmpty() && !housingStyle.equals("null")) ? housingStyle : "Not specified",
                (desiredLocation != null && !desiredLocation.isEmpty() && !desiredLocation.equals("null")) ? desiredLocation : "Not specified",
                (startDate != null && !startDate.isEmpty() && !startDate.equals("null")) ? startDate : "Not specified",
                (endDate != null && !endDate.isEmpty() && !endDate.equals("null")) ? endDate : "Not specified",
                (minRent != null && !minRent.isEmpty() && !minRent.equals("null")) ? "$" + minRent : "Not specified",
                (maxRent != null && !maxRent.isEmpty() && !maxRent.equals("null")) ? "$" + maxRent : "Not specified",
                (bio != null && !bio.isEmpty() && !bio.equals("null")) ? bio : "Not specified"
        );
    }*/
        return String.format(
                "<b>Gender:</b> %s<br>" +
                "<b>Number of Roommates:</b> %s<br>" +
                "<b>Roommate Gender:</b> %s<br>" +
                "<b>Housing Style:</b> %s<br>" +
                "<b>Location:</b> %s<br>" +
                "<b>Dates looking for:</b> %s<br>" +
                "<b>Rent range:</b> %s<br>" +
                "<b>Bio:</b> %s<br>",
                formatString(gender),
                formatString(numRoommates),
                formatString(roommateGender),
                formatString(housingStyle),
                formatString(desiredLocation),
                formatRange(startDate, endDate, false),
                formatRange(minRent, maxRent, true),
                formatString(bio)
        );
    }

    private String formatString(String text) {
        if (text == null || text.isEmpty() || text.equals("null")) {
            return "<span style='color: red;'>Not specified</span>";
        } else {
            return text;
        }
    }

    private String formatRange(String start, String end, boolean isCurrency) {
        if ((start == null || start.isEmpty() || start.equals("null")) &&
                (end == null || end.isEmpty() || end.equals("null"))) {
            return "<span style='color: red;'>Not specified</span>";
        } else {
            String formattedStart = (isCurrency && start != null && !start.isEmpty() && !start.equals("null")) ? "$" + start : start;
            String formattedEnd = (isCurrency && end != null && !end.isEmpty() && !end.equals("null")) ? "$" + end : end;
            return formattedStart + " to " + formattedEnd;
        }
    }

}