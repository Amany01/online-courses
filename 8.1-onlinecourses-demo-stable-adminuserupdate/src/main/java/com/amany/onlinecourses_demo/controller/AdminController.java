package com.amany.onlinecourses_demo.controller;

import com.amany.onlinecourses_demo.dao.CourseDao;
import com.amany.onlinecourses_demo.dao.InstructorDao;
import com.amany.onlinecourses_demo.dao.ReviewDao;
import com.amany.onlinecourses_demo.dao.StudentDao;
import com.amany.onlinecourses_demo.entity.Course;
import com.amany.onlinecourses_demo.entity.Instructor;
import com.amany.onlinecourses_demo.entity.Member;
import com.amany.onlinecourses_demo.entity.Student;
import com.amany.onlinecourses_demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private StudentService studentService;
    private MemberService memberService;
    private InstructorService instructorService;
    private CourseService courseService;
    private ReviewService reviewService;
    @Autowired
    public AdminController (ReviewService theReviewService, StudentService theStudentService, MemberService theMemberService, InstructorService theInstructorService, CourseService theCourseService) {
        this.studentService = theStudentService;
        this.memberService = theMemberService;
        this.instructorService = theInstructorService;
        this.courseService = theCourseService;
        this.reviewService = theReviewService;
    }
    @PostMapping("/updateStudent")
    public String updateStudentProfile(@RequestParam String username, Model theModel) {
        if (studentService.findStudentByUsername(username) == null) {
            return "user_not_found";
        } else {
            Student tempStudent = studentService.findStudentByUsername(username);
            theModel.addAttribute("student", tempStudent);
            //return"confirmation";
            return "update_student_form";
        }
    }

    @PostMapping("/deleteStudent")
    public String deleteStudent (@RequestParam String username) {
        if (studentService.findStudentByUsername(username) == null) {
            return "user_not_found";
        }
        Student theStudent = studentService.findStudentByUsername(username);
        studentService.deleteStudent(theStudent);
        // deleting member will delete from roles by cascading effect
        Member theMember = memberService.findByUserName(username);
        memberService.delete(theMember);
        return "confirmation";
    }

    @PostMapping("/deleteInstructor")
    public String deleteInstructor (@RequestParam String username) {
        if (instructorService.findInstructorByUsername(username) == null) {
            return "user_not_found";
        }
        Instructor theInstructor = instructorService.findInstructorByUsername(username);
        instructorService.deleteInstructor(theInstructor);
        Member theMember = memberService.findByUserName(username);
        memberService.delete(theMember);
        return "confirmation";
    }

    @PostMapping("/deleteCourse")
    public String deleteCourse (@RequestParam String title) {
        if (courseService.findCourseByTitle(title) == null) {
            return "user_not_found";
        }
        Course tempCourse = courseService.findCourseByTitle(title);
        courseService.deleteCourse(tempCourse);
        return "confirmation";
    }

    @GetMapping("/deleteReview/{reviewId}")
    public String deleteReview (@PathVariable int reviewId) {
        System.out.println(reviewId);
        reviewService.deleteReviewById(reviewId);
        return "confirmation";
    }
}
