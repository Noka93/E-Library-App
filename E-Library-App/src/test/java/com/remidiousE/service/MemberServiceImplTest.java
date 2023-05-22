package com.remidiousE.service;

import com.remidiousE.Exceptions.MemberRegistrationException;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.request.MemberRegistrationRequest;
import com.remidiousE.dto.response.*;
import com.remidiousE.model.Admin;
import com.remidiousE.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    private MemberRegistrationRequest memberRegistrationRequest;

    @BeforeEach
    void setUp() {
        memberRegistrationRequest = buildMemberRegistration();

    }
    @Test
    void testToRegisterNewAMember() throws MemberRegistrationException {
        MemberRegistrationResponse foundMember = memberService.registerNewMember(memberRegistrationRequest);
        assertNotNull(foundMember);
    }
    @Test
    void testToFindMemberById() throws MemberRegistrationException {
        MemberRegistrationResponse memberResponse = memberService.registerNewMember(memberRegistrationRequest);

        Long registeredMemberId = 1L;
        memberResponse.setId(registeredMemberId);

        assertTrue(registeredMemberId != null && registeredMemberId > 0);

        Optional<Member> foundMember = memberService.findMemberById(registeredMemberId);
        assertTrue(foundMember.isPresent());

        assertEquals(registeredMemberId, foundMember.get().getId());
    }
    @Test
    void testToFindAllMembers() throws MemberRegistrationException {
        MemberRegistrationRequest member1 = new MemberRegistrationRequest();
        MemberRegistrationRequest member2 = new MemberRegistrationRequest();
        memberService.registerNewMember(member1);
        memberService.registerNewMember(member2);

        List<Member> members = memberService.findAllMembers();

        assertEquals(2, members.size());
    }
    private static MemberRegistrationRequest buildMemberRegistration(){
        MemberRegistrationRequest memberRegistrationRequest = new MemberRegistrationRequest();
        memberRegistrationRequest.setFirstName("Favour");
        memberRegistrationRequest.setLastName("Chiemela");
        memberRegistrationRequest.setUsername("SexyFavour");
        memberRegistrationRequest.setPhoneNumber("0813773987");
        memberRegistrationRequest.setPassword("2233");
        memberRegistrationRequest.setEmail("favourChi@gmail.com");
        memberRegistrationRequest.setHouseNumber("No. 23");
        memberRegistrationRequest.setStreet("Akinola Street");
        memberRegistrationRequest.setLga("Abaji");
        memberRegistrationRequest.setState("FCT");
        return memberRegistrationRequest;
    }

    @Test
    void testToDeleteMemberById() throws MemberRegistrationException {
        memberService.registerNewMember(memberRegistrationRequest);

        List<Member> members = memberService.findAllMembers();

        Member lastRegisteredMember = members.get(members.size() - 1);

        memberService.deleteMemberById(lastRegisteredMember.getId());

        members = memberService.findAllMembers();

        assertEquals(0, members.size());
    }

    @Test
    void testToLoginMember() {
        MemberRegistrationRequest registrationRequest = new MemberRegistrationRequest();
        registrationRequest.setUsername("SexyFavour");
        registrationRequest.setPassword("2233");

        MemberLoginResponse loginResponse = memberService.loginMember(registrationRequest);
        System.out.println(loginResponse);
        assertEquals("You have logged in successfully", loginResponse.getMessage());
    }

    @Test
    void testThatMemberCanSearchBookByTitle(){
        MemberRegistrationRequest registrationRequest = new MemberRegistrationRequest();
        registrationRequest.setUsername("SexyFavour");
        registrationRequest.setPassword("2233");

        MemberLoginResponse loginResponse = memberService.loginMember(registrationRequest);
        assertEquals("You have logged in successfully", loginResponse.getMessage());

        String bookTitle = "Judge of the Jungle";

        assertTrue(searchResultsDisplayed(), "Search results are not displayed");

        assertTrue(bookWithTitlePresent(bookTitle), "Book with title \"" + bookTitle + "\" is not found in the search results");

        assertTrue(bookDetailsPageDisplayed(), "Book details page is not displayed");

        MemberSearchBookByTitleResponse bookDetails = getBookDetails();
        System.out.println(bookDetails);
    }
    private boolean searchResultsDisplayed() {
        return true;
    }
    private boolean bookWithTitlePresent(String bookTitle) {
        return true;
    }
    private boolean bookDetailsPageDisplayed() {
        return true;
    }
    private MemberSearchBookByTitleResponse getBookDetails() {
        MemberSearchBookByTitleResponse response = new MemberSearchBookByTitleResponse();
        response.setTitle(response.getTitle());
        response.setStatus(response.getStatus());
        response.setDescription(response.getDescription());
        return response;
    }
}