package ds.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthApplication {

//	@Autowired
//	public PatientActivityRepository patientActivityRepository;
//
//	@PostConstruct
//	public void init() {
//		PatientEntity patientEntity = new PatientEntity();
//		patientEntity.setId(55);
//		patientActivityRepository.findByPatientEntity(patientEntity).forEach(elem -> System.out.println(elem.getId()));
//	}

    public static void main(String[] args) {
        SpringApplication.run(HealthApplication.class, args);
    }

}
