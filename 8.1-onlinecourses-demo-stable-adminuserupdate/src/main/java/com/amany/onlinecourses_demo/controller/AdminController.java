package com.amany.onlinecourses_demo.controller;

import com.amany.onlinecourses_demo.dao.StudentDao;
import com.amany.onlinecourses_demo.entity.Member;
import com.amany.onlinecourses_demo.entity.Student;
import com.amany.onlinecourses_demo.service.MemberService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private StudentDao studentDao;
    private MemberService memberService;
    @Autowired
    public AdminController (StudentDao theStudentDao, MemberService theMemberService) {
        this.studentDao = theStudentDao;
        this.memberService = theMemberService;
    }
    @PostMapping("/updateStudent")
    public String updateStudentProfile(@RequestParam String username, Model theModel) {
        if (studentDao.findStudentByUsername(username) == null) {
            return "user_not_found";
        } else {
            Student tempStudent = studentDao.findStudentByUsername(username);
            theModel.addAttribute("student", tempStudent);
            //return"confirmation";
            return "update_student_form";
        }
    }

    @PostMapping("/deleteStudent")
    public String deleteStudent (@RequestParam String username) {
        if (studentDao.findStudentByUsername(username) == null) {
            return "user_not_found";
        }
        Student theStudent = studentDao.findStudentByUsername(username);
        studentDao.deleteStudent(theStudent);
        // deleting member will delete from roles by cascading effect
        Member theMember = memberService.findByUserName(username);
        memberService.delete(theMember);
        return "confirmation";
    }
}
