package ds.health.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/soa/*");
    }

    @Bean(name = "patientActivity")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema patientActivitySchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("PatientActivityPort");
        wsdl11Definition.setLocationUri("/patient_activity");
        wsdl11Definition.setTargetNamespace("http://health.ds/soa/soa-endpoints");
        wsdl11Definition.setSchema(patientActivitySchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema patientActivitySchema() {
        return new SimpleXsdSchema(new ClassPathResource("patient_activity.xsd"));
    }

    @Bean(name = "patientRecommendation")
    public DefaultWsdl11Definition messageDefinition(XsdSchema patientRecommendationSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("PatientRecommendationPort");
        wsdl11Definition.setLocationUri("/patient_recommendation");
        wsdl11Definition.setTargetNamespace("http://health.ds/soa/soa-endpoints");
        wsdl11Definition.setSchema(patientRecommendationSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema patientRecommendationSchema() {
        return new SimpleXsdSchema(new ClassPathResource("recommendation.xsd"));
    }

    @Bean(name = "medicationIntake")
    public DefaultWsdl11Definition medicationIntakeDefinition(XsdSchema medicationIntakeSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("MedicationIntakePort");
        wsdl11Definition.setLocationUri("/medication_intake");
        wsdl11Definition.setTargetNamespace("http://health.ds/soa/soa-endpoints");
        wsdl11Definition.setSchema(medicationIntakeSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema medicationIntakeSchema() {
        return new SimpleXsdSchema(new ClassPathResource("medication_intake.xsd"));
    }
}

