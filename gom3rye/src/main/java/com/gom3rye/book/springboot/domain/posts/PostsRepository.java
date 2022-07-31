package com.gom3rye.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
public interface PostsRepository extends JpaRepository<Posts, Long> {
    // JpaRepository<Entity 클래스, pk타입>을 상속하면 기본적인 cRUD 메소드 자동으로 생성됨
}