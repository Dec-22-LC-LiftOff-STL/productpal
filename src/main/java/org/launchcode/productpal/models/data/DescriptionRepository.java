package org.launchcode.productpal.models.data;

import org.launchcode.productpal.models.Product;
import org.launchcode.productpal.models.Description;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionRepository extends CrudRepository<Description, Integer> {
    public long countById(Integer id);

}