package com.ingemark.springdemo.repository.helper;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

// note: refresh repository
@NoRepositoryBean
public class RefreshRepositoryImpl<T, K> extends SimpleJpaRepository<T, K> implements RefreshRepository<T, K> {

    private final EntityManager entityManager;

    public RefreshRepositoryImpl(JpaEntityInformation<T, K> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void refresh(T t) {
        // refresh is used to fetch missing data after save/update (child objects)
        // requires flush, to apply previously saved changes
        entityManager.flush();
        entityManager.refresh(t);
    }
}