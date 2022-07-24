package com.efub.minju.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //상속 시 BaseTimeEntity도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class) //auditing 기능
public class BaseTimeEntity {

    @CreatedDate //Entity 저장시 시간 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate //조회한 Entity 변경 시 시간 자동 저장
    private LocalDateTime modifiedDate;
}
