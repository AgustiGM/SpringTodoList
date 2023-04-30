package com.techtest.test.repos;

import com.techtest.test.entities.TODO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TODORepository extends CrudRepository<TODO,Long> {
    List<TODO> findByTitle(String title);

    Page<TODO> findByUser_Username(String username, Pageable pageable);



    Page<TODO> findByTitleContainsIgnoreCase(String title, Pageable pageable);

    Page<TODO> findAll(Pageable pageable);





}
