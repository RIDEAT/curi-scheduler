package com.scheduler.curi.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    UNEXPECTED_SERVER_ERROR("SERVER-001", "서버 관리자에게 문의하세요."),

    MISSING_REQUIRED_VALUE_ERROR("COMMON-001", "필수 요청값이 누락되었습니다."),
    NOT_ALLOWED_PERMISSION_ERROR("COMMON-002", "허용되지 않은 권한입니다."),
    DUPLICATED_REQUEST_ERROR("COMMON-003", "중복된 요청입니다."),
    INVALID_REQUEST_ERROR("COMMON-004", "올바르지 않은 데이터 요청입니다."),
    ASYNC_HANDLING_ERROR("COMMON-005", "비동기 처리에서 문제가 발생했습니다."),
    NETWORK_ERROR("COMMON-006", "네트워크 처리에서 문제가 발생했습니다."),
    INVALID_URL_ERROR ("COMMON-007", "올바르지 않은 URL입니다."),

    WORKSPACE_NOT_EXISTS("WORKSPACE-001", "존재하지 않는 워크 스페이스입니다."),
    DUPLICATED_WORKSPACE_NAME ("WORKSPACE-002", "중복된 워크 스페이스 이름입니다."),
    USER_NOT_EXISTS ("USER-001", "존재하지 않는 유저입니다."),

    FIREBASE_TOKEN_NULL ("FIREBASE-001", "firebase access token이 null입니다."),

    TOKENS_NOT_VALID ("TOKEN-001", "auth token과 refresh token 모두 유효하지 않습니다."),
    NO_BEARER_AUTH ("TOKEN-002", "auth token은 Bearer로 시작해야 합니다.");

    private final String errorCode;
    private final String message;
}
