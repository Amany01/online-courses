package com.amany.onlinecourses_demo.controller;
import com.amany.onlinecourses_demo.dao.CourseDao;
import com.amany.onlinecourses_demo.dao.InstructorDao;
import com.amany.onlinecourses_demo.entity.*;
import com.amany.onlinecourses_demo.service.CourseService;
import com.amany.onlinecourses_demo.service.InstructorService;
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

import java.util.List;

@Controller
@RequestMapping("/instructors")
public class InstructorController {
    private MemberService memberService;
    private InstructorService instructorService;
    private HttpSession session;
    private CourseService courseService;
    @Autowired
    public InstructorController(MemberService memberService, InstructorService instructorService, HttpSession session, CourseService courseService) {
        this.memberService = memberService;
        this.instructorService = instructorService;
        this.session = session;
        this.courseService = courseService;
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
                instructorService.saveInstructor(tempInstructor);
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
        Instructor theInstructor = instructorService.findInstructorByUsername(username);
        instructorService.deleteInstructor(theInstructor);
        Member theMember = memberService.findByUserName(username);
        memberService.delete(theMember);
        session.invalidate();
        return "redirect:/";
    }

    @DeleteMapping("/id/{courseId}")
    public String deleteCourse (@PathVariable int courseId) {
        Course tempCourse = courseService.findCourseById(courseId);
        courseService.deleteCourse(tempCourse);
        return "confirmation";
    }

    @GetMapping("/bio")
    public String updateBio (@AuthenticationPrincipal UserDetails userDetails, Model theModel) {
        Instructor theInstructor = instructorService.findInstructorByUsername(userDetails.getUsername());
        theModel.addAttribute("instructor", theInstructor);
        return "bio-form";
    }

    @PostMapping("/processBioForm")
    public String processBioForm ( @Valid @ModelAttribute("instructor") Instructor theInstructor, BindingResult theBindingResult) {
        if(theBindingResult.hasErrors()) {
            return "bio-form";
        }
        instructorService.saveInstructor(theInstructor);
        return "confirmation";
    }

    @PutMapping("/{username}")
    public String updateInstructor (@PathVariable String username, Model theModel) {
        Instructor tempInstructor = instructorService.findInstructorByUsername(username);
        theModel.addAttribute("instructor", tempInstructor);
        return "update-instructor-form";
    }

    @PostMapping("/processUpdates")
    public String processUpdates (@Valid @ModelAttribute("instructor") Instructor theInstructor, BindingResult theBindingResult) {
        if (theBindingResult.hasErrors()) {
            return "update-instructor-form";
        } else {
            instructorService.saveInstructor(theInstructor);
            return "confirmation";
        }
    }

    @PostMapping("/addCourse")
    public String addCourse (@AuthenticationPrincipal UserDetails userDetails, @RequestParam String title) {
        Instructor tempInstructor = instructorService.findInstructorByUsername(userDetails.getUsername());
        Course course = new Course(title, tempInstructor);
        tempInstructor.add(course);
        instructorService.saveInstructor(tempInstructor);
        return "confirmation";
    }

    @GetMapping("/{username}")
    public String findCourses (@PathVariable String username, Model theModel) {
        Instructor tempInstructor = instructorService.findInstructorByUsername(username);
        theModel.addAttribute("Instructor", tempInstructor);
        return "Instructor-courses";
    }
}
