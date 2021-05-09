package com.grsu.guide.repository;

import com.grsu.guide.domain.Element;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElementRepository extends CrudRepository<Element,Long> {
    Optional<Element> findById(Long id);
}
