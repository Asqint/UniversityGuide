package com.grsu.guide.repository;

import com.grsu.guide.domain.Feedback;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends CrudRepository<Feedback,Long> {
}
