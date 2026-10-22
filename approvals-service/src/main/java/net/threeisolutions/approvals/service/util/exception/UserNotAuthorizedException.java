package net.threeisolutions.approvals.service.util.exception;

import org.springframework.http.HttpStatus;

public class UserNotAuthorizedException extends ApiException {

    public UserNotAuthorizedException(String message) {
        super(HttpStatus.FORBIDDEN, "USER_FORBIDDEN", message);
    }
}
