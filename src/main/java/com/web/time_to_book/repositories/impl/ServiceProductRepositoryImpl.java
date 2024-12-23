package com.web.time_to_book.repositories.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.web.time_to_book.models.ServiceProduct;
import com.web.time_to_book.models.enums.AppointmentStatusEnum;
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

    @Override
    public List<ServiceProduct> findByCategoryId(UUID id) {
        return entityManager
                .createQuery("SELECT s FROM ServiceProduct s WHERE s.category.id = :ids", ServiceProduct.class)
                .setParameter("ids", id).getResultList();
    }

    @Override
    public List<ServiceProduct> findByStatusAndUserId(UUID id, AppointmentStatusEnum status) {
        return entityManager
                .createQuery(
                        "SELECT s FROM ServiceProduct s JOIN Appointment a ON a.service = s WHERE a.status = :status AND a.client.id = :id",
                        ServiceProduct.class)
                .setParameter("id", id).setParameter("status", status).getResultList();
    }

    @Override
    public List<ServiceProduct> findAllByMaster(UUID id) {
        return entityManager
                .createQuery("SELECT s FROM ServiceProduct s WHERE s.createdBy.id = :id", ServiceProduct.class)
                .setParameter("id", id).getResultList();
    }
}
