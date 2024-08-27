package com.ucd.keynote.domain.user.controller;

import com.ucd.keynote.domain.user.dto.JoinDTO;
import com.ucd.keynote.domain.user.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@Controller
@RequestMapping("/api/users")
@ResponseBody
public class JoinController {
    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signup")
    public String joinProcess(JoinDTO joinDTO) {

        System.out.println(joinDTO.getUsername());
        System.out.println(joinDTO.getEmail());
        System.out.println(joinDTO.getPassword());
        joinService.joinProcess(joinDTO);

        return "ok";
    }

    @GetMapping("/user")
    public String mainP() {

        return "main Controller";
    }
}
