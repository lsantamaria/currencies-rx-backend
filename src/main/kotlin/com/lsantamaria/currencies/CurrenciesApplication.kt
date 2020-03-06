package com.lsantamaria.currencies

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan


@SpringBootApplication
class CurrenciesApplication

fun main(args: Array<String>) {
	runApplication<CurrenciesApplication>(*args)
}
