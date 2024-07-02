package com.amany.onlinecourses_demo.controller;

import com.amany.onlinecourses_demo.dao.InstructorDao;
import com.amany.onlinecourses_demo.dao.StudentDao;
import com.amany.onlinecourses_demo.service.MemberService;
import com.amany.onlinecourses_demo.user.WebUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/instructors")
public class InstructorController {
    private MemberService memberService;
    private InstructorDao instructorDao;
    private HttpSession session;
    @Autowired
    public InstructorController(MemberService memberService, InstructorDao instructorDao, HttpSession session) {
        this.memberService = memberService;
        this.instructorDao = instructorDao;
        this.session = session;
    }

    @InitBinder
    public void initBinder (WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/registration")
    public String instructorRegistration (Model theModel) {
        WebUser webUser = new WebUser();
        theModel.addAttribute("webuser", webUser);
        return "confirmation";
    }
}
