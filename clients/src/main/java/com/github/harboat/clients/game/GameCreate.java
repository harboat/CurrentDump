package com.github.harboat.clients.game;

import java.util.Collection;
import java.util.Map;

public record GameCreate(Size size, Map<String, Collection<ShipDto>> fleet) {
}
