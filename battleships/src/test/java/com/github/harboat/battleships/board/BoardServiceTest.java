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
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.mockito.BDDMockito.*;
import static org.testng.Assert.assertEquals;

@Listeners({MockitoTestNGListener.class})
public class BoardServiceTest {

    @Mock
    private BoardRepository repository;
    @Mock
    private CoreQueueProducer producer;
    private BoardService service;

    @Captor
    ArgumentCaptor<BoardCreationResponse> captor;

    @BeforeMethod
    public void setUp() {
        service = new BoardService(repository, producer);
        given(repository.save(any())).willReturn(null);
    }

    @Test
    public void shouldCreateBoardWithProperGameId() {
        //given
        Size size = new Size(10, 10);
        //when
        service.createBoard(new BoardCreation("test", "testPlayer", size));
        verify(producer).sendResponse(captor.capture());
        BoardCreationResponse actual = captor.getValue();
        //then
        assertEquals(actual.gameId(), "test");
    }

    @Test
    public void shouldCreateBoardWithProperPlayerId() {
        //given
        Size size = new Size(10, 10);
        //when
        service.createBoard(new BoardCreation("test", "testPlayer", size));
        verify(producer).sendResponse(captor.capture());
        BoardCreationResponse actual = captor.getValue();
        //then
        assertEquals(actual.playerId(), "testPlayer");
    }

    @Test
    public void shouldCreateBoardWithProperSize() {
        //given
        Size size = new Size(10, 10);
        //when
        service.createBoard(new BoardCreation("test", "testPlayer", size));
        verify(producer).sendResponse(captor.capture());
        BoardCreationResponse actual = captor.getValue();
        //then
        assertEquals(actual.size(), size);
    }
}
