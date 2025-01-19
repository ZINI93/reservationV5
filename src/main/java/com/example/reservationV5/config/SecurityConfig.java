package com.example.reservationV5.config;

import com.example.reservationV5.domain.member.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean  // role 수직계층 시큐리티에 적용
    public RoleHierarchy roleHierarchy(){

        return RoleHierarchyImpl.withRolePrefix("ROLE_")
                .role(UserRole.ADMIN.toString()).implies(UserRole.USER.toString())
                .build();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrfConfig) -> csrfConfig.disable()) // CSRF 보호 비활성화
                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()) // H2 콘솔을 위한 프레임 옵션 비활성화
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers(PathRequest.toH2Console()).permitAll() // H2 콘솔에 대한 접근 허용
                                .requestMatchers("/css/**", "/js/**", "/images/**","/").permitAll() // 로그인, 회원 등록, 내 정보 페이지는 모든 사용자에게 허용
                                .requestMatchers("/members/new").permitAll() // 로그인, 회원 등록, 내 정보 페이지는 모든 사용자에게 허용
                                .requestMatchers("/api/**").permitAll() // api test 잠시 열어두는 중
                                .requestMatchers("/reservations/**").authenticated()
                                .requestMatchers("/admin/**").hasRole("ADMIN") // /admin은 ADMIN 역할만 접근 가능
                                .anyRequest().authenticated() // 그 외의 모든 요청은 인증된 사용자만 접근 가능
                )

                .formLogin((auth) ->
                        auth.loginPage("/login") // 로그인 페이지 설정
                                .loginProcessingUrl("/loginProc") // 로그인 처리 URL 설정
                                .defaultSuccessUrl("/reservations",true)
                                .permitAll() // 로그인 페이지는 모두 접근 가능
                );

        return http.build(); // SecurityFilterChain 빌드
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}