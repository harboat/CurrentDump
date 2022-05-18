package com.github.harboat.battleships.board;

import com.github.harboat.battleships.CoreQueueProducer;
import com.github.harboat.clients.core.board.BoardCreation;
import com.github.harboat.clients.core.board.BoardCreationResponse;
import com.github.harboat.clients.core.board.Size;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.*;
import static org.testng.Assert.*;

@Listeners({MockitoTestNGListener.class})
public class BoardServiceTest {

    @Mock
    private BoardRepository repository;
    @Mock
    private CoreQueueProducer producer;
    private BoardService service;
    private final String gameId = "test";
    private final  String playerId = "testPlayer";
    private final Size size = new Size(10, 10);
    @Captor
    ArgumentCaptor<BoardCreationResponse> captor;
    @Captor
    ArgumentCaptor<Board> boardCaptor;

    @BeforeMethod
    public void setUp() {
        service = new BoardService(repository, producer);
    }

    @Test
    public void shouldCreateBoardWithProperGameId() {
        //given
        given(repository.save(any())).willReturn(null);
        //when
        service.createBoard(new BoardCreation(gameId, playerId, size));
        verify(producer).sendResponse(captor.capture());
        BoardCreationResponse actual = captor.getValue();
        //then
        assertEquals(actual.gameId(), gameId);
    }

    @Test
    public void shouldCreateBoardWithProperPlayerId() {
        //given
        given(repository.save(any())).willReturn(null);
        //when
        service.createBoard(new BoardCreation(gameId, playerId, size));
        verify(producer).sendResponse(captor.capture());
        BoardCreationResponse actual = captor.getValue();
        //then
        assertEquals(actual.playerId(), playerId);
    }

    @Test
    public void shouldCreateBoardWithProperSize() {
        //given
        given(repository.save(any())).willReturn(null);
        //when
        service.createBoard(new BoardCreation(gameId, playerId, size));
        verify(producer).sendResponse(captor.capture());
        BoardCreationResponse actual = captor.getValue();
        //then
        assertEquals(actual.size(), size);
    }

    @DataProvider
    public static Object[][] listOfCellsFor10x10Board () {
        return new Object [][] {
        {List.of(1, 2, 15)}
        ,{List.of(1)}
        ,{List.of(1, 100)}
        };
    }
    @Test(dataProvider = "listOfCellsFor10x10Board")
    public void shouldMarkOccupied(Collection<Integer> cells) {
        //given
        Board board = Board.builder()
                .gameId(gameId)
                .playerId(playerId)
                .size(size)
                .cells(IntStream.rangeClosed(1, 10 * 10).boxed()
                        .collect(Collectors.toMap((i) -> i, (i) -> Cell.WATER)))
                .build();
        given(repository.findByGameIdAndPlayerId(any(), any())).willReturn(Optional.ofNullable(board));
        //when
        service.markOccupied(gameId, playerId, cells);
        verify(repository).save(boardCaptor.capture());
        var actual = boardCaptor.getValue();
        //then
        assertEquals(actual.getCells().keySet().stream()
                .filter(i -> actual.getCells().get(i) == Cell.OCCUPIED)
                .collect(Collectors.toList()), cells);
    }

    @DataProvider
    public static Object[][] cellIdsFor10x10Board () {
        return new Object [][] {
        {1}
        ,{100}
        ,{10}
        };
    }
    @Test(dataProvider = "cellIdsFor10x10Board")
    public void shouldMarkHit(int cellId) {
        //given
        Board board = Board.builder()
                .gameId(gameId)
                .playerId(playerId)
                .size(size)
                .cells(IntStream.rangeClosed(1, 10 * 10).boxed()
                        .collect(Collectors.toMap((i) -> i, (i) -> Cell.WATER)))
                .build();
        given(repository.findByGameIdAndPlayerId(any(), any())).willReturn(Optional.ofNullable(board));
        //when
        service.markHit(gameId, playerId, cellId);
        verify(repository).save(boardCaptor.capture());
        var actual = boardCaptor.getValue();
        //then
        assertSame(actual.getCells().get(cellId), Cell.HIT);
    }
}
