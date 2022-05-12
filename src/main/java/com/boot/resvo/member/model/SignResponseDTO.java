package com.boot.resvo.member.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class SignResponseDTO {

	/**
	 * 회원 정보
	 */
	private MemberEntity memberEntity;

	/**
	 * 메세지
	 */
	private String msg;

	/**
	 * 성공 여부
	 */
	private boolean successYN;


}
