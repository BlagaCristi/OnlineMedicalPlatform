package health.app.cronjob

import health.app.model.Activity
import health.app.producer.Producer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.FileReader
import java.sql.Timestamp
import javax.annotation.PostConstruct

@Component
class Cronjob {

    @Autowired
    private lateinit var producer: Producer

    private lateinit var bufferedReader: BufferedReader

    @PostConstruct
    private fun init() {
        try {
            bufferedReader = BufferedReader(FileReader("activity.txt"))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    @Scheduled(cron = "*/10 * * * * * ")
    fun cronJobSch() {
        val line: String? = bufferedReader.readLine()
        if (line != null) {
            producer.produce(convertLine(line))
        }
    }

    private fun convertLine(line: String): Activity {
        var splittedLine: List<String> = line.split("\\s+".toRegex())
        var patientId: Int = splittedLine[0].toInt()
        var startTime: Timestamp = Timestamp.valueOf(splittedLine[1] + " " + splittedLine[2])
        var endTime: Timestamp = Timestamp.valueOf(splittedLine[3] + " " + splittedLine[4])
        var activity: String = splittedLine[5]
        return Activity(
                patientId = patientId,
                startTime = startTime,
                endTime = endTime,
                activity = activity
        )
    }
}