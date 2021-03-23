package com.hyuk.costagram.domain.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 30,unique = true)
	private String username;
	
	@JsonIgnore // Json으로 파싱되지 않게 하는 어노테이션
	private String password;
	
	private String name; // 이름
	private String website; // 자기 홈페이지
	private String bio; // 자기 소개
	private String email;
	private String phone;
	private String gender;
	
	private String profileImage; // 프로필 이미지 경로
	private String provider; // 제공자 Google, Facebook, Naver 등
	
	private String role; // USER, ADMIN
	
	@CreationTimestamp
	private Timestamp createDate;
}
