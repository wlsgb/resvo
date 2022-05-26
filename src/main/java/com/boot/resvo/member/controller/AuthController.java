package com.boot.resvo.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.resvo.common.model.Token;
import com.boot.resvo.config.security.JwtTokenProvider;
import com.boot.resvo.member.model.MemberEntity;
import com.boot.resvo.member.model.MemberSignUpRequest;
import com.boot.resvo.member.model.SignResponseDTO;
import com.boot.resvo.member.service.AuthService;
import com.boot.resvo.member.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "인증")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final MemberService memberService;

	private final AuthService authService;

	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * 회원 가입
	 * @return
	 */
	@ApiOperation(
		value = "회원 가입",
		notes = "회원 가입을 진행한다."
	)
	@PostMapping("/sign-up")
	public SignResponseDTO signUp(@RequestBody MemberSignUpRequest member) {
		return authService.signUp(member.toEntity());
	}

	/**
	 * 로그인
	 */
	@PostMapping(
		value = "/sign-in",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity signIn(
		final HttpServletRequest req,
		final HttpServletResponse res,
		@Valid @RequestBody Token.Request request) throws Exception {

		SignResponseDTO signResponseDTO = authService.signIn(request.getEmail(), request.getPassword());

		if (!signResponseDTO.isSuccessYN())
			throw new IllegalArgumentException(signResponseDTO.getMessage().getMessage());

		MemberEntity member = signResponseDTO.getMemberEntity();

		String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles());

		Token.Response response = Token.Response.builder().token(token).build();

		return ResponseEntity.ok(response);
	}
}
