package com.remidiousE.service;

import com.remidiousE.Exceptions.MemberRegistrationException;
import com.remidiousE.Mapper.AdminMapper;
import com.remidiousE.Mapper.MemberMapper;
import com.remidiousE.dto.request.MemberRegistrationRequest;
import com.remidiousE.dto.response.MemberLoginResponse;
import com.remidiousE.dto.response.MemberRegistrationResponse;
import com.remidiousE.model.Address;
import com.remidiousE.model.Book;
import com.remidiousE.model.Member;
import com.remidiousE.repositories.AddressRepository;
import com.remidiousE.repositories.BookRepository;
import com.remidiousE.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final AddressRepository addressRepository;

    private final BookRepository bookRepository;
    @Override
    public MemberRegistrationResponse registerNewMember(MemberRegistrationRequest memberRequest) throws MemberRegistrationException {
        Member newMember = MemberMapper.map(memberRequest);
        Address address = newMember.getAddress();

        Address savedAddress = addressRepository.save(address);
        newMember.setAddress(savedAddress);

        memberRepository.save(newMember);

        MemberRegistrationResponse memberResponse = new MemberRegistrationResponse();
        memberResponse.setMessage("Your have successfully Registered");

        return memberResponse;
    }

    @Override
    public Optional<Member> findMemberById(Long id) {
        Optional<Member> foundNewMember = memberRepository.findById(id);
        return foundNewMember;
    }

    @Override
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public void deleteMemberById(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public MemberLoginResponse loginMember(MemberRegistrationRequest request) {
        MemberLoginResponse memberLoginResponse = new MemberLoginResponse();
        String username = request.getUsername();
        String password = request.getPassword();

        if (authenticate(username, password)) {
            memberLoginResponse.setMessage("You have logged in successfully");
        } else {
            memberLoginResponse.setMessage("Failed to log in");
        }

        return memberLoginResponse;
    }
    private boolean authenticate(String username, String password) {
        if (username.equals("SexyFavour") && password.equals("2233")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Book> searchBookByTitle(String title) {
        List<Book> searchResults = bookRepository.searchByTitle(title);
        return searchResults;
    }
}
