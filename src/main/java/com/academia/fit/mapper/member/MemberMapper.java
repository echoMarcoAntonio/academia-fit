package com.academia.fit.mapper.member;

import com.academia.fit.dto.member.MemberRequest;
import com.academia.fit.dto.member.MemberResponse;
import com.academia.fit.model.member.Member;
import com.academia.fit.utils.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**

 * Mapper para conversão entre Member, MemberRequest e MemberResponse.

 * Utiliza MapStruct para automação do mapeamento.

 */
@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(target = "membershipStatus", expression = "java(mapStatus(request.getMembershipStatus()))")
    Member toEntity(MemberRequest request);

    void updateEntityFromRequest(MemberRequest request, @MappingTarget Member member);

    MemberResponse toResponse(Member member);

    // Converter String -> Status
    default Status mapStatus(String status) {
        if (status == null) return null;
        return Status.fromString(status);
    }
}
