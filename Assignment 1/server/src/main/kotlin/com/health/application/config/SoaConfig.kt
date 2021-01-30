package com.health.application.config

import com.health.application.soa.CaregiverClient
import com.health.application.soa.DoctorClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.oxm.jaxb.Jaxb2Marshaller


@Configuration
class SoaConfig {

    @Bean
    fun marshaller(): Jaxb2Marshaller {
        val marshaller = Jaxb2Marshaller()
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.contextPath = "ds.health.soa.soa_endpoints"
        return marshaller
    }

    @Bean
    fun doctorClient(marshaller: Jaxb2Marshaller): DoctorClient {
        val client = DoctorClient()
        client.setDefaultUri("http://localhost:8081/soa")
        client.setMarshaller(marshaller)
        client.setUnmarshaller(marshaller)
        return client
    }

    @Bean
    fun caregiverClient(marshaller: Jaxb2Marshaller): CaregiverClient {
        val client = CaregiverClient()
        client.setDefaultUri("http://localhost:8082/soa")
        client.setMarshaller(marshaller)
        client.setUnmarshaller(marshaller)
        return client
    }

}