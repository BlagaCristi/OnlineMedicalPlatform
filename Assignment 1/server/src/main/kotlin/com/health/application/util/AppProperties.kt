package com.health.application.util

class AppProperties {

    companion object {

        const val USER_ROLE_PATH = "/user-role"
        const val USER_PATH = "/user"
        const val PATIENT_PATH = "/patient"
        const val MEDICATION_PATH = "/medication"
        const val MEDICATION_PLAN_PATH = "/medication-plan"
        const val MEDICATION_PLAN_DRUGS_PATH = "/medication-plan-drugs"
        const val MEDICATION_INTAKE_PATH = "/medication-intake"
        const val DOCTOR_PATH = "/doctor"
        const val CAREGIVER_PATH = "/caregiver"
        const val HEALTH_PATH = "/health"
        const val WS_PATH = "/ws"

        const val ADMIN = "ADMIN"
        const val DOCTOR = "DOCTOR"
        const val CAREGIVER = "CAREGIVER"
        const val PATIENT = "PATIENT"

        const val ACTIVITY_LEAVING = "Leaving"
        const val ACTIVITY_TOILETING = "Toileting"
        const val ACTIVITY_SHOWERING = "Showering"
        const val ACTIVITY_SLEEPING = "Sleeping"
        const val ACTIVITY_BREAKFAST = "Breakfast"
        const val ACTIVITY_LUNCH = "Lunch"
        const val ACTIVITY_DINNER = "Dinner"
        const val ACTIVITY_SNACK = "Snack"
        const val ACTIVITY_SPARE_TIME_TV = "Spare_Time/TV"
        const val ACTIVITY_GROOMING = "Grooming"

        const val ONE_HOUR: Long = 1000 * 60 * 60
        const val TWELVE_HOURS: Long = 12 * ONE_HOUR
    }
}