package br.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/desligar")
@Api(value = "Desligar serviço", tags = "Desligar", description = " Desligar serviço.")
public class DesligarController {

	@Autowired
	ConfigurableWebApplicationContext context;

	    @ApiOperation (
	    		value = "Desligar aplicação",
	    		notes = "Serviço que desliga a aplicação.")
	    @GetMapping("/desligar")
	    public void shutdown() {
	        TaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
	        taskExecutor.execute(() -> {
	            context.close();
	            System.exit(0);
	        });
	    }
}
