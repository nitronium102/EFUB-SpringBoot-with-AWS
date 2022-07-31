package com.gom3rye.book.springboot.service.posts;

import com.gom3rye.book.springboot.domain.posts.Posts;
import com.gom3rye.book.springboot.domain.posts.PostsRepository;
import com.gom3rye.book.springboot.web.dto.PostsListResponseDto;
import com.gom3rye.book.springboot.web.dto.PostsResponseDto;
import com.gom3rye.book.springboot.web.dto.PostsSaveRequestDto;
import com.gom3rye.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor // final로 선언된 모든 필드를 인자값으로 하는 생성자를 대신 생성 -> 의존관계 변경돼도 생성자코드에 손대지 않아도 된다.
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto)
    {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto)
    {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public void delete(Long id) // JpaRepository에서 이미 delete 메소드를 지원하고 있으니 활용한다.
    {
        //존재하는 게시물인지 확인하고 삭제
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시물이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    public PostsResponseDto findById(Long id)
    {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }
    @Transactional(readOnly = true) // 조회 기능만 남겨 조회 속도가 개선되기 때문에 등록, 수정, 삭제 기능이 전혀 없는 서비스 메소드에서 사용하는 걸 추천
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }
}
