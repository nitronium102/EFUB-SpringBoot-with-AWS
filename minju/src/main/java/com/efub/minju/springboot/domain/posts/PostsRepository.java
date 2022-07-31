package com.efub.minju.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

//Dao라고도 불리며 JPA에서는 Repository
//@Repository 어노테이션 추가하지 않아도 됨
//Entity와 Repository는 함께 위치 -> 둘이 밀접한 관계이며, Entity는 Repository가 있어야 제대로 역할하기 때문에
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
