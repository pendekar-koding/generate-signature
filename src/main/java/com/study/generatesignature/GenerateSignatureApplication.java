package com.study.generatesignature;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Tools Generate Signature", version = "1.0"))
public class GenerateSignatureApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenerateSignatureApplication.class, args);
	}

}
