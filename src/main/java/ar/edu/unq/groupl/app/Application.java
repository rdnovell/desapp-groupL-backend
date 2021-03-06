package ar.edu.unq.groupl.app;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import ar.edu.unq.groupl.app.service.MoneyExternalService;
import ar.edu.unq.groupl.app.service.MoneyLoanService;

@EnableScheduling
@SpringBootApplication
public class Application {
	
	@Autowired
    private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@PostConstruct
	public void postConstructSpringApp() {
		MoneyExternalService moneyExternalService = applicationContext.getBean(MoneyExternalService.class);
		MoneyLoanService moneyLoanService = applicationContext.getBean(MoneyLoanService.class);
		moneyExternalService.addObserver(moneyLoanService);
	}
	
}