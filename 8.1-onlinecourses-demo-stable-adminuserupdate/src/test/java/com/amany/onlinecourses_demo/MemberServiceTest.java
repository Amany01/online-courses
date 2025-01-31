package com.amany.onlinecourses_demo;

import com.amany.onlinecourses_demo.dao.MemberDao;
import com.amany.onlinecourses_demo.entity.Member;
import com.amany.onlinecourses_demo.entity.Role;
import com.amany.onlinecourses_demo.service.MemberService;
import com.amany.onlinecourses_demo.user.WebUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MemberServiceTest {
    @MockBean
    private MemberDao memberDao;
    @Autowired
    private MemberService memberService;
    private WebUser sampleWebUser;
    private Member sampleMember;
    private Role role;

    @BeforeEach
    public void setUp () {
        role = new Role("Doug1", "student");
        sampleMember = new Member("Doug", "pass1234", true,role);
        sampleWebUser = new WebUser("Doug", "password1", "Doug", "Mills","doug@mills.com");
    }

    @Test
    public void testFindUserByName () {
        when(memberDao.findByMemberName("Doug")).thenReturn(sampleMember);

        Member member = memberService.findByUserName("Doug");

        assertNotNull(member);
        assertEquals("Doug", member.getUserName());
    }

    @Test
    public void testDeleteMember () {
        doNothing().when(memberDao).delete(sampleMember);

        memberService.delete(sampleMember);

        verify(memberDao, times(1)).delete(sampleMember);
    }




}
