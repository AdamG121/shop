package com.adam.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableJpaRepositories (repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class) // włącznik do generowania tabelek audytowych i automatycznych insertów do tabelek w work bench
@EnableJpaAuditing //dzięki tej adnotacji będzie działał auditing w całej aplikacji - włącznik
@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

}
