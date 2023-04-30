package com.techtest.test.repos;

import org.springframework.data.repository.CrudRepository;

import com.techtest.test.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
}
