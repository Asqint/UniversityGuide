package com.grsu.guide.repository;

import com.grsu.guide.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;


@Repository
public interface PageRepository extends CrudRepository<Page,Long> {

    Optional<Page> findById(Long id);

    List<Page> findPagesByNamePageContainsIgnoreCase(String namePageContains);

    Iterable<Page> findPagesByParentPageId(Long id);

    void deletePagesByParentPageId(Long aLong);
}