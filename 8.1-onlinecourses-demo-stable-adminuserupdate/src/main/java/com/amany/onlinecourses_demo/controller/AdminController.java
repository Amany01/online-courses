package com.amany.onlinecourses_demo.controller;

import com.amany.onlinecourses_demo.dao.CourseDao;
import com.amany.onlinecourses_demo.dao.InstructorDao;
import com.amany.onlinecourses_demo.dao.ReviewDao;
import com.amany.onlinecourses_demo.dao.StudentDao;
import com.amany.onlinecourses_demo.entity.Course;
import com.amany.onlinecourses_demo.entity.Instructor;
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
    private InstructorDao instructorDao;
    private CourseDao courseDao;
    private ReviewDao reviewDao;
    @Autowired
    public AdminController (ReviewDao theReviewDao, StudentDao theStudentDao, MemberService theMemberService, InstructorDao theInstructorDao, CourseDao theCourseDao) {
        this.studentDao = theStudentDao;
        this.memberService = theMemberService;
        this.instructorDao = theInstructorDao;
        this.courseDao = theCourseDao;
        this.reviewDao = theReviewDao;
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

    @PostMapping("/deleteInstructor")
    public String deleteInstructor (@RequestParam String username) {
        if (instructorDao.findInstructorByUsername(username) == null) {
            return "user_not_found";
        }
        Instructor theInstructor = instructorDao.findInstructorByUsername(username);
        instructorDao.deleteInstructor(theInstructor);
        Member theMember = memberService.findByUserName(username);
        memberService.delete(theMember);
        return "confirmation";
    }

    @PostMapping("/deleteCourse")
    public String deleteCourse (@RequestParam String title) {
        if (courseDao.findCourseByTitle(title) == null) {
            return "user_not_found";
        }
        Course tempCourse = courseDao.findCourseByTitle(title);
        courseDao.deleteCourse(tempCourse);
        return "confirmation";
    }

    @GetMapping("/deleteReview/{reviewId}")
    public String deleteReview (@PathVariable int reviewId) {
        System.out.println(reviewId);
        reviewDao.deleteReviewById(reviewId);
        return "confirmation";
    }
}
