package com.github.harboat.core.room;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Herman Ciechanowiec
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class NoSpecifiedUserInRoomException extends RuntimeException {

    NoSpecifiedUserInRoomException(int roomNumber, String username) {
        super(String.format("There is no user '%s' in the room #'%d'", username, roomNumber));
    }
}
