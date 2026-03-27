package com.example.library.project.demo.service;

import com.example.library.project.demo.entity.Review;
import com.example.library.project.demo.exception.ReviewException;
import com.example.library.project.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public Review addReview(Review review) {
        if (review == null){
            throw ReviewException.create("Cannot add null review.");
        }
        return reviewRepository.save(review);
    }

    public Iterable<Review> getAllReviews() {
        Iterable<Review> reviews = reviewRepository.findAll();
        if (!reviews.iterator().hasNext()){
            throw ReviewException.create("No reviews found");
        }
        return reviews;
    }

    public Review getReviewById(Integer reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> ReviewException.create("Review with ID " + reviewId + " not found"));
    }

    @Transactional
    public void deleteReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                        .orElseThrow(() -> ReviewException.create("Cannot delete a non existing review."));
        reviewRepository.delete(review);
    }

    @Transactional
    public Review updateReview(Integer reviewId, Review updatedReview) {
        if (updatedReview == null){
            throw ReviewException.create("Updated review cannot be null");
        }
        return reviewRepository.findById(reviewId)
                .map(review -> {
                    review.setRating(updatedReview.getRating());
                    review.setComment(updatedReview.getComment());
                    review.setReviewDate(updatedReview.getReviewDate());
                    review.setBook(updatedReview.getBook());
                    review.setUser(updatedReview.getUser());
                    return reviewRepository.save(review);
                })
                .orElseThrow(() -> ReviewException.create("Review not found"));
    }

    public List<Review> getReviewsByBook(Integer bookId) {
        List<Review> reviews = reviewRepository.findByBook_BookId(bookId);
        if (reviews.isEmpty()){
            throw ReviewException.create("No reviews found for book with ID " + bookId);
        }
        return reviews;
    }

    public List<Review> getReviewsByUser(Integer userId) {
        List<Review> reviews = reviewRepository.findByUser_UserId(userId);
        if (reviews.isEmpty()){
            throw ReviewException.create("User with ID " + userId + "did not write any reviews");
        }
        return reviews;
    }
}