package com.spring.efub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
// 해당 annotation이 있는 위치부터 설정을 읽어가기 때문에 프로젝트의 최상단에 있어야 함
@SpringBootApplication // 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 자동으로 설정
public class Application {
	public static void main(String[] args){
		SpringApplication.run(Application.class, args); // 내장 WAS 실행 : 언제 어디서나 같은 환경에서 스프링 부트 배포 가능
	}
}
