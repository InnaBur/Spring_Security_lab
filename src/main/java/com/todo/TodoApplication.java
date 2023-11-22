package com.todo;

import com.todo.config.JwtService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ToDo", version = "1.1", description = "ToDoList API"))
@SecurityScheme(name = "ToDoAPI", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)

public class TodoApplication {
	private static final Logger logger = LoggerFactory.getLogger(TodoApplication.class);
	public static void main(String[] args) {
//		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//		String secretString = Encoders.BASE64.encode(key.getEncoded());
//		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//		String secretString = Encoders.BASE64.encode(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
//
//		logger.info("Secret key: " + secretString);
//		logger.info("Secret key: " + secretString);
		SpringApplication.run(TodoApplication.class, args);
	}

}
