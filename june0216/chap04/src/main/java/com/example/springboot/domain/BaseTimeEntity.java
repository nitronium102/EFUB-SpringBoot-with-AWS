package com.example.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass//JPA 엔티티 클래스들이 BaseTimeEntity를 상속할 경우 이 필드들도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class)//Auditing기능 포함
public class BaseTimeEntity {

	@CreatedDate//생성될 떄 시간이 자동으로 저장
	private LocalDateTime createdDate;

	@LastModifiedDate//엔티티의 변경 시간 자동 저장
	private LocalDateTime modifiedDate;
}
