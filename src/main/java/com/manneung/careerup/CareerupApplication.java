package com.manneung.careerup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


//@EnableJpaAuditing -> JpaAuditingConfig
@SpringBootApplication
public class CareerupApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerupApplication.class, args);

		// 메모리 사용량 출력
		long heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("HEAP Size(M) : "+ heapSize / (1024*1024) + " MB");
	}


}
