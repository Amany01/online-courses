package com.amany.onlinecourses_demo.service;

import com.amany.onlinecourses_demo.dao.MemberDao;
import com.amany.onlinecourses_demo.dao.RoleDao;
import com.amany.onlinecourses_demo.entity.Member;
import com.amany.onlinecourses_demo.entity.Role;
import com.amany.onlinecourses_demo.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {
    private MemberDao memberDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceImpl (MemberDao memberDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Member findByUserName(String username) {
        return memberDao.findByMemberName(username);
    }

    @Override
    @Transactional
    public void save(WebUser webUser, String role) {
        String userName = webUser.getUserName();
        String rawPassword = webUser.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        Role tempRole = new Role(userName, role);
        Member tempMember = new Member(userName, encodedPassword,true,tempRole);
        memberDao.save(tempMember);
    }
}
