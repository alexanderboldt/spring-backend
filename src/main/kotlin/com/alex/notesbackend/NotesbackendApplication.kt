package com.alex.notesbackend

import com.alex.notesbackend.repository.note.DbModelNote
import com.alex.notesbackend.repository.note.NoteDao
import com.alex.notesbackend.repository.user.DbModelUser
import com.alex.notesbackend.repository.user.UserDao
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class NotesbackendApplication(
	noteDao: NoteDao,
	userDao: UserDao) {

	init {
		// seeds

		val date = Date().time

		userDao.saveAll(listOf(
			DbModelUser(0, "hal", "hal"),
			DbModelUser(1, "bruce", "bruce"),
			DbModelUser(2, "barbara", "barbara")
		))

		noteDao.saveAll(listOf(
			DbModelNote(1, "Einkaufen", "Gemüse nicht vergessen", date, date),
			DbModelNote(2, "Aufräumen", "Grundreinigung", date, date),
			DbModelNote(3, "Abwaschen", null, date, date),
			DbModelNote(4, "Tanken", "Diesel", date, date),
			DbModelNote(5, "Blumen gießen", "Dünger nicht vergessen", date, date)
		))
	}


}

fun main(args: Array<String>) {
	runApplication<NotesbackendApplication>(*args)
}
