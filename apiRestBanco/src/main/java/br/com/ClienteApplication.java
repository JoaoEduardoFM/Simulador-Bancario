package br.com;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClienteApplication {

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled(true);
		return modelMapper;
	}


	public static void main(String[] args) {
		SpringApplication.run(ClienteApplication.class, args);
		System.err.println(" {Bem-vindo à nossa API bancária! \n "
		   		+ "Para acessar as funcionalidades acesse o swagger no seguinte link. \n "
		   		+ "http://localhost:8080/swagger-ui.html#/}");
	}

}
