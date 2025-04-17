package com.springboot.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404,"Member not found"),
    MEMBER_EXISTS(409,"Member exists"),
    MEMBER_NOT_OWNER(403, "You are not the owner of this resource"),
    MEMBER_NAME_EXISTS(409, "Member Name exists"),
    MEMBER_PHONE_NUMBER_EXISTS(409, "Member Phone Number exists"),
    USER_NOT_LOGGED_IN(401, "You are not logged in"),
    LOGOUT_ERROR(409, "logout error"),
    INVALID_REFRESH_TOKEN(400, "유효하지 않은 리플래시 토큰입니다."),
    UNAUTHORIZED_ACCESS(403, "인증이 필요합니다."),
    ACCESS_DENIED(403, "권한이 없습니다."),
    MENU_NOT_FOUND(404, "메뉴가 존재하지 않습니다."),
    RECIPEBOARD_NOT_FOUND(404, "레시피 게시글이 존재하지 않습니다."),
    MEMBER_NOT_MATCH(403, "작성자만 접근할 수 있습니다."),
    INVALID_KEYWORD(400, "검색어가 유효하지 않습니다."),
    COLLECTION_NOT_FOUND(404, "도감이 존재하지 않습니다."),
    COLLECTION_ITEM_NOT_FOUND(404, "도감 메뉴가 존재하지 않습니다."),
    DUPLICATE_COLLECTION_MENU(409, "도감에 동일한 메뉴명이 이미 존재합니다."),
    CHALLENGE_NOT_FOUND(404, "도전과제가 존재하지 않습니다."),
    CHALLENGE_CATEGORY_NOT_FOUND(404, "도전과제 카테고리가 존재하지 않습니다."),
    EMAIL_VERIFY_FAILED(400, "이메일 인증이 필요합니다"),
    EMAIL_NOT_VERIFIED(400, "이메일 인증이 완료되지 않았습니다."),
    EMAIL_CODE_MISMATCH(400, "인증번호가 일치하지 않거나 만료되었습니다."),
    PASSWORD_MISMATCH(401, "비밀번호가 일치하지 않습니다."),
    PASSWORD_DUPLICATED(400, "이미 사용 중인 비밀번호입니다."),
    RECIPE_STEP_NOT_FOUND(404, "레시피 스텝이 존재하지 않습니다."),
    RECIPE_STEP_EXISTS(409, "레시피 스텝이 이미 존재합니다.");



    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int statusCode, String message){
        this.message = message;
        this.status = statusCode;
    }
}
