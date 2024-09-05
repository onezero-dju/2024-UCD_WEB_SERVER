package com.ucd.keynote.domain.channel.repository;

import com.ucd.keynote.domain.channel.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
