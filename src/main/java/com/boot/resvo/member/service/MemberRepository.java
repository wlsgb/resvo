package com.boot.resvo.member.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.resvo.member.model.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

	/**
	 * And : 여러필드를 and 로 검색
	 * Or : 여러필드를 or 로 검색
	 * Between : 필드의 두 값 사이에 있는 항목 검색
	 * LessThan : 작은 항목 검색
	 * GreaterThanEqual : 크거나 같은 항목 검색
	 * Like : like 검색
	 * IsNull : null 인 항목 검색
	 * In : 여러 값중에 하나인 항목 검색
	 * OrderBy : 검색 결과를 정렬하여 전달
	 */

	/**
	 * 이메일로 회원 검색
	 * @param email 이메일
	 * @return
	 */
	Optional<MemberEntity> findByEmail(String email);

}
