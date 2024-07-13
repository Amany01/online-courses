package com.amany.onlinecourses_demo.controller;

import com.amany.onlinecourses_demo.dao.CourseDao;
import com.amany.onlinecourses_demo.dao.MemberDao;
import com.amany.onlinecourses_demo.dao.RoleDaoImpl;
import com.amany.onlinecourses_demo.dao.StudentDao;
import com.amany.onlinecourses_demo.entity.Course;
import com.amany.onlinecourses_demo.entity.Member;
import com.amany.onlinecourses_demo.entity.Role;
import com.amany.onlinecourses_demo.entity.Student;
import com.amany.onlinecourses_demo.service.MemberService;
import com.amany.onlinecourses_demo.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private MemberService memberService;
    private StudentDao studentDao;
    private HttpSession session;
    private CourseDao courseDao;
    @Autowired
    public StudentController (MemberService theMemberService, StudentDao theStudentDao, HttpSession session, CourseDao courseDao) {
        this.memberService = theMemberService;
        this.studentDao = theStudentDao;
        this.session = session;
        this.courseDao = courseDao;
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

    @PutMapping("/{username}")
    public String updateStudent (@PathVariable String username, Model theModel) {
        Student tempStudent = studentDao.findStudentByUsername(username);
        theModel.addAttribute("student", tempStudent);
        return "update_student_form";
    }

    @PostMapping("/processUpdates")
    public String processUpdates (@Valid @ModelAttribute("student") Student theStudent, BindingResult theBindingResult) {
        if (theBindingResult.hasErrors()) {
            return "update_student_form";
        } else {
            studentDao.saveStudent(theStudent);
            return "confirmation";
        }
    }

    @DeleteMapping ("/{username}")
    public String deleteStudent (@PathVariable String username) {
        if (memberService.findByUserName(username) == null) {
            return "user_not_found";
        }
        Student theStudent = studentDao.findStudentByUsername(username);
        studentDao.deleteStudent(theStudent);
        // deleting member will delete from roles by cascading effect
        Member theMember = memberService.findByUserName(username);
        memberService.delete(theMember);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/{username}")
    public String studentCourses (@PathVariable String username, Model theModel) {
        Student tempStudent = studentDao.findStudentByUsername(username);
        List<Course> courses = tempStudent.getCourses();
        theModel.addAttribute("courses", courses);
        return "student-courses";
    }

    // add endpoint to remove course from student courses
    @DeleteMapping("courses/{courseId}")
    public String removeCourse (@PathVariable int courseId, @AuthenticationPrincipal UserDetails userDetails) {
        Student tempStudent = studentDao.findStudentByUsername(userDetails.getUsername());
        Course course = courseDao.findCourseById(courseId);
        tempStudent.getCourses().remove(course);
        studentDao.saveStudent(tempStudent);
        return "confirmation";
    }
}
