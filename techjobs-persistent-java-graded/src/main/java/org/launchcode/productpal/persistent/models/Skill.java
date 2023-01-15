package org.launchcode.productpal.persistent.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Skill extends AbstractEntity {
    @NotBlank(message = "Description is needed")
    @Size(min = 3, max = 255, message = "Description must be between 3 and 300 characters")
    private String description;

    @ManyToMany (mappedBy="skills")
    private List<Product> products = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getJobs(){
        return products;
    }

    public void setJobs(List<Product> products){
        this.products = products;
    }
    public Skill() {}

}

