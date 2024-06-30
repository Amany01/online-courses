package com.amany.onlinecourses_demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
    @Value("${developer.name}")
    private String developer;
    @GetMapping("/")
    public String home (Model theModel) {
        theModel.addAttribute("theDate", java.time.LocalDateTime.now());
        theModel.addAttribute( "theDeveloper", developer);
        return "home";
    }

    @GetMapping("/dashboard")
    public String showAdminDashBoard (@AuthenticationPrincipal UserDetails userDetails, Model theModel) {
        String user="default";
        if (userDetails != null) {
            user = userDetails.getUsername();
        }
        theModel.addAttribute("username", user);
        return "dashboard";
    }


}
