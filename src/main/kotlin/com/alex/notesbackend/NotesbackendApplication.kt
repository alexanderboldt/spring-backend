package com.alex.notesbackend

import com.alex.notesbackend.repository.user.DbModelUser
import com.alex.notesbackend.repository.user.UserDao
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NotesbackendApplication(userDao: UserDao) {

	init {
		// seeds

		userDao.saveAll(listOf(
			DbModelUser(1, "hal", "123456"),
			DbModelUser(2, "bruce", "123456"),
			DbModelUser(3, "barbara", "123456")
		))
	}
}

fun main(args: Array<String>) {
	runApplication<NotesbackendApplication>(*args)
}
