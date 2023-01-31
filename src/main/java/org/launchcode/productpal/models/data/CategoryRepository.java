package org.launchcode.productpal.models.data;

import org.launchcode.productpal.models.Category;
import org.launchcode.productpal.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    public long countById(Integer id);
    public Optional<Category> findById (Integer id);
}