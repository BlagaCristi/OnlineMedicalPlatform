package health.app.model

import java.sql.Timestamp

data class Activity (
        var patientId: Int,
        var startTime: Timestamp,
        var endTime: Timestamp,
        var activity: String
)