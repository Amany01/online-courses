package com.amany.onlinecourses_demo.controller;

import com.amany.onlinecourses_demo.dao.StudentDao;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private StudentDao studentDao;
    @Autowired
    public AdminController (StudentDao theStudentDao) {
        this.studentDao = theStudentDao;
    }
    @GetMapping("/updateStudent")
    public String getStudentProfile(@RequestParam String username, Model theModel) {
        if (studentDao.findStudentByUsername(username) == null) {
            return "user_not_found";
        } else {
            return "redirect:/students/" + username;
        }
    }
}
