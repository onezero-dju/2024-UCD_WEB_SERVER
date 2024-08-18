package com.ucd.keynote.domain.user.controller;

import com.ucd.keynote.domain.user.dto.JoinDTO;
import com.ucd.keynote.domain.user.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController {
    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {

        System.out.println(joinDTO.getName());
        joinService.joinProcess(joinDTO);

        return "ok";
    }
}
