package com.github.harboat.placement;

import com.github.harboat.clients.core.board.Size;
import com.github.harboat.clients.core.placement.Masts;
import com.github.harboat.clients.core.placement.OccupiedCells;
import com.github.harboat.clients.core.placement.ShipType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

@Test
public class ShipCreatorTest {

    @Test
    public void shouldCreateShip() {
        //given
        Size size = new Size(10, 10);
        ShipType type = ShipType.DESTROYER;
        Masts masts = new Masts(List.of(1));
        ShipCreator shipCreator = new ShipCreator(size, type, masts);
        //when
        var actual = shipCreator.create();
        //then
        assertNotNull(actual);
    }

    @Test
    public void shouldCreateShipOfProperType() {
        //given
        Size size = new Size(10, 10);
        ShipType type = ShipType.DESTROYER;
        Masts masts = new Masts(List.of(1));
        ShipCreator shipCreator = new ShipCreator(size, type, masts);
        //when
        var actual = shipCreator.create();
        //then
        assertEquals(actual.getType(), ShipType.DESTROYER);
    }

    @Test
    public void shouldCreateShipWithProperMasts() {
        //given
        Size size = new Size(10, 10);
        ShipType type = ShipType.DESTROYER;
        Masts masts = new Masts(List.of(1));
        ShipCreator shipCreator = new ShipCreator(size, type, masts);
        //when
        var actual = shipCreator.create();
        //then
        assertEquals(actual.getMasts(), masts);
    }

    @DataProvider
    public static Object[][] occupiedCellsFor10x10Board() {
        return new Object[][]{
                {13, new OccupiedCells(List.of(2, 3, 4, 22, 23, 24, 12, 14))}
                , {1, new OccupiedCells(List.of(2, 11, 12))}
                , {10, new OccupiedCells(List.of(19, 20, 9))}
                , {91, new OccupiedCells(List.of(81, 82, 92))}
                , {100, new OccupiedCells(List.of(99, 89, 90))}
        };
    }

    @Test(dataProvider = "occupiedCellsFor10x10Board")
    public void shouldCreateShipWithProperOccupiedCells(int cellId, OccupiedCells occupiedCells) {
        //given
        Size size = new Size(10, 10);
        ShipType type = ShipType.DESTROYER;
        Masts masts = new Masts(List.of(cellId));
        ShipCreator shipCreator = new ShipCreator(size, type, masts);
        //when
        var actual = shipCreator.create();
        //then
        assertEquals(actual.getCells().toString(), occupiedCells.toString());
    }
}
