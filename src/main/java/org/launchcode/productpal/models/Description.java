package org.launchcode.productpal.models;

import org.launchcode.productpal.models.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Description extends AbstractEntity {

    @NotBlank(message = "Description is needed")
    @Size(min = 3, max = 255, message = "Description must be between 3 and 300 characters")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Description() {}

}
