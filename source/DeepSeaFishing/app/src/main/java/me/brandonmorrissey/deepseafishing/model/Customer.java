package me.brandonmorrissey.deepseafishing.model;

/**
 * Created by: Brandon Morrissey
 * Date: 2/05/2016
 * Customer model class to hold all customer information
 */
public class Customer {

    private long _id;
    private int credit_card;
    private int phone;
    private String last_name;
    private String first_name;
    private String email;

    public Customer() {

    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int getCredit_card() {
        return credit_card;
    }

    public void setCredit_card(int credit_card) {
        this.credit_card = credit_card;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
