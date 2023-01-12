package org.launchcode.productpal.models.data;

import org.launchcode.productpal.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    // Special query method to find a user by their username
    @Query
    User findByUsername(String username);

    @Query
    public User findByEmail(String email);

    @Query
    public User findByResetPasswordToken(String token);
}