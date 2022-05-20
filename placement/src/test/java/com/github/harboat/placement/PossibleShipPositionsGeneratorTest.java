package com.github.harboat.placement;

import com.github.harboat.clients.game.Size;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.testng.Assert.assertEquals;

public class PossibleShipPositionsGeneratorTest {

    @DataProvider
    public Object[][] shipSizesForExtractingPositionsOnEmptyBoard() {
        return new Object[][]{
                {1, 10, 10, 100},
                {2, 10, 10, 180},
                {3, 10, 10, 160},
                {4, 10, 10, 140},
        };
    }

    @Test(dataProvider = "shipSizesForExtractingPositionsOnEmptyBoard")
    public void shouldGenerateAllPossiblePositionsOnEmptyBoard(int shipSize, int boardWidth,
                                                               int boardHeight, int expectedPositionsNumber) {
        //given
        PossibleShipPositionsGenerator generator = new PossibleShipPositionsGenerator(new Size(boardWidth, boardHeight));
        //when
        var positions = generator.generateShipPositions(shipSize, Collections.EMPTY_SET);
        //then
        assertEquals(positions.size(), expectedPositionsNumber, "Expected found positions number to be " +
                expectedPositionsNumber + " but found " + positions.size());
    }
}
