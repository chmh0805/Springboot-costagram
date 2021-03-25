package com.hyuk.costagram.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
	
	// nativeQuery로 write할 때는 @Modifying을 붙여줘야 동작한다.
	
	@Modifying
	@Query(nativeQuery = true, value = "INSERT INTO follow(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())")
	int mFollow(int fromUserId, int toUserId);
	
	@Modifying
	@Query(nativeQuery = true, value = "DELETE FROM follow WHERE fromUserId = :fromUserId AND toUserId = :toUserId")
	int mUnFollow(int fromUserId, int toUserId);
	
	@Query(nativeQuery = true, value = "SELECT count(*) FROM follow f WHERE toUserId = :principalId")
	int mFollowCount(int principalId);
	
	@Query(nativeQuery = true, value = "SELECT count(*) FROM follow f WHERE fromUserId = :principalId AND toUserId = :toUserId")
	int isFollowing(int principalId, int toUserId);
}
