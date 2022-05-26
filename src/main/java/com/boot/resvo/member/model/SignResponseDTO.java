package com.boot.resvo.member.model;

import com.boot.resvo.member.code.AuthMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원가입 응답 DTO
 */
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SignResponseDTO {
	// 회원 정보
	private MemberEntity memberEntity;
	// 메세지
	private AuthMessage message;
	// 성공 여부
	private boolean successYN;
}
