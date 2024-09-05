package com.ucd.keynote.domain.channel.repository;

import com.ucd.keynote.domain.channel.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    // 해당 조직에 속한 채널의 리스트 가져오기
    List<Channel> findByOrganization_OrganizationId(Long organizationId);
}
