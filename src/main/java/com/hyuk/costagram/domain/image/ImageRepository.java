package com.hyuk.costagram.domain.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer> {

	@Query(
			nativeQuery = true,
			value = "select i.* from image i INNER JOIN follow f ON f.toUserId = i.userId WHERE f.fromUserId = :principalId ORDER BY i.id DESC"
			)
	List<Image> mFeed(int principalId);
}
