package com.health.application

import com.health.application.repository.UserRepository
import com.health.application.repository.UserRoleRepository
import com.health.application.soa.CaregiverClient
import com.health.application.soa.DoctorClient
import ds.health.soa.soa_endpoints.GetPatientActivityResponse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@SpringBootApplication
//@ComponentScan(basePackageClasses = [UserController::class])
class OnlineMedicationPlatformApplication {

//    @Autowired
//    lateinit var userRoleRepository: UserRoleRepository
//
//    @Autowired
//    private lateinit var userRepository: UserRepository
//
//    @Autowired
//    private lateinit var doctorClient: DoctorClient
//
//    @Autowired
//    private lateinit var caregiverClient: CaregiverClient

    @PostConstruct
    fun init() {
//        userRoleRepository.save(UserRoleEntity(roleName = RoleEnum.DOCTOR))
//        userRoleRepository.save(UserRoleEntity(roleName = RoleEnum.PATIENT))
//        userRoleRepository.save(UserRoleEntity(roleName = RoleEnum.CAREGIVER))

//        userRepository.save(UserEntity(
//                userRole = UserRoleEntity(id = 102),
//                personName = "mihai",
//                username = "mihai",
//                password = BCryptPasswordEncoder().encode("mihai"),
//                birthDate = Date(0),
//                address = "a",
//                email = "a",
//                gender = GenderEnum.MALE
//        ))
//        var response: GetPatientActivityResponse = GetPatientActivityResponse()
//        doctorClient.getPatientActivity(55).patientActivityList.patientActivity.forEach {
//            println(it.id)
//        }
//
//        println(caregiverClient.getPatientRecommendation(55).recommendation)
    }
}

fun main(args: Array<String>) {
    runApplication<OnlineMedicationPlatformApplication>(*args)
}
