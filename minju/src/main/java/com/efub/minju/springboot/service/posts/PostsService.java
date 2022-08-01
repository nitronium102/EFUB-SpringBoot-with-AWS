package com.efub.minju.springboot.service.posts;

import com.efub.minju.springboot.domain.posts.Posts;
import com.efub.minju.springboot.domain.posts.PostsRepository;
import com.efub.minju.springboot.web.dto.PostsListResponseDto;
import com.efub.minju.springboot.web.dto.PostsResponseDto;
import com.efub.minju.springboot.web.dto.PostsSaveRequestDto;
import com.efub.minju.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor //생성자 주입 -> 롬복이 대신 생성
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
         Posts posts = postsRepository.findById(id)
                 .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

         posts.update(requestDto.getTitle(), requestDto.getContent());

         return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        //postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto 변환 -> List로
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // .map(posts -> new PostsListResponseDto(posts)) 와 같음
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        postsRepository.delete(posts);
    }
}
