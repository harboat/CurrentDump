package com.github.harboat.core.board;

import com.github.harboat.clients.core.board.BoardCreation;
import com.github.harboat.clients.core.board.BoardCreationResponse;
import com.github.harboat.clients.core.board.Size;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.core.GameQueueProducer;
import com.github.harboat.core.games.GameService;
import com.github.harboat.core.websocket.Event;
import com.github.harboat.core.websocket.WebsocketService;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;
import static org.mockito.BDDMockito.*;

@Listeners({MockitoTestNGListener.class})
public class BoardServiceTest {

    @Mock
    private GameQueueProducer producer;
    @Mock
    private GameService gameService;
    @Mock
    private WebsocketService websocketService;
    private BoardService boardService;
    @Captor
    ArgumentCaptor<BoardCreation> boardCreationCaptor;
    @Captor
    ArgumentCaptor<String> idCaptor;
    @Captor
    ArgumentCaptor<Event> eventCaptor;

    @BeforeMethod
    public void setUp() {
        boardService = new BoardService(producer, gameService, websocketService);
    }

    @Test
    public void shouldCreateBoardWithProperGameId() {
        //given
        Size size = new Size(10, 10);
        given(gameService.getNotStartedGamesIdsForUser(any())).willReturn(List.of("test"));
        //when
        boardService.create("test", "testPlayer", size);
        verify(producer).sendRequest(boardCreationCaptor.capture());
        var actual = boardCreationCaptor.getValue();
        //then
        assertEquals(actual.gameId(), "test");
    }

    @Test
    public void shouldCreateBoardWithProperPlayerId() {
        //given
        Size size = new Size(10, 10);
        given(gameService.getNotStartedGamesIdsForUser(any())).willReturn(List.of("test"));
        //when
        boardService.create("test", "testPlayer", size);
        verify(producer).sendRequest(boardCreationCaptor.capture());
        var actual = boardCreationCaptor.getValue();
        //then
        assertEquals(actual.playerId(), "testPlayer");
    }

    @Test
    public void shouldCreateBoardWithProperSize() {
        //given
        Size size = new Size(10, 10);
        given(gameService.getNotStartedGamesIdsForUser(any())).willReturn(List.of("test"));
        //when
        boardService.create("test", "testPlayer", size);
        verify(producer).sendRequest(boardCreationCaptor.capture());
        var actual = boardCreationCaptor.getValue();
        //then
        assertEquals(actual.size(), size);
    }

    @Test
    public void shouldCreateFromBoardCreationResponseWithProperId() {
        //given
        Size size = new Size(10, 10);
        BoardCreationResponse response = new BoardCreationResponse("test", "testPlayer", size);
        //when
        boardService.create(response);
        verify(websocketService).notifyFrontEnd(idCaptor.capture(), eventCaptor.capture());
        var actual = idCaptor.getValue();
        //then
        assertEquals(actual, "testPlayer");
    }

    @Test
    public void shouldCreateFromBoardCreationResponseWithProperEventType() {
        //given
        Size size = new Size(10, 10);
        BoardCreationResponse response = new BoardCreationResponse("test", "testPlayer", size);
        //when
        boardService.create(response);
        verify(websocketService).notifyFrontEnd(idCaptor.capture(), eventCaptor.capture());
        var actual = eventCaptor.getValue();
        //then
        assertEquals(actual.getEventType(), EventType.BOARD_CREATED);
    }

    @Test
    public void shouldCreateFromBoardCreationResponseWithProperSize() {
        //given
        Size size = new Size(10, 10);
        BoardCreationResponse response = new BoardCreationResponse("test", "testPlayer", size);
        //when
        boardService.create(response);
        verify(websocketService).notifyFrontEnd(idCaptor.capture(), eventCaptor.capture());
        var actual = eventCaptor.getValue();
        //then
        assertEquals(actual.getContent(), size);
    }
}
