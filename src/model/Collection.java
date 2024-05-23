package model;

import java.util.Date;

public class Collection {
    private String collectionId;
    private String name;
    private String description;
    private String category;
    private Date acquisitionDate;
    private String status;
    private String imageUrl;

    public Collection(String collectionId, String name, String description, String category, Date acquisitionDate, String status, String imageUrl) {
        this.collectionId = collectionId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.acquisitionDate = acquisitionDate;
        this.status = status;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
