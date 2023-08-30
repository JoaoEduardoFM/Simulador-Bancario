package br.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ConfigurableWebApplicationContext;

@Service
public class DesligarService {

	@Autowired
	ConfigurableWebApplicationContext context;

	public void shutdown() {
		TaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		taskExecutor.execute(() -> {
			context.close();
			System.exit(0);
		});
	}
}
