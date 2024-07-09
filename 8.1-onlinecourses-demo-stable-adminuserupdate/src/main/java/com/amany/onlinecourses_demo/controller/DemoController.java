package com.amany.onlinecourses_demo.controller;

import com.amany.onlinecourses_demo.dao.CourseDao;
import com.amany.onlinecourses_demo.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DemoController {
    private CourseDao courseDao;
    @Autowired
    public DemoController (CourseDao courseDao) {
        this.courseDao = courseDao;
    }
    @Value("${developer.name}")
    private String developer;
    @GetMapping("/")
    public String home (Model theModel) {
        theModel.addAttribute("theDate", java.time.LocalDateTime.now());
        theModel.addAttribute( "theDeveloper", developer);
        List<Course> courses = courseDao.findAllCourses();
        theModel.addAttribute("courseList", courses);
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
