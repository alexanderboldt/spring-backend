package com.alex.notesbackend.features.note

import com.alex.notesbackend.exception.ResourceNotFoundException
import com.alex.notesbackend.features.note.model.NoteCreateRequest
import com.alex.notesbackend.features.note.model.NotePutRequest
import com.alex.notesbackend.repository.note.DbModelNote
import com.alex.notesbackend.repository.note.NoteDao
import com.alex.notesbackend.repository.session.SessionDao
import com.alex.notesbackend.utils.toSortPairs
import org.springframework.data.domain.Sort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class NoteController(
    private val noteDao: NoteDao,
    private val sessionDao: SessionDao) {

    // create

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("v1/notes")
    fun create(
        @RequestHeader("Authorization") token: String,
        @RequestBody noteRequest: NoteCreateRequest,
        @RequestAttribute("user_id") userId: Long): DbModelNote {
        return Date()
            .time
            .let { date ->
                DbModelNote(
                    0,
                    userId,
                    noteRequest.title,
                    noteRequest.description,
                    date,
                    date)
            }.let { noteDb -> noteDao.save(noteDb) }
    }

    // read

    @GetMapping("v1/notes")
    fun getAllV1(): ResponseEntity<List<DbModelNote>> {
        return HttpHeaders()
            .apply { add("Deprecated", "true") }
            .let { headers -> ResponseEntity(noteDao.findAll(), headers, HttpStatus.OK) }
    }

    @GetMapping("v2/notes")
    fun getAll(
        @RequestParam sort: String?,
        @RequestParam offset: Int?,
        @RequestParam limit: Int?,
        @RequestAttribute("user_id") userId: Long): List<DbModelNote> {

        val sort = sort
            ?.toSortPairs()
            ?.map { (sortString, isAscending) ->
                Sort.Order(if (isAscending) Sort.Direction.ASC else Sort.Direction.DESC, sortString)
            }.let { sortList -> Sort.by(sortList ?: emptyList()) }

        return noteDao
            .findAllByUserId(userId, sort)
            .let { notes -> if (offset != null && offset >= 1) notes.drop(offset) else notes }
            .let { notes -> if (limit != null && limit >= 1) notes.take(limit) else notes }
    }

    @GetMapping("v1/notes/{id}")
    fun get(@PathVariable id: Long, @RequestAttribute("user_id") userId: Long): DbModelNote {
        return noteDao.findByIdAndUserId(id, userId) ?: throw ResourceNotFoundException("Note not found")
    }

    // update

    @PutMapping("v1/notes/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody noteRequest: NotePutRequest,
        @RequestAttribute("user_id") userId: Long): DbModelNote {
        return noteDao
            .findByIdAndUserId(id, userId)
            ?.run {
                if (noteRequest.title != null) title = noteRequest.title
                if (noteRequest.description != null) description = noteRequest.description
                updatedAt = Date().time
                noteDao.save(this)
            } ?: throw ResourceNotFoundException("Note not found")
    }

    // delete

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("v1/notes")
    fun deleteAll(@RequestAttribute("user_id") userId: Long) {
        noteDao.deleteByUserId(userId)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("v1/notes/{id}")
    fun delete(@PathVariable id: Long, @RequestAttribute("user_id") userId: Long) {
        noteDao.deleteByIdAndUserId(id, userId)
    }
}