package com.github.harboat.core.games;

import com.github.harboat.clients.core.game.GameCreation;
import com.github.harboat.clients.core.game.GameCreationResponse;
import com.github.harboat.clients.core.game.GameStart;
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
import java.util.NoSuchElementException;
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
    private String enemyId;
    private String gameId;

    @BeforeMethod
    public void setUp() {
        service = new GameService(repository, producer, websocketService);
        playerId = "testPlayer";
        gameId = "test";
        enemyId = "testEnemy";
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

    @Test(expectedExceptions = ResourceNotFound.class, expectedExceptionsMessageRegExp = "Game not found!")
    public void joinShouldThrowWhenNoGameWithThisId() {
        //given
        given(repository.findByGameId(gameId)).willReturn(Optional.empty());
        //when
        service.join(playerId, gameId);
        //then
    }

    @Test(expectedExceptions = BadRequest.class, expectedExceptionsMessageRegExp = "You are already in this game!")
    public void joinShouldThrowWhenThereIsAlreadyPlayerWithThisId() {
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

    @Test(expectedExceptions = BadRequest.class, expectedExceptionsMessageRegExp = "Game already started!")
    public void joinShouldThrowWhenGameAlreadyStarted() {
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

    @Test(expectedExceptions = BadRequest.class, expectedExceptionsMessageRegExp = "Game has ended!")
    public void joinShouldThrowWhenGameHasEnded() {
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

    @Test(expectedExceptions = NoSuchElementException.class)
    public void startShouldThrowWhenThereIsNoGameWithThisId() {
        //given
        given(repository.findByGameId(gameId)).willReturn(Optional.empty());
        //when
        service.start(playerId, gameId);
        //then
    }

    @Test(expectedExceptions = BadRequest.class, expectedExceptionsMessageRegExp = "You are not an owner of this lobby!")
    public void startShouldThrowWhenPlayerIsNotOwner() {
        //given
        Game game = Game.builder()
                .gameId(gameId)
                .ownerId(enemyId)
                .build();
        given(repository.findByGameId(gameId)).willReturn(Optional.of(game));
        //when
        service.start(playerId, gameId);
        //then
    }

    @Test(expectedExceptions = BadRequest.class, expectedExceptionsMessageRegExp = "Game is not ready!")
    public void startShouldThrowWhenGameIsNotSet() {
        //given
        Game game = Game.builder()
                .gameId(gameId)
                .ownerId(playerId)
                .feelWasSet(List.of(false))
                .build();
        given(repository.findByGameId(gameId)).willReturn(Optional.of(game));
        //when
        service.start(playerId, gameId);
        //then
    }

    @Test(expectedExceptions = BadRequest.class, expectedExceptionsMessageRegExp = "Game already started!")
    public void startShouldThrowWhenGameHasStarted() {
        //given
        Game game = Game.builder()
                .gameId(gameId)
                .ownerId(playerId)
                .feelWasSet(List.of())
                .started(true)
                .build();
        given(repository.findByGameId(gameId)).willReturn(Optional.of(game));
        //when
        service.start(playerId, gameId);
        //then
    }

    @Test()
    public void shouldStartWithProperGameId() {
        //given
        Game game = Game.builder()
                .gameId(gameId)
                .ownerId(playerId)
                .feelWasSet(List.of())
                .started(false)
                .build();
        given(repository.findByGameId(gameId)).willReturn(Optional.of(game));
        ArgumentCaptor<GameStart> captor = ArgumentCaptor.forClass(GameStart.class);
        //when
        service.start(playerId, gameId);
        verify(producer).sendRequest(captor.capture());
        var actual = captor.getValue();
        //then
        assertEquals(actual.gameId(), gameId);
    }

}