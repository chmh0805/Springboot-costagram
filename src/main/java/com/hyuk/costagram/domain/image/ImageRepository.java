package com.hyuk.costagram.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer> {

	@Query(
			nativeQuery = true,
			value = "SELECT * FROM image WHERE userId IN (SELECT toUserId FROM follow WHERE fromUserId = :principalId) ORDER BY id DESC"
			)
	Page<Image> mFeed(int principalId, Pageable pageable);
}
