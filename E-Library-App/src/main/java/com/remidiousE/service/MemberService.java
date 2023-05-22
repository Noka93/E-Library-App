package com.remidiousE.service;

import com.remidiousE.Exceptions.AdminRegistrationException;
import com.remidiousE.Exceptions.MemberRegistrationException;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.request.MemberRegistrationRequest;
import com.remidiousE.dto.response.AdminLoginResponse;
import com.remidiousE.dto.response.AdminRegistrationResponse;
import com.remidiousE.dto.response.MemberLoginResponse;
import com.remidiousE.dto.response.MemberRegistrationResponse;
import com.remidiousE.model.Admin;
import com.remidiousE.model.Book;
import com.remidiousE.model.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MemberService {
    MemberRegistrationResponse registerNewMember(MemberRegistrationRequest memberRequest) throws MemberRegistrationException;

    Optional<Member> findMemberById(Long id);

    List<Member> findAllMembers();

    void deleteMemberById(Long id);

    MemberLoginResponse loginMember(MemberRegistrationRequest request);

    List<Book> searchBookByTitle(String title);
}
