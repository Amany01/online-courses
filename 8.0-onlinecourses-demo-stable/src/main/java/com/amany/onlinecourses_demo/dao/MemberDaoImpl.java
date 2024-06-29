package com.amany.onlinecourses_demo.dao;

import com.amany.onlinecourses_demo.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MemberDaoImpl implements MemberDao{
    private EntityManager entityManager;

    @Autowired
    public MemberDaoImpl (EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }
    @Override
    public Member findByMemberName(String memberName) {
        TypedQuery<Member> theQuery = entityManager.createQuery("From Member where userName =:uName", Member.class);
        theQuery.setParameter("uName", memberName);
        Member theMember = null;
        try {
            theMember = theQuery.getSingleResult();
        } catch (Exception e) {
            theMember = null;
        }
        return theMember;
    }

    @Override
    public void save(Member theMember) {
        entityManager.merge(theMember);

    }
}
