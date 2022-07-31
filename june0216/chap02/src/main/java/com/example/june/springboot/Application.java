package com.example.june.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication이 있는 위치부터 설정을 읽어감 -> 프로젝트의 최상단 위치
@SpringBootApplication//스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정
public class Application { //프로젝트의 메인 클래스
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args); //내장 WAS를 실행 => 항상 서버에 톰캣을 설치할 필요가 없게 되고 jar파일로 실행하면 된다.
    }
}


