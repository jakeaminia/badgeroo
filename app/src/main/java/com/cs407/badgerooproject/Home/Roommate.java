package com.cs407.badgerooproject.Home;

public class Roommate {
    private String email;
    private String fullName;
    private String profilePicture;
    private int age;
    private String gender;
    private String bio;
    private double minRent;
    private double maxRent;
    private int numRoommates;
    private String roommateGender;
    private String desiredLocation;
    private String housingStyle;
    private String startDate;
    private String endDate;

    public Roommate(String email, String fullName, String profilePicture, int age, String gender,
                    String bio, double minRent, double maxRent, int numRoommates, String roommateGender,
                    String desiredLocation, String housingStyle, String startDate, String endDate) {
        this.email = email;
        this.fullName = fullName;
        this.profilePicture = profilePicture;
        this.age = age;
        this.gender = gender;
        this.bio = bio;
        this.minRent = minRent;
        this.maxRent = maxRent;
        this.numRoommates = numRoommates;
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
        this.age = 20;
        this.gender = "gender";
        this.bio = "bio";
        this.minRent = 1000;
        this.maxRent = 1500;
        this.numRoommates = 2;
        this.roommateGender = "roommateGender";
        this.desiredLocation = "desiredLocation";
        this.housingStyle = "housingStyle";
        this.startDate = "startDate";
        this.endDate = "endDate";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public double getMinRent() {
        return minRent;
    }

    public void setMinRent(double minRent) {
        this.minRent = minRent;
    }

    public double getMaxRent() {
        return maxRent;
    }

    public void setMaxRent(double maxRent) {
        this.maxRent = maxRent;
    }

    public int getNumRoommates() {
        return numRoommates;
    }

    public void setNumRoommates(int numRoommates) {
        this.numRoommates = numRoommates;
    }

    public String getRoommateGender() {
        return roommateGender;
    }

    public void setRoommateGender(String roommateGender) {
        this.roommateGender = roommateGender;
    }

    public String getDesiredLocation() {
        return desiredLocation;
    }

    public void setDesiredLocation(String desiredLocation) {
        this.desiredLocation = desiredLocation;
    }

    public String getHousingStyle() {
        return housingStyle;
    }

    public void setHousingStyle(String housingStyle) {
        this.housingStyle = housingStyle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return String.format("%d year old %s looking for %d %s roommates for a(n) %s in %s from %s to %s with a price range from $%s to $%s.\n%s",
                age, gender, numRoommates, roommateGender, housingStyle, desiredLocation, startDate, endDate, minRent, maxRent, bio);
    }
}