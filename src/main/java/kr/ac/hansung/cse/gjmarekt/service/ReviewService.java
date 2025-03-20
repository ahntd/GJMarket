package kr.ac.hansung.cse.gjmarekt.service;

import kr.ac.hansung.cse.gjmarekt.entity.GJUser;
import kr.ac.hansung.cse.gjmarekt.entity.Post;
import kr.ac.hansung.cse.gjmarekt.entity.Review;
import kr.ac.hansung.cse.gjmarekt.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserService userService, PostService postService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.postService = postService;
    }

    // 리뷰 남기기
    public Review createReview(Integer postId, Integer reviewerId, Integer revieweeId,
                               Integer rating, String comment) {
        if (reviewerId.equals(revieweeId)) {
            throw new IllegalArgumentException("자신에게 리뷰를 남길 수 없습니다.");
        }

        Post post = postService.getPostById(postId);
        GJUser reviewer = userService.getUserById(reviewerId);
        GJUser reviewee = userService.getUserById(revieweeId);

        Review review = new Review();
        review.setPost(post);
        review.setReviewer(reviewer);
        review.setReviewee(reviewee);
        review.setRating(rating);
        review.setComment(comment);

        Review savedReview = reviewRepository.save(review);
        updateUserRating(revieweeId);

        return savedReview;
    }

    // 유저의 점수 업데이트
    private void updateUserRating(Integer userId) {
        List<Review> reviews = reviewRepository.findByRevieweeId(userId);
        if (reviews.isEmpty()) {
            return;
        }

        double averageRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0);

        userService.updateUserRating(userId, (float) averageRating);
    }

    // 유저가 받은 리뷰 목록
    public List<Review> getReviewsByRevieweeId(Integer revieweeId) {
        return reviewRepository.findByRevieweeId(revieweeId);
    }

    // 유저가 보낸 리뷰 목록
    public List<Review> getReviewsByReviewerId(Integer reviewerId) {
        return reviewRepository.findByReviewerId(reviewerId);
    }
}
