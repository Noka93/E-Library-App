package com.remidiousE.Mapper;

import com.remidiousE.dto.request.MemberRegistrationRequest;
import com.remidiousE.model.Address;
import com.remidiousE.model.Member;

public class MemberMapper {
    public static Member map(MemberRegistrationRequest request) {
        Member member = new Member();
        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setEmail(request.getEmail());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setAddress(userAddress());
        return member;
    }
    public  static Address userAddress(){
        Address address = new Address();
        address.setHouseNumber(address.getHouseNumber());
        address.setStreet(address.getStreet());
        address.setLocalGovernmentArea(address.getLocalGovernmentArea());
        address.setState(address.getState());
        return address;
    }
}