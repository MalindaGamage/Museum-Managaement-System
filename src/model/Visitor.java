package model;

import java.util.Date;

public class Visitor {
    private int visitorId;
    private String fullName;
    private String email;
    private String phone;
    private Date visitDate;

    // Constructor
    public Visitor(int visitorId, String fullName, String email, String phone, Date visitDate) {
        this.visitorId = visitorId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.visitDate = visitDate;
    }

    // Getters
    public int getVisitorId() {
        return visitorId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    // Setters
    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
}
