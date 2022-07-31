package com.example.june.springboot.web.dto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter//선언된 모든 필드의 get메소드를 생성
@RequiredArgsConstructor //필드의 생성자를 자동 주입한다.
public class HelloResponseDto {

    private final String name;
    private final int amount;
}
