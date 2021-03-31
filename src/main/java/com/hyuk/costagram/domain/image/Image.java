package com.hyuk.costagram.domain.image;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hyuk.costagram.domain.comment.Comment;
import com.hyuk.costagram.domain.likes.Likes;
import com.hyuk.costagram.domain.tag.Tag;
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
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String caption;
	private String postImageUrl;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@OneToMany(mappedBy = "image")
	private List<Tag> tags;
	
	@OneToMany(mappedBy = "image")
	private List<Likes> likes;
	
	@OneToMany(mappedBy = "image")
	private List<Comment> comments;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	@Transient // 컬럼이 생성되지 않는다.
	private int likeCount;
	
	@Transient
	private boolean likeState;
}
