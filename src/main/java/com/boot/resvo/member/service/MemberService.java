package com.boot.resvo.member.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.resvo.member.model.MemberEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * 회원 서비스
 */
@Slf4j
@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	/**
	 * 모든 회원 조회
	 * @return 모든 회원 반환
	 */
	public List<MemberEntity> getAllMember() {
		return memberRepository.findAll();
	}

	/**
	 * 회원 조회
	 * @param memId 회원 ID
	 * @return 회원 반환
	 */
	public Optional<MemberEntity> getMemberById(long memId) {
		return memberRepository.findById(memId);
	}

	/**
	 * 회원 조회
	 * @param email 이메일
	 * @return 회원 반환
	 */
	public Optional<MemberEntity> getMemberByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	/**
	 * 회원 존재 여부
	 * @param memId 회원 ID
	 * @return 회원이 존재하는 경우 true 반환
	 */
	public boolean hasMemberById(long memId) {
		return getMemberById(memId).isPresent();
	}

	/**
	 * 회원 존재 여부
	 * @param email 이메일
	 * @return 회원이 존재하는 경우 true 반환
	 */
	public boolean hasMemberByEmail(String email) {
		return getMemberByEmail(email).isPresent();
	}

	/**
	 * 회원 저장
	 * @param member 회원
	 */
	public MemberEntity saveMember(MemberEntity member) {
		return memberRepository.save(member);
	}

	/**
	 * 회원 삭제
	 * @param memId 회원 ID
	 */
	public void deleteMember(long memId) {
		memberRepository.deleteById(memId);
	}
}
