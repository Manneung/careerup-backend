package com.manneung.careerup.domain.base;

import lombok.Getter;

//에러 메시지 모음
@Getter
public enum BaseResponseStatus {

    /**
     * 1000: 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),



    /**
     * 2000 : User 오류
     */

    TOO_SHORT_PASSWORD(false, 2018, "비밀번호의 길이를 8자 이상을 설정해주세요."),

    FAILED_TO_SIGN_UP(false, 2019, "회원가입에 실패하였습니다."),

    USERS_EXISTS_EMAIL(false,2020,"중복된 이메일입니다."),

    USERS_EXISTS_NICKNAME(false,2021,"중복된 닉네임입니다."),

    POST_USERS_EMPTY_NICKNAME(false,2022,"닉네임을 입력해주세요."),

    FAILED_TO_LOG_IN(false, 2023, "로그인에 실패하였습니다."),

    NOT_EXIST_USER_ID(false, 2024, "존재하지 않는 유저 id입니다."),

    NOT_EXIST_NICKNAME(false, 2025, "존재하지 않는 닉네임입니다."),

    NOT_EXIST_EMAIL(false, 2026, "존재하지 않는 이메일입니다."),

    NOT_CORRECT_PASSWORD(false, 2027, "비밀번호가 일치하지 않습니다."),

    ALREADY_DELETED_USER(false, 2028, "이미 탈퇴된 유저입니다."),



    /**
     * 3000 : Map 오류
     */
    //todo board -> map으로 수정
    ALREADY_DELETED_ERROR(false, 5001, "이미 삭제된 게시물입니다."),

    NOT_FOUND_ID_ERROR(false, 5002, "존재하지 않는 게시글입니다."),

    UPDATE_FAILED_ERROR(false, 5003, "게시글 업데이트에 실패했습니다."),

    EMPTY_LIST_ERROR(false, 5004, "결과 리스트를 찾을 수 없습니다."),

    CREATE_FAILED_ERROR(false, 5005, "게시글 작성에 실패했습니다."),

    NOT_FOUND_NICKNAME_ERROR(false, 5006, "존재하지 않는 닉네임입니다.");











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
