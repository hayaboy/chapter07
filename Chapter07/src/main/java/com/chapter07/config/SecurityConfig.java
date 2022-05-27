package com.chapter07.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity  // 이 클래스로부터 생성된 객체가 시큐리티 설정파일임을 의미하면서 동시에 시큐리티를 사용하는데 필요한 수많은 객체를 생성
public class SecurityConfig extends WebSecurityConfigurerAdapter{ //WebSecurityConfigurerAdapter 상속한 시큐리티 설정 클래스가 빈으로 등록되기만 해도 더이상 로그인을 강제하지 않는다.
	
	@Autowired
	private BoardUserDetailsService boardUserDetailsService;

	
	@Autowired
	private DataSource dataSource;   // 데이터 소스를 멤버 변수로 선언하고 의존성 주입
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {  //HttpSecurity 객체를 이용하여 애플리케이션 자원에 대한 인증과 인가를 제어
		security.authorizeRequests().antMatchers("/").permitAll();
		security.authorizeRequests().antMatchers("/member/**").authenticated();
		security.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");
		security.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
		security.csrf().disable();
		//security.formLogin();
		
		security.formLogin().loginPage("/login").defaultSuccessUrl("/loginSuccess", true); //loginPage 로그인에 사용할 화면 지정, defaultSuccessUrl 로그인에 성공했을 때 이동할 URL 지정
		security.exceptionHandling().accessDeniedPage("/accessDenied"); // 에러화면 대시 적절한 권한이 없어서 해당 페이지를 볼 수 없다는 메시지를 보여줌
		security.logout().invalidateHttpSession(true).logoutSuccessUrl("/login"); // 쿠키정보까지 삭제하고자 할 경우  deleteCookies() 

		security.userDetailsService(boardUserDetailsService);

		
	}
	
	//스프링 시큐리티에서 비밀번호 암호화
	@Bean  
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	
//	@Autowired // AuthenticationManagerBuilder 객체를 @Autowired를 통해 의존성 주입 받은 authenticate에서 인증에 필요한 사용자 정보 생성
	//public void authenticate(AuthenticationManagerBuilder auth) throws Exception{ //AuthenticationManagerBuilder가 지원하는 인증방식은 메모리, JDBC, LDAP
//		auth.inMemoryAuthentication().withUser("manager").password("{noop}manager123").roles("MANAGER");//noop 암호화 처리 안함
//		auth.inMemoryAuthentication().withUser("admin").password("{noop}admin123").roles("ADMIN");//noop 암호화 처리 안함
		
	//	String query1="select id username, concat('{noop}',password) password, true enabled from member where id=?";  // 사용자 정보 조회(인증)
		
		
		//스프링 시큐리티는 조회한 사용자 정보를 시큐리티에서 내부적으로 사용하는 org.springframework.security.core.userdetails.User 객체에 자동으로 매팅
		//이때 아이디는 "username"에, 비밀번호는 "password"변수에 각각 저장, 조회 결과의 칼럼 이름이 USERNAME과 PASSWORD로 일치해야 자동으로 매팅
		//이를 위해 조회 쿼리에 Alias를 각각 적용
		//비밀번호는 암호화를 적용하지 안기 위해서 비밀번호 문자열 앞에 '{noop}'를 추가
		
	//	String query2="select id, role from member where id=?";  // 권한 정보 조회
		
		
		
	//	auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(query1).authoritiesByUsernameQuery(query2);
		//AuthenticationManagerBuilder가 지원하는 인증방식은 JDBC
		//usersByUsernameQuery()인증 처리, 만약 여기에서 인증에 실패하면 다음 단계로 진행되지 않는다.
		//authoritiesByUsernameQuery() 인증에 통과한 사용자의 권한 검증
	//}
	
	
	
	

}
