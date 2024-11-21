package com.PetSitter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PetSitterApplication

fun main(args: Array<String>) {
	runApplication<PetSitterApplication>(*args)
}
