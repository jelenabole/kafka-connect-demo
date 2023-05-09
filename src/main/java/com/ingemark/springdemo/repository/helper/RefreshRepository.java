package com.ingemark.springdemo.repository.helper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

// note: refresh repository
// extension to the JpaRepository that enables object refresh with entity manager
// refresh is used to fetch missing data (like child objects) after save or update
@NoRepositoryBean
public interface RefreshRepository<T, K> extends JpaRepository<T, K> {

    void refresh(T t);

}