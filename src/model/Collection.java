package model;

import java.util.Date;

public class Collection {
    private int collectionId;
    private String name;
    private String description;
    private String category;
    private Date acquisitionDate;
    private String status;
    private String imageUrl;

    public Collection(int collectionId, String name, String description, String category, Date acquisitionDate, String status, String imageUrl) {
        this.collectionId = collectionId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.acquisitionDate = acquisitionDate;
        this.status = status;
        this.imageUrl = imageUrl;
    }

    // Getters
    public int getCollectionId() {
        return collectionId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public String getStatus() {
        return status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Setters
    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}