package com.efub.minju.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//Dao라고도 불리며 JPA에서는 Repository
//@Repository 어노테이션 추가하지 않아도 됨
//Entity와 Repository는 함께 위치 -> 둘이 밀접한 관계이며, Entity는 Repository가 있어야 제대로 역할하기 때문에
public interface PostsRepository extends JpaRepository<Posts, Long> {
    //조회 : querydsl, jooq, MyBatis -> querydsl 추천
    //등록, 수정, 삭제 : SpringDataJpa
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
