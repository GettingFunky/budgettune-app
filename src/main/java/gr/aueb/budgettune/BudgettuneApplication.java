package gr.aueb.budgettune;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "BudgetTune API",
				version = "1.0",
				description = "API for managing transactions and users"
		)
)

@SpringBootApplication
public class BudgettuneApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgettuneApplication.class, args);
	}

}
