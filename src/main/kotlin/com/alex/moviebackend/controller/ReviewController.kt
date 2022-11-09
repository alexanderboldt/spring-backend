package com.alex.moviebackend.controller

import com.alex.moviebackend.exception.ResourceNotFoundException
import com.alex.moviebackend.repository.api.review.ApiModelReviewGet
import com.alex.moviebackend.repository.api.review.ApiModelReviewPut
import com.alex.moviebackend.repository.database.review.ReviewRepository
import com.alex.moviebackend.repository.mapping.toApiModelGet
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/reviews")
class ReviewController(private val reviewRepository: ReviewRepository) {

    private val textReviewNotFoundWithGivenId = "Review with the given id not found!"

    // create

    @GetMapping
    fun getReviews(@RequestAttribute userId: Long): List<ApiModelReviewGet> {
        return reviewRepository.findAllByUserId(userId).toApiModelGet()
    }

    // update

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody request: ApiModelReviewPut,
        @RequestAttribute userId: Long
    ): ApiModelReviewGet {
        val review = reviewRepository.findByIdAndUserId(id, userId) ?: throw ResourceNotFoundException(textReviewNotFoundWithGivenId)

        return reviewRepository.save(
            review.copy(description = request.description ?: review.description)
        ).toApiModelGet()
    }

    // delete

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteReview(@PathVariable("id") id: Long, @RequestAttribute userId: Long) {
        reviewRepository.findByIdAndUserId(id, userId) ?: throw ResourceNotFoundException(textReviewNotFoundWithGivenId)
        reviewRepository.deleteById(id)
    }
}