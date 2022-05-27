package com.chapter07.domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
@Entity
public class Member  {
	
	
	
	private static final long serialVersionUID = 1L;
	@Id
	private String id; 
	private String password;
	private String name;
	@Enumerated(EnumType.STRING)  //role 변수는 권한에 해당하는 값이 문자열로 저장
	private Role role;
	private boolean enabled;
	
//  extends User User 클래스를 상속해서 생성자 추가하는 방식
//	public Member(Member member) {
//		super(member.getId(), "{noop}" + member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
//		
//	}

}
