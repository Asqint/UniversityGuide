package com.grsu.guide.repository;

import com.grsu.guide.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PageRepository extends CrudRepository<Page,Long> {
    Page findByNamePage(String namePage);

}