package com.github.harboat.battleships.fleet;

import com.github.harboat.clients.core.placement.OccupiedCells;
import com.github.harboat.clients.core.placement.ShipType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.testng.Assert.*;

@Test
public class FleetTest {

    private Ship ship1;
    private Ship ship2;

    @BeforeMethod
    public void setUp() {
        ship1 = new Ship(ShipType.DESTROYER,
                new MastsState(new HashMap<Integer, MastState>() {{
                    put(1, MastState.ALIVE);
                }}),
                new OccupiedCells(Arrays.asList(2, 11, 12)));

        ship2 = new Ship(ShipType.SUBMARINE,
                new MastsState(new HashMap<Integer, MastState>() {{
                    put(3, MastState.ALIVE);
                    put(4, MastState.ALIVE);
                }}),
                new OccupiedCells(Arrays.asList(2, 12, 13, 14, 15, 5)));

    }

    @Test
    public void shouldReturnProperCells() {
        //given
        Fleet fleet = new Fleet("test", "test", "test", Arrays.asList(ship1, ship2));
        //when
        Collection<Integer> actual = fleet.getAllCells();
        //then
        assertTrue(actual.containsAll(List.of(2, 11, 12, 13, 14, 15, 5)));
    }

    @Test
    public void shouldReturnDTOWithProperGameId() {
        //given
        Fleet fleet = new Fleet("test", "test", "test", Arrays.asList(ship1, ship2));
        //when
        FleetDto actual = fleet.toDto();
        //then
        assertEquals(actual.getGameId(), "test");
    }

    @Test
    public void shouldReturnDTOWithProperPlayerId() {
        //given
        Fleet fleet = new Fleet("test", "test", "test", Arrays.asList(ship1, ship2));
        //when
        FleetDto actual = fleet.toDto();
        //then
        assertEquals(actual.getPlayerId(), "test");
    }

    @Test
    public void shouldReturnDTOWithProperShips() {
        //given
        Fleet fleet = new Fleet("test", "test", "test", Arrays.asList(ship1, ship2));
        //when
        FleetDto actual = fleet.toDto();
        //then
        assertEquals(actual.getShips().toString(),
                Stream.of(ship1, ship2).map(Ship::toDto).toList().toString());
    }
}
