package org.launchcode.productpal.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Category extends AbstractEntity{

    @NotNull
    @Size(min=3, max=50)
    private String name;

    public Category() {
    }

    // Initialize the id and value fields.
    public Category(String aName) {
        super();
        this.name = aName;
    }

    // Getters and setters.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
