package com.amany.onlinecourses_demo.dao;

import com.amany.onlinecourses_demo.entity.Member;

public interface MemberDao {
    Member findByMemberName (String memberName);
    void save (Member theMember);
    void delete(Member theMember);
}
