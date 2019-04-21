package ar.edu.unq.grupol.app;

import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ar.edu.unq.grupol.app.service.MoneyExternalService;
import ar.edu.unq.grupol.app.service.MoneyLoanService;

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
		System.out.println("HOLAAAA ENTROOO");
	}
	
}