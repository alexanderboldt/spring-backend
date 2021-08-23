package com.alex.notesbackend.features.note

import com.alex.notesbackend.exception.ResourceNotFoundException
import com.alex.notesbackend.repository.note.DbModelNote
import com.alex.notesbackend.repository.note.NoteDao
import com.alex.notesbackend.repository.session.SessionDao
import org.springframework.data.repository.findByIdOrNull
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

    @PostMapping("v1/notes")
    fun create(@RequestBody note: DbModelNote): DbModelNote {
        val date = Date().time
        note.apply {
            createdAt = date
            updatedAt = date
        }
        return noteDao.save(note)
    }

    // read

    @GetMapping("v1/notes")
    fun getAllV1(): ResponseEntity<List<DbModelNote>> {
        val headers = HttpHeaders().apply { add("Deprecated", "true") }
        return ResponseEntity(noteDao.findAll(), headers, HttpStatus.OK)
    }

    @GetMapping("v2/notes")
    fun getAll(
        @RequestParam sort: String?,
        @RequestParam offset: Int?,
        @RequestParam limit: Int?): List<DbModelNote> {

        //sort?.toSortPair(), offset, limit)
        return noteDao.findAll()
    }

    @GetMapping("v1/notes/{id}")
    fun get(@PathVariable id: Long): DbModelNote {
        return noteDao.findByIdOrNull(id) ?: throw ResourceNotFoundException()
    }

    // update

    @PutMapping("v1/notes/{id}")
    fun update(@PathVariable id: Long, @RequestBody noteRequest: DbModelNote): DbModelNote {
        return noteDao
            .findByIdOrNull(id)
            ?.run {
                title = noteRequest.title
                description = noteRequest.description
                updatedAt = Date().time
                noteDao.save(this)
            } ?: throw ResourceNotFoundException()
    }

    // delete

    @DeleteMapping("v1/notes")
    fun deleteAll(): ResponseEntity<Unit> {
        noteDao.deleteAll()
        return ResponseEntity(null, HttpStatus.NO_CONTENT)
    }

    @DeleteMapping("v1/notes/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        noteDao.deleteById(id)
        return ResponseEntity(null, HttpStatus.NO_CONTENT)
    }
}