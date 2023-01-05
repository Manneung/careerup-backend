package com.manneung.careerup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.annotation.PostConstruct;
import java.io.File;


//@EnableJpaAuditing -> JpaAuditingConfig
@SpringBootApplication
public class CareerupApplication {

	@Value("${spring.environment}")
	private String environment;

	@Value("${spring.file-dir}")
	private String fileDir;

	public static void main(String[] args) {
		SpringApplication.run(CareerupApplication.class, args);

		// 메모리 사용량 출력
		long heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("HEAP Size(M) : "+ heapSize / (1024*1024) + " MB");
	}
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
		return new HiddenHttpMethodFilter();
	}


	@PostConstruct
	private void init() {

		if (environment.equals("local")) {
			String staticFolder = System.getProperty("user.dir") + "/src/main/resources/static";
			mkdirResource(staticFolder);

			String files = System.getProperty("user.dir") + fileDir;
			mkdirResource(files);
		} else if (environment.equals("development")) {
			String filesFolder = "/var/www/html/files";
			mkdirResource(filesFolder);
		}
	}

	/**
	 * @param fileDir 생성을 위한 폴더명
	 * @description 주어진 경로에 폴더를 생성함
	 */
	private static void mkdirResource(String fileDir) {
		File Folder = new File(fileDir);

		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		if (!Folder.exists()) {
			try {
				Folder.mkdir(); //폴더 생성합니다.
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
	}


}
