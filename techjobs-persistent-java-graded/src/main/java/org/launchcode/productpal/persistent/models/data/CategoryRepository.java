package org.launchcode.productpal.persistent.models.data;

import org.launchcode.productpal.persistent.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository <Category, Integer> {
}
