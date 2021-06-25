package com.grsu.guide.service;

import com.grsu.guide.domain.Feedback;
import com.grsu.guide.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public void saveFeedback(Feedback feedback){
        feedbackRepository.save(feedback);
    }
}
