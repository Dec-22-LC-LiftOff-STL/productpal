package org.launchcode.productpal.persistent.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends AbstractEntity {
    @NotBlank(message = "Location is needed")
    @Size(min = 3, max = 150, message = "Location must be between 3 and 100 characters")
    private String description;
    @OneToMany
    @JoinColumn(name = "category_id")
    private List<Product> products = new ArrayList<>();

    public String getDescription() {
        return description;
    }
    public Category() {

    }


    public void setDescription(String description) {
        this.description = description;
    }
    public List<Product> getJobs() {
        return products;
    }

    public void setJobs(List<Product> products) {
        this.products = products;
    }

}
