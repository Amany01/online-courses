package com.amany.onlinecourses_demo.controller;

import com.amany.onlinecourses_demo.dao.MemberDao;
import com.amany.onlinecourses_demo.dao.RoleDaoImpl;
import com.amany.onlinecourses_demo.dao.StudentDao;
import com.amany.onlinecourses_demo.entity.Member;
import com.amany.onlinecourses_demo.entity.Role;
import com.amany.onlinecourses_demo.entity.Student;
import com.amany.onlinecourses_demo.service.MemberService;
import com.amany.onlinecourses_demo.user.WebUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {
    private MemberService memberService;
    private StudentDao studentDao;
    @Autowired
    public StudentController (MemberService theMemberService, StudentDao theStudentDao) {
        this.memberService = theMemberService;
        this.studentDao = theStudentDao;
    }
    @InitBinder
    public void initBinder (WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/registration")
    public String studentRegistration (Model theModel) {
        WebUser webUser = new WebUser();
        theModel.addAttribute("webuser", webUser);
        return "student-registration-form";
    }

    @PostMapping("/processForm")
    public String processForm (@Valid @ModelAttribute("webuser") WebUser theWebUser, BindingResult theBindingResult, Model theModel) {

        if(theBindingResult.hasErrors()) {
            return "student-registration-form";
        } else {
            String userName = theWebUser.getUserName();
            if (memberService.findByUserName(userName) == null) {
                memberService.save(theWebUser, "ROLE_STUDENT");
                Student tempStudent = new Student(userName, theWebUser.getFirstName(), theWebUser.getLastName(), theWebUser.getEmail());
                studentDao.saveStudent(tempStudent);
                return "confirmation";
            } else {
                theModel.addAttribute("userexist", "this user already exists!");
                return "student-registration-form";
            }
        }
    }

    @GetMapping("/{username}")
    public String updateStudent (@PathVariable String username, Model theModel) {
        Student tempStudent = studentDao.findStudentByUsername(username);
        theModel.addAttribute("student", tempStudent);
        return "update_student_form";
    }

    @PostMapping("/processUpdates")
    public String processUpdates (@Valid @ModelAttribute("student") Student theStudent, BindingResult theBindingResult, Model theModel) {
        if (theBindingResult.hasErrors()) {
            return "update_student_form";
        } else {
            studentDao.saveStudent(theStudent);
            return "confirmation";
        }
    }
}
