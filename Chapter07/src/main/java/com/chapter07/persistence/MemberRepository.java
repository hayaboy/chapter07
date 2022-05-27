package com.chapter07.persistence;

import org.springframework.data.repository.CrudRepository;

import com.chapter07.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String>  {

}
