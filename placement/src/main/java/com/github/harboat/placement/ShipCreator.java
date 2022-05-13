package com.github.harboat.placement;

import com.github.harboat.clients.board.Size;
import com.github.harboat.clients.placement.Masts;
import com.github.harboat.clients.placement.OccupiedCells;
import com.github.harboat.clients.placement.ShipDto;
import com.github.harboat.clients.placement.ShipType;

import java.util.HashSet;
import java.util.Set;

public class ShipCreator {
    private final Size size;
    private final ShipType type;
    private final Masts masts;

    ShipCreator(Size size, ShipType type, Masts masts) {
        this.size = size;
        this.type = type;
        this.masts = masts;
    }

    ShipDto create() {
        OccupiedCells occupiedCells = addOccupiedFields(masts);
        return new ShipDto(type, masts, occupiedCells);
    }

    private OccupiedCells addOccupiedFields(Masts masts) {
        Set<Integer> occupied = new HashSet<>();
        masts.getPositions().forEach(p -> {
            occupied.add(p);
            addTopAndBottomFields(p, occupied);
            addLeftFields(p, occupied);
            addRightFields(p, occupied);
            masts.getPositions().forEach(occupied::remove);
        });
        return new OccupiedCells(occupied);
    }

    private void addTopAndBottomFields(int cellID, Set<Integer> positions) {
        addCellIdIfInRange(cellID - size.width(), positions);
        addCellIdIfInRange(cellID + size.width(), positions);
    }

    private void addLeftFields(int cellID, Set<Integer> positions) {
        if (!isCellOnLeftEdge(cellID)) {
            addCellIdIfInRange(cellID - 1, positions);
            addCellIdIfInRange(cellID - size.width() - 1, positions);
            addCellIdIfInRange(cellID + size.width() - 1, positions);
        }
    }

    private boolean isCellOnLeftEdge(int cellID) {
        return (cellID - 1) % size.width() == 0;
    }

    private void addRightFields(int cellID, Set<Integer> positions) {
        if (!isCellOnRightEdge(cellID)) {
            addCellIdIfInRange(cellID + 1, positions);
            addCellIdIfInRange(cellID - size.width() + 1, positions);
            addCellIdIfInRange(cellID + size.width() + 1, positions);
        }
    }

    private boolean isCellOnRightEdge(int cellID) {
        return cellID % size.width() == 0;
    }

    private void addCellIdIfInRange(int cellID, Set<Integer> positions) {
        if (isInBoardRange(cellID)) positions.add(cellID);
    }

    private boolean isInBoardRange(int cellID) {
        return cellID > 0 && cellID <= size.width() * size.height();
    }
}
