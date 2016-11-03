package com.acme.repository;

import java.util.List;

import com.acme.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findById(Long id);

    List<Category> findByName(String name);
}