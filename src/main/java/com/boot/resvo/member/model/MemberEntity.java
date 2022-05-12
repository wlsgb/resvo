package com.boot.resvo.member.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "member")
public class MemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String memId;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String passward;

	private String nickNm;

	@Column(nullable = false)
	private String memNm;

	private String telNo;

	@Column(nullable = false)
	private String cpNo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date regtDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updtDate;

}
