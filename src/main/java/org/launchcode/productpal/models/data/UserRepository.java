package org.launchcode.productpal.models.data;

import org.launchcode.productpal.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    // Special query method to find a user by their username
    User findByUsername(String username);
}