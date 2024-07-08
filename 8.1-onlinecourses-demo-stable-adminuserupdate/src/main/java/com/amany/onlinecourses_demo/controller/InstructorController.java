package com.amany.onlinecourses_demo.controller;

import com.amany.onlinecourses_demo.dao.InstructorDao;
import com.amany.onlinecourses_demo.dao.StudentDao;
import com.amany.onlinecourses_demo.entity.Instructor;
import com.amany.onlinecourses_demo.entity.Member;
import com.amany.onlinecourses_demo.service.MemberService;
import com.amany.onlinecourses_demo.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
        return "instructor_registration_form";
    }

    @PostMapping("/processForm")
    public String processForm (@Valid @ModelAttribute("webuser") WebUser theWebUser, BindingResult theBindingResult, Model theModel) {
        if(theBindingResult.hasErrors()) {
            return "instructor_registration_form";
        } else {
            String userName = theWebUser.getUserName();
            if (memberService.findByUserName(userName) == null) {
                memberService.save(theWebUser, "ROLE_INSTRUCTOR");
                Instructor tempInstructor = new Instructor(userName, theWebUser.getFirstName(),theWebUser.getLastName(),theWebUser.getEmail(),null,null);
                instructorDao.saveInstructor(tempInstructor);
                return "confirmation";
            } else {
                theModel.addAttribute("userexist", "this user already exists!");
                return "student-registration-form";
            }
        }
    }

    @DeleteMapping ("/{username}")
    public String deleteInstructor (@PathVariable String username) {
        if (memberService.findByUserName(username) == null) {
            return "user_not_found";
        }
        Instructor theInstructor = instructorDao.findInstructorByUsername(username);
        instructorDao.deleteInstructor(theInstructor);
        Member theMember = memberService.findByUserName(username);
        memberService.delete(theMember);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/bio")
    public String updateBio (@AuthenticationPrincipal UserDetails userDetails, Model theModel) {
        Instructor theInstructor = instructorDao.findInstructorByUsername(userDetails.getUsername());
        theModel.addAttribute("instructor", theInstructor);
        return "bio-form";
    }

    @PostMapping("/processBioForm")
    public String processBioForm (@AuthenticationPrincipal UserDetails userDetails, @RequestParam String bio) {
        System.out.println("processing");
        Instructor instructor = instructorDao.findInstructorByUsername(userDetails.getUsername());
        instructor.setBio(bio);
        instructorDao.saveInstructor(instructor);
        return "confirmation";

    }


}
