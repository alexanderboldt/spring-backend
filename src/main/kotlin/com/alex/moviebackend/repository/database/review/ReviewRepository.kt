package com.alex.moviebackend.repository.database.review

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

interface ReviewRepository : CrudRepository<DbModelReview, Long> {

    // read

    fun findAllByUserId(userId: Long): List<DbModelReview>

    fun findAllByMovieId(movieId: Long): List<DbModelReview>

    fun findByIdAndUserId(id: Long, userId: Long): DbModelReview?

    // delete

    @Transactional
    @Modifying
    fun deleteByMovieId(movieId: Long): Int
}