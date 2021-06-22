package com.grsu.guide.repository;

import com.grsu.guide.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PageRepository extends CrudRepository<Page,Long> {
    Optional<Page> findById(Long id);

}