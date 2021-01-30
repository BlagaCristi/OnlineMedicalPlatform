package health.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class AppApplication

fun main(args: Array<String>) {
	runApplication<AppApplication>(*args)
}
