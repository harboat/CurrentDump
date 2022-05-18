package com.github.harboat.core.room;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Herman Ciechanowiec
 */
@ResponseStatus(HttpStatus.CONFLICT)
class RoomFullyOccupiedException extends RuntimeException {

    RoomFullyOccupiedException(String roomNumber) {
        super(String.format("There is no free space in the room number '%s'", roomNumber));
    }
}
