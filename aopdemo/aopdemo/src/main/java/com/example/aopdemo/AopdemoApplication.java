package com.example.aopdemo;

import com.example.aopdemo.dao.AccountDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AopdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AopdemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AccountDAO accountDAO){
		return runner->{
			demoTheBeoreAdvice(accountDAO);
			demoTheAfterReturningAdvice(accountDAO);
		};
	}

	private void demoTheAfterReturningAdvice(AccountDAO accountDAO) {
		List<Account>accounts=accountDAO.findAccount();
		System.out.println("Main program : demoTheAfterReturningAdvice");
		for(Account account:accounts){
			System.out.println(account);
		}
	}

	private void demoTheBeoreAdvice(AccountDAO accountDAO) {
		accountDAO.addAccount(3,true);
	}
}


