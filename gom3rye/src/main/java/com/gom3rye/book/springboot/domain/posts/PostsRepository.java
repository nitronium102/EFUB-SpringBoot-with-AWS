package com.gom3rye.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    // JpaRepository<Entity 클래스, pk타입>을 상속하면 기본적인 cRUD 메소드 자동으로 생성됨
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC") // SpringDataJpa에서 제공하지 않는 메소드는 위처럼 쿼리로 작성해도 된다.
    List<Posts> findAllDesc();
}