package com.example.springboot.config.auth;

import com.example.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@RequiredArgsConstructor//final이나 NotNull 생성자 자동 생성
@EnableWebSecurity//시큐리티 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
			.csrf().disable() // csrf 보호기능을 해제
			.headers().frameOptions().sameOrigin() // h2-console화면을 사용하기 위한 처리
			.and()
			.authorizeRequests() // URL 별로 권한 관리를 설정하는 옵션의 시작점
			.antMatchers("/", "/css/**", "/images/**",
					"/js/**", "/h2-console/**").permitAll()
			// 권한 관리 대상 지정, URL와 HTTP 메소드 별로 관리가 가능하다.
			.antMatchers("/api/v1/**").hasRole(Role.USER.name())
			// 해당 자원은 User권한 가진 사람만 가능
			.anyRequest().authenticated() // 나머지 요청은 권한 인증된 사용자에게만 공개(로그인 시 공개)
			.and()
			.logout() // 로그아웃 설정 진입점
			.logoutSuccessUrl("/") // 로그아웃 성공 시 "/" 주소로 이동
			.and()
			.oauth2Login() // oauth2 로그인 시작점
			.userInfoEndpoint() // 로그인 성공하면 사용자 정보 가져올 때 설정을 담당
			.userService(customOAuth2UserService);
		//소셜 로그인 성공 시 후속 조치를 진행할 UserService인터페이스의 구현체를 등록한다.
		//리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다.
	}
}
