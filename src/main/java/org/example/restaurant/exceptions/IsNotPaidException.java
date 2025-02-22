package org.example.restaurant.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class IsNotPaidException extends RuntimeException {
    public IsNotPaidException(Long tableId) {
        super("Restaurant Table is not paid " + tableId);
    }
}
