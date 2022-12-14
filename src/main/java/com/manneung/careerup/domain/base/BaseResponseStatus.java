package com.manneung.careerup.domain.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

//에러 메시지 모음
@AllArgsConstructor
@Getter
public enum BaseResponseStatus {
    //모듈_에러내용_ERROR


    /**
     * 1000: 기본 요청 성공, 실패
     */
    SUCCESS("1000", "요청에 성공하였습니다."),

    FAIL("1001", "요청에 실패했습니다."),


    /**
     * 2000 : User 오류
     */
    USER_EXISTS_NICKNAME_ERROR("2001","중복된 닉네임입니다."),

    USER_FAILED_TO_LOG_IN_ERROR("2002", "로그인에 실패하였습니다."),

    USER_NOT_EXIST_USER_IDX_ERROR("2003", "존재하지 않는 유저 idx입니다."),

    USER_NOT_EXIST_NICKNAME_ERROR("2004", "존재하지 않는 닉네임입니다."),

    USER_NOT_EXIST_EMAIL_ERROR("2005", "존재하지 않는 이메일입니다."),

    USER_ALREADY_DELETED_ERROR("2006", "이미 탈퇴된 유저입니다."),

    USER_NOT_EXIST_PROVIDER_ERROR("2007", "제공하지 않는 소셜 로그인입니다."),

    USER_ERROR("2008", "로그인된 유저 찾기 실패"),

    /**
     * 3000 : Map 오류
     */
    MAP_ALREADY_DELETED_ERROR("3001", "이미 삭제된 커리어맵입니다."),

    MAP_NOT_FOUND_IDX_ERROR("3002", "존재하지 않는 커리어맵입니다."),

    MAP_FAILED_TO_UPDATE_ERROR("3003", "커리어맵 업데이트에 실패했습니다."),

    MAP_EMPTY_LIST_ERROR( "3004", "커리어맵 리스트를 찾을 수 없습니다."),

    MAP_FAILED_TO_CREATE_ERROR( "3005", "커리어맵 작성에 실패했습니다."),




    /**
     * 6000 : Jwt 오류
     */
    JWT_UNKNOWN_ERROR("6001", "JWT토큰을 입력해주세요."),

    JWT_WRONG_TYPE_TOKEN("6002", "잘못된 JWT 서명입니다."),

    JWT_EXPIRED_TOKEN("6003", "만료된 토큰입니다."),

    JWT_UNSUPPORTED_TOKEN("6004", "지원되지 않는 토큰입니다."),

    JWT_ACCESS_DENIED("6005","접근이 거부되었습니다."),

    JWT_WRONG_TOKEN("6006","JWT 토큰이 잘못되었습니다.")

    ;

    private final String code;
    private final String message;
}
