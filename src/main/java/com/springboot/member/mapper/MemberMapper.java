package com.springboot.member.mapper;

import com.springboot.member.dto.MemberDto;
import com.springboot.member.dto.MyPageResponseDto;
import com.springboot.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

//매핑되지 않은 필드가 있더라도 에러를 무시하도록 설정
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    Member memberPostToMember(MemberDto.Post memberPostDto);
    Member memberPatchToMember(MemberDto.Patch memberPatchDto);
    Member memberDeleteToMember(MemberDto.Delete memberDeleteDto);
    MemberDto.Response memberToMemberResponse(Member member);
    List<MemberDto.Response> membersToMemberResponses(List<Member> members);
    MyPageResponseDto memberToMyPageResponseDto(Member member);
}