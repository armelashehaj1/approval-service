package net.threeisolutions.approvals.service.util.exception;

import org.springframework.http.HttpStatus;

public class ClientNotAuthorizedException extends ApiException {

    public ClientNotAuthorizedException(String message) {
        super(HttpStatus.FORBIDDEN, "CLIENT_FORBIDDEN", message);
    }
}
