package com.hyuk.costagram.domain.follow;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.hyuk.costagram.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JoinColumn(name = "fromUserId")
	@ManyToOne
	private User fromUser; // --가
	
	@JoinColumn(name = "toUserId")
	@ManyToOne
	private User toUser; // --를 팔로우
	
	@CreationTimestamp
	private Timestamp createDate;
}
