package me.brandonmorrissey.deepseafishing.model;

/**
 * Created by: Brandon Morrissey
 * Date: 2/05/2016
 * Booking model class to hold a booking data.
 */
public class Booking {

    private long _id;
    private int customerId;
    private int numberOfPassengers;
    private double cost;
    private String dateBooked;

    public Booking() {

    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDateBooked() {
        return dateBooked;
    }

    public void setDateBooked(String dateBooked) {
        this.dateBooked = dateBooked;
    }
}
