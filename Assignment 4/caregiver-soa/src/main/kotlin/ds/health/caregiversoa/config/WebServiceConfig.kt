package ds.health.caregiversoa.config

import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.ws.config.annotation.EnableWs
import org.springframework.ws.config.annotation.WsConfigurerAdapter
import org.springframework.ws.transport.http.MessageDispatcherServlet
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition
import org.springframework.xml.xsd.SimpleXsdSchema
import org.springframework.xml.xsd.XsdSchema

@EnableWs
@Configuration
class WebServiceConfig : WsConfigurerAdapter() {
    @Bean
    fun messageDispatcherServlet(applicationContext: ApplicationContext): ServletRegistrationBean<*> {
        val servlet = MessageDispatcherServlet()
        servlet.setApplicationContext(applicationContext)
        servlet.isTransformWsdlLocations = true
        return ServletRegistrationBean(servlet, "/soa/*")
    }

    @Bean(name = ["patientRecommendation"])
    fun defaultWsdl11Definition(patientRecommendationSchema: XsdSchema): DefaultWsdl11Definition {
        val wsdl11Definition = DefaultWsdl11Definition()
        wsdl11Definition.setPortTypeName("PatientRecommendationPort")
        wsdl11Definition.setLocationUri("/patient_recommendation")
        wsdl11Definition.setTargetNamespace("http://health.ds/soa/soa-endpoints")
        wsdl11Definition.setSchema(patientRecommendationSchema)
        return wsdl11Definition
    }

    @Bean
    fun patientRecommendationSchema(): XsdSchema {
        return SimpleXsdSchema(ClassPathResource("patient_recommendation.xsd"))
    }

}

