package com.gom3rye.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //JPA 엔티티 클래스들이 BaseTimeEntity를 상속할 경우 createdDate, modifiedDate도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class) // Auditing 기능도 포함시킨다.
public abstract class BaseTimeEntity {

    @CreatedDate //생성될 떄 시간이 자동으로 저장
    private LocalDateTime createdDate;

    @LastModifiedDate //엔티티의 변경 시간 자동 저장
    private LocalDateTime modifiedDate;
}
