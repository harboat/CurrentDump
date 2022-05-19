package com.github.harboat.core.games;

import com.github.harboat.clients.core.game.GameCreation;
import com.github.harboat.clients.core.game.GameCreationResponse;
import com.github.harboat.clients.core.game.PlayerJoin;
import com.github.harboat.clients.exceptions.BadRequest;
import com.github.harboat.clients.exceptions.ResourceNotFound;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.core.GameQueueProducer;
import com.github.harboat.core.websocket.Event;
import com.github.harboat.core.websocket.WebsocketService;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;
import static org.mockito.BDDMockito.*;

@Listeners({MockitoTestNGListener.class})
public class GameServiceTest {

    @Mock
    private GameRepository repository;
    @Mock
    private GameQueueProducer producer;
    @Mock
    private WebsocketService websocketService;
    private GameService service;
    private String playerId;
    private String gameId;

    @BeforeMethod
    public void setUp() {
        service = new GameService(repository, producer, websocketService);
        playerId = "testPlayer";
        gameId = "test";
    }

    @Test
    public void shouldCreateFromPlayerIdWithProperPlayerId() {
        //given
        ArgumentCaptor<GameCreation> captor = ArgumentCaptor.forClass(GameCreation.class);
        //when
        service.create(playerId);
        verify(producer).sendRequest(captor.capture());
        var actual = captor.getValue();
        //then
        assertEquals(actual.playerId(), playerId);
    }

    @Test
    public void shouldCreateFromCreationResponseWithProperPlayerId() {
        //given
        ArgumentCaptor<String> playerIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        GameCreationResponse response = new GameCreationResponse(gameId, playerId);
        given(repository.save(any())).willReturn(null);
        //when
        service.create(response);
        verify(websocketService).notifyFrontEnd(playerIdCaptor.capture(), eventCaptor.capture());
        var actual = playerIdCaptor.getValue();
        //then
        assertEquals(actual, playerId);
    }

    @Test
    public void shouldCreateFromCreationResponseWithProperEventType() {
        //given
        ArgumentCaptor<String> playerIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        GameCreationResponse response = new GameCreationResponse(gameId, playerId);
        given(repository.save(any())).willReturn(null);
        //when
        service.create(response);
        verify(websocketService).notifyFrontEnd(playerIdCaptor.capture(), eventCaptor.capture());
        var actual = eventCaptor.getValue();
        //then
        assertEquals(actual.getEventType(), EventType.GAME_CREATED);
    }

    @Test
    public void shouldCreateFromCreationResponseWithProperGameId() {
        //given
        ArgumentCaptor<String> playerIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        GameCreationResponse response = new GameCreationResponse(gameId, playerId);
        given(repository.save(any())).willReturn(null);
        //when
        service.create(response);
        verify(websocketService).notifyFrontEnd(playerIdCaptor.capture(), eventCaptor.capture());
        var actual = eventCaptor.getValue();
        //then
        assertEquals(actual.getContent(), new GameCreated(gameId));
    }

    @Test(expectedExceptions = ResourceNotFound.class)
    public void shouldThrowWhenNoGameWithThisId() {
        //given
        given(repository.findByGameId(gameId)).willReturn(Optional.empty());
        //when
        service.join(playerId, gameId);
        //then
    }

    @Test(expectedExceptions = BadRequest.class)
    public void shouldThrowWhenThereIsAlreadyPlayerWithThisId() {
        //given
        Game game = Game.builder()
                .gameId(gameId)
                .players(List.of(playerId))
                .build();
        given(repository.findByGameId(gameId)).willReturn(Optional.of(game));
        //when
        service.join(playerId, gameId);
        //then
    }

    @Test(expectedExceptions = BadRequest.class)
    public void shouldThrowWhenGameAlreadyStarted() {
        //given
        Game game = Game.builder()
                .gameId(gameId)
                .players(List.of())
                .started(true)
                .build();
        given(repository.findByGameId(gameId)).willReturn(Optional.of(game));
        //when
        service.join(playerId, gameId);
        //then
    }
    @Test(expectedExceptions = BadRequest.class)
    public void shouldThrowWhenGameHasEnded() {
        //given
        Game game = Game.builder()
                .gameId(gameId)
                .players(List.of())
                .started(false)
                .ended(true)
                .build();
        given(repository.findByGameId(gameId)).willReturn(Optional.of(game));
        //when
        service.join(playerId, gameId);
        //then
    }

    @Test
    public void shouldJoinWithProperGameId() {
        //given
        Game game = Game.builder()
                .gameId(gameId)
                .players(List.of())
                .started(false)
                .ended(false)
                .build();
        given(repository.findByGameId(gameId)).willReturn(Optional.of(game));
        ArgumentCaptor<PlayerJoin> captor = ArgumentCaptor.forClass(PlayerJoin.class);
        //when
        service.join(playerId, gameId);
        verify(producer).sendRequest(captor.capture());
        var actual = captor.getValue();
        //then
        assertEquals(actual.gameId(), gameId);
    }

    @Test
    public void shouldJoinWithProperPlayerId() {
        //given
        Game game = Game.builder()
                .gameId(gameId)
                .players(List.of())
                .started(false)
                .ended(false)
                .build();
        given(repository.findByGameId(gameId)).willReturn(Optional.of(game));
        ArgumentCaptor<PlayerJoin> captor = ArgumentCaptor.forClass(PlayerJoin.class);
        //when
        service.join(playerId, gameId);
        verify(producer).sendRequest(captor.capture());
        var actual = captor.getValue();
        //then
        assertEquals(actual.playerId(), playerId);
    }

}