package com.manneung.careerup.domain.base;

import lombok.Getter;

//에러 메시지 모음
@Getter
public enum BaseResponseStatus {
    //모듈_에러내용_ERROR


    /**
     * 1000: 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),



    /**
     * 2000 : User 오류
     */
    USER_EXISTS_NICKNAME_ERROR(false,2001,"중복된 닉네임입니다."),

    USER_FAILED_TO_LOG_IN_ERROR(false, 2002, "로그인에 실패하였습니다."),

    USER_NOT_EXIST_USER_IDX_ERROR(false, 2003, "존재하지 않는 유저 idx입니다."),

    USER_NOT_EXIST_NICKNAME_ERROR(false, 2004, "존재하지 않는 닉네임입니다."),

    USER_NOT_EXIST_EMAIL_ERROR(false, 2005, "존재하지 않는 이메일입니다."),

    USER_ALREADY_DELETED_ERROR(false, 2006, "이미 탈퇴된 유저입니다."),



    /**
     * 3000 : Map 오류
     */
    MAP_ALREADY_DELETED_ERROR(false, 3001, "이미 삭제된 커리어맵입니다."),

    MAP_NOT_FOUND_IDX_ERROR(false, 3002, "존재하지 않는 커리어맵입니다."),

    MAP_FAILED_TO_UPDATE_ERROR(false, 3003, "커리어맵 업데이트에 실패했습니다."),

    MAP_EMPTY_LIST_ERROR(false, 3004, "커리어맵 리스트를 찾을 수 없습니다."),

    MAP_FAILED_TO_CREATE_ERROR(false, 3005, "커리어맵 작성에 실패했습니다."),







    ;







    //contructor
    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
