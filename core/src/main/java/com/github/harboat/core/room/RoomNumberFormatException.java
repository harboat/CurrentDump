package com.github.harboat.core.room;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Herman Ciechanowiec
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class RoomNumberFormatException extends RuntimeException {

    RoomNumberFormatException(String providedNum) {
        super(String.format("'%s' is not a valid room number format", providedNum));
    }
}
