package com.chapter07.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chapter07.domain.Member;
import com.chapter07.persistence.MemberRepository;



// 사용자가 리소스에 접근하기 위해서 인증 관리 필터의 검증 통과해야 하는데 인증 관리 필터가 사용자가 입력한 정보를 토대로 기능을 처리하기 위해서 

@Service 
public class BoardUserDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberRepository memberRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//MemberRepository로 회원 정보를 조회하여
		//UserDetails 타입의 객체로 리턴
		
		Optional<Member> optional=memberRepo.findById(username);
		if(!optional.isPresent()) {
			throw new UsernameNotFoundException(username + " 사용자 없음");
		}else {
			Member member = optional.get();
			return new SecurityUser(member);
		}
		
	
	}
	
	
	
	
	
}
