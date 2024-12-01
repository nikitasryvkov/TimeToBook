package com.web.time_to_book.repositories.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.web.time_to_book.models.Category;
import com.web.time_to_book.repositories.CategoryRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CategoryRepositoryImpl extends CRUDRepository<Category> implements CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    protected CategoryRepositoryImpl() {
        super(Category.class);
    }

    @Override
    public List<Category> findAll() {
        return entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }
}
