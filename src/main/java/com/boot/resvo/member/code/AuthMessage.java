package com.boot.resvo.member.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum AuthMessage {
	SIGN_UP_SUCCESS("회원가입에 성공하였습니다."),
	SIGN_IN_SUCCESS("로그인에 성공하였습니다."),
	MEMBER_MODIFY_SUCCESS("회원 수정에 성공하였습니다."),

	MEMBER_NOT_EXIST("회원이 존재하지 않습니다."),

	EMAIL_NOT_EXIST("이메일 정보가 존재하지 않습니다."),
	EMAIL_ALREADY_EXIST("이메일이 이미 존재합니다."),

	PASSWORD_IS_INVALID("패스워드가 잘못되었습니다."),
	PASSWORD_NOT_POLICY("패스워드 정책과 맞지 않습니다."),
	SAME_EXIST_PASSWORD("기존 패스워드와 동일합니다.")
	;

	private String message;
}
