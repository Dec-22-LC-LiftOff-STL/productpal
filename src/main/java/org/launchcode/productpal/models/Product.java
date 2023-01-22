package org.launchcode.productpal.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Product extends AbstractEntity{

    @NotNull
    @Size(min=3, max=50)
    private String name;
    private String category;
    private String description;
    private Integer amount;

    public Product() {
    }

    // Initialize the id and value fields.
    public Product(String aName, String aCategory, String aDescription, Integer anAmount) {
        super();
        this.name = aName;
        this.category = aCategory;
        this.description = aDescription;
        this.amount = anAmount;
    }

    // Getters and setters.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return name;
    }
}