package com.boot.resvo.member.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boot.resvo.member.model.MemberEntity;
import com.boot.resvo.member.model.SignResponseDTO;
import com.boot.resvo.utils.StrUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 회원 가입 관련 서비스
 */
@Slf4j
@Service
public class SignService {

	@Autowired
	private MemberService memberService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 회원 가입
	 */
	public SignResponseDTO signUp(MemberEntity member) {

		// Email 중복 점검
		if (memberService.hasMemberByEmail(member.getEmail()))
			return fail("해당 Email 은 이미 존재합니다.");

		// 패스워드
		String password = member.getPassward();

		// 패스워드 검증
		if (!validationPassword(password))
			return fail("패스워드 정책에 맞지 않습니다.");

		// 패스워드 설정
		member.setPassward(passwordEncoder.encode(member.getPassward()));
		// 가입 날짜 설정
		member.setRegtDate(new Date());

		// 회원 저장
		MemberEntity memberEntity = memberService.saveMember(member);

		// 결과값 반환
		return success(memberEntity, "회원가입에 성공하였습니다.");
	}

	/**
	 * 일반 로그인
	 * @param email 이메일
	 * @param password 패스워드
	 */
	public SignResponseDTO signIn(String email, String password) {
		Optional<MemberEntity> optiMember = memberService.getMemberByEmail(email);

		// 이메일이 존재하지 않는 경우
		if (!optiMember.isPresent())
			return fail("이메일이 존재하지 않습니다.");

		// 회원 정보
		MemberEntity member = optiMember.get();

		// 패스워드가 틀린 경우
		if (!equalsPassword(member, password))
			return fail("패스워드가 틀렸습니다.");

		return success(member, "로그인에 성공하였습니다.");
	}

	/**
	 * 회원 패스워드 수정
	 */
	public SignResponseDTO updatePassword(long memId, String password) {
		// 회원 조회
		Optional<MemberEntity> optiMember = memberService.getMemberById(memId);

		// 회원 존재 여부 확인
		if (!optiMember.isPresent())
			return fail("회원이 존재하지 않습니다.");

		// 회원
		MemberEntity member = optiMember.get();

		// 패스워드 검증
		if (!validationPassword(password))
			return fail("패스워드 정책과 맞지 않습니다.");

		// 암호화된 패스워드
		String encodePassword = passwordEncoder.encode(password);

		// 기존 패스워드와 동일한 경우
		if (encodePassword.equals(member.getPassward()))
			return fail("기존 패스워드와 동일합니다.");

		// 패스워드 설정
		member.setPassward(encodePassword);
		// 수정 날짜 설정
		member.setUpdtDate(new Date());

		// 회원 수정
		MemberEntity memberEntity = memberService.saveMember(member);

		return success(memberEntity, "회원 수정에 성공하였습니다.");
	}

	/**
	 * 성공한 경우
	 * @param memberEntity 회원
	 * @param msg 메세지
	 * @return
	 */
	private SignResponseDTO success(MemberEntity memberEntity, String msg) {
		return SignResponseDTO.builder()
			.memberEntity(memberEntity)
			.msg(msg)
			.successYN(true)
			.build();
	}

	/**
	 * 실패한 경우
	 * @param msg 메세지
	 * @return
	 */
	private SignResponseDTO fail(String msg) {
		return SignResponseDTO.builder()
			.msg(msg)
			.successYN(false)
			.build();
	}

	/**
	 * 입력한 패스워드와 기존 패스워드 같은지 확인
	 * @param member 회원
	 * @param password 패스워드
	 * @return
	 */
	private boolean equalsPassword(MemberEntity member, String password) {
		return member.getPassward().equals(passwordEncoder.encode(password));
	}

	/**
	 * 패스워드 검증
	 *
	 * @param password 패스워드
	 * @return 문제 없는경우 true 반환
	 */
	private boolean validationPassword(String password) {
		return !StrUtil.ifNull(password) && password.length() >= 8;
	}

}
