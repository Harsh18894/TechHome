package com.techHome.dto;

/**
 * Created by Harsh on 7/19/2016.
 */
public class OrderDTO {
    private String category;
    private String description;
    private String city;
    private String slot;
    private String date;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", slot='" + slot + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
