package com.amany.onlinecourses_demo.service;

import com.amany.onlinecourses_demo.entity.Member;
import com.amany.onlinecourses_demo.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService {
    public Member findByUserName(String username);
    void save(WebUser webUser, String role);
    void delete(Member theMember);
}
