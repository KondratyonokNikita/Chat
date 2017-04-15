package com.up.repository;

import com.up.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Samsung on 15.04.2017.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String Name);
}
