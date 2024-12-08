package com.web.time_to_book.repositories.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.web.time_to_book.models.ServiceProduct;
import com.web.time_to_book.repositories.ServiceProductRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ServiceProductRepositoryImpl extends CRUDRepository<ServiceProduct> implements ServiceProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ServiceProductRepositoryImpl() {
        super(ServiceProduct.class);
    }

    @Override
    public List<ServiceProduct> findAll() {
        return entityManager.createQuery("SELECT s FROM ServiceProduct s", ServiceProduct.class).getResultList();
    }
}
