package com.lsantamaria.currencies

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.slf4j.LoggerFactory
import org.springframework.http.codec.ServerSentEvent
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.reactive.function.client.WebClient


internal class Profile {

	private val id: String? = null

	// <4>
	private val email: String? = null
}

@SpringBootTest
class DemoApplicationTests {

	val log = LoggerFactory.getLogger(this.javaClass);

	@Test
	fun contextLoads() {
		val client = WebClient.create("http://localhost:8080/")
		val type = object : ParameterizedTypeReference<ServerSentEvent<String>>() {
		}

		val eventStream = client.get()
				.uri("/profiles")
				.retrieve()
				.bodyToFlux(Profile::class.java);

		eventStream.subscribe(
				{ content ->
					log.info("Received"+content);
				},
				{ error -> log.error("Error receiving SSE: {}", error) },
				{ log.info("Completed!!!") })

		Thread.sleep(30000);

	}

}
