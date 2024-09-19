package com.ucd.keynote.domain.channel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelUpdateRequestDTO {
    @JsonProperty("new_name")
    private String newName;
    @JsonProperty("new_description")
    private String newDescription;
}
