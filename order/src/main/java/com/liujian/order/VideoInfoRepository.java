package com.liujian.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoInfoRepository extends JpaRepository<VideoInfo, Long> {

	Page<VideoInfo> findByUserId(String userId, Pageable page);

}
