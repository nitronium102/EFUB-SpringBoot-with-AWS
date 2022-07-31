package com.example.springboot.service.posts;



import com.example.springboot.domain.posts.Posts;
import com.example.springboot.domain.posts.PostsRepository;
import com.example.springboot.web.dto.PostsListResponseDto;
import com.example.springboot.web.dto.PostsResponseDto;
import com.example.springboot.web.dto.PostsSaveRequestDto;
import com.example.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor //final로 선언된 모든 필드를 인자값으로 하는 생성자를 생성 -> 의존관계 변경돼도 생성자코드에 손대지 않아도 된다.
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
	public void delete(Long id)
	{
		//존재하는 게시물인지 확인하고 삭제
		Posts posts = postsRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("해당 게시물이 없습니다. id=" + id));

		postsRepository.delete(posts);

	}

	@Transactional
	public PostsResponseDto findById(Long id)
	{
		Posts entity = postsRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		return new PostsResponseDto(entity);
	}

	@Transactional(readOnly = true) //조회 속도 개선 -> 조회만 하는 서비스 메소드에서 사용하는 것 추천
	public List<PostsListResponseDto> findAllDesc()
	{
		return postsRepository.findAllDesc().stream()
				.map(PostsListResponseDto::new)//Stream을 PostsListResponseDto로 변환
				.collect(Collectors.toList());//dto를 리스트로 변환
	}
}
