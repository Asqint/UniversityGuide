package com.grsu.guide.repository;

import com.grsu.guide.domain.Template;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TemplateRepository extends CrudRepository<Template,Long> {
    List<Template> findTemplatesByUserId(Long id);
}
