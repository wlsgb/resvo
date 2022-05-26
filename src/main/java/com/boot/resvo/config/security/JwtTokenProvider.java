package com.boot.resvo.config.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.boot.resvo.common.code.AppConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	@Value("${globals.jwt.secret-key}")
	private String JWT_SECRET_KEY;

	// 토큰 유효시간 1시간
	private Long JWT_EXPIRATION = 60 * 60 * 1000L;

	private final UserDetailsService userDetailsService;

	@PostConstruct
	protected void init() {
		JWT_SECRET_KEY = Base64.getEncoder().encodeToString(JWT_SECRET_KEY.getBytes());
	}

	public String createToken(String userPk, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
		claims.put("roles", roles);
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now) // 현재 시간 기반으로 생성
			.setExpiration(new Date(now.getTime() + JWT_EXPIRATION)) // 만료 시간 세팅
			.signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY) // 사용할 암호화 알고리즘, signature 에 들어갈 secret 값 세팅
			.compact();
	}

	/**
	 * JWT 토큰에서 인증 정보 조회
	 * @param token
	 * @return
	 */
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(getMemberPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	/**
	 * 토큰에서 회원 정보 추출
	 * @param token
	 * @return
	 */
	public String getMemberPk(String token) {
		return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * Request의 Header에서 token 값을 가져옵니다.
	 * "X-AUTH-TOKEN" : "TOKEN 값"
	 *
	 * @param request
	 * @return
	 */
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader(AppConfig.X_AUTH_TOKEN);
	}

	/**
	 * 토큰의 유효성 검사 + 만료 일자 확인
	 *
	 * @param jwtToken
	 * @return
	 */
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			log.error(e.toString());
			return false;
		}
	}
}
