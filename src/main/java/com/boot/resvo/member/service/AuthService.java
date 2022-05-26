package com.boot.resvo.member.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boot.resvo.member.code.MemberMessage;
import com.boot.resvo.member.model.MemberEntity;
import com.boot.resvo.member.model.SignResponseDTO;
import com.boot.resvo.utils.StrUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 인증 관련 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

	private final MemberService memberService;

	private final PasswordEncoder passwordEncoder;

	/**
	 * 회원 가입
	 */
	public SignResponseDTO signUp(MemberEntity member) {

		// Email 중복 점검
		if (memberService.hasMemberByEmail(member.getEmail()))
			return fail(MemberMessage.EMAIL_ALREADY_EXIST);

		// 패스워드
		String password = member.getPassward();

		// 패스워드 검증
		if (!validationPassword(password))
			return fail(MemberMessage.PASSWORD_NOT_POLICY);

		// 패스워드 설정
		member.setPassward(passwordEncoder.encode(member.getPassward()));
		// 가입 날짜 설정
		member.setRegtDate(new Date());

		// 회원 저장
		MemberEntity memberEntity = memberService.saveMember(member);

		// 결과값 반환
		return success(memberEntity, MemberMessage.SIGN_UP_SUCCESS);
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
			return fail(MemberMessage.EMAIL_NOT_EXIST);

		// 회원 정보
		MemberEntity member = optiMember.get();

		// 패스워드가 틀린 경우
		if (!passwordEncoder.matches(password, member.getPassward()))
			return fail(MemberMessage.PASSWORD_IS_INVALID);

		return success(member, MemberMessage.SIGN_IN_SUCCESS);
	}

	/**
	 * 회원 패스워드 수정
	 */
	public SignResponseDTO updatePassword(long memId, String password) {
		// 회원 조회
		Optional<MemberEntity> optiMember = memberService.getMemberById(memId);

		// 회원 존재 여부 확인
		if (!optiMember.isPresent())
			return fail(MemberMessage.MEMBER_NOT_EXIST);

		// 회원
		MemberEntity member = optiMember.get();

		// 패스워드 검증
		if (!validationPassword(password))
			return fail(MemberMessage.PASSWORD_NOT_POLICY);

		// 암호화된 패스워드
		String encodePassword = passwordEncoder.encode(password);

		// 기존 패스워드와 동일한 경우
		if (encodePassword.equals(member.getPassward()))
			return fail(MemberMessage.SAME_EXIST_PASSWORD);

		// 패스워드 설정
		member.setPassward(encodePassword);
		// 수정 날짜 설정
		member.setUpdtDate(new Date());

		// 회원 수정
		MemberEntity memberEntity = memberService.saveMember(member);

		return success(memberEntity, MemberMessage.MEMBER_MODIFY_SUCCESS);
	}

	/**
	 * 성공한 경우
	 * @param memberEntity 회원
	 * @param message 메세지
	 * @return
	 */
	private SignResponseDTO success(MemberEntity memberEntity, MemberMessage message) {
		return SignResponseDTO.builder()
			.memberEntity(memberEntity)
			.message(message)
			.successYN(true)
			.build();
	}

	/**
	 * 실패한 경우
	 * @param message 메세지
	 * @return
	 */
	private SignResponseDTO fail(MemberMessage message) {
		return SignResponseDTO.builder()
			.message(message)
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
		return passwordEncoder.matches(password, member.getPassward());
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
