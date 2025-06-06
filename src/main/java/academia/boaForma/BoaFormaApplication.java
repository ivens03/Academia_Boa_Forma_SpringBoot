package academia.boaForma;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Academia Boa Forma",
		version = "1.0.0",
		description = "API de cadastro de alunos e pagamentos"
))
public class BoaFormaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoaFormaApplication.class, args);
	}

}
