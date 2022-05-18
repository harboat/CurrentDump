package com.github.harboat.core.room;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Herman Ciechanowiec
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class RoomNotFoundException extends RuntimeException {

    RoomNotFoundException(String roomNumber) {
        super(String.format("Room '%s' doesn't seem to exist", roomNumber));
    }
}
