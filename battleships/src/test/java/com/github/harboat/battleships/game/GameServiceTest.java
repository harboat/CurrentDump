package com.github.harboat.battleships.game;

import com.github.harboat.battleships.CoreQueueProducer;
import com.github.harboat.battleships.board.BoardService;
import com.github.harboat.clients.core.game.GameCreation;
import com.github.harboat.clients.core.game.GameCreationResponse;
import com.github.harboat.clients.core.game.PlayerJoin;
import com.github.harboat.clients.core.game.PlayerJoinedResponse;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;
import  static  org.mockito.BDDMockito.*;

@Listeners({MockitoTestNGListener.class})
public class GameServiceTest {

    @Mock
    private GameRepository repository;
    @Mock
    private BoardService boardService;
    @Mock
    private CoreQueueProducer producer;
    private GameService service;
    @Captor
    ArgumentCaptor<GameCreationResponse> gameCaptor;
    @Captor
    ArgumentCaptor<PlayerJoinedResponse> playerCaptor;
    private Game game;

    @BeforeMethod
    public void setUp() {
        service = new GameService(repository, boardService, producer);
        game = new Game("test", List.of("testPlayer"),"testPlayer");
        given(repository.save(any())).willReturn(game);
    }

    @Test
    public void shouldCreateGameWithProperPlayerId() {
        //given
        //when
        service.createGame(new GameCreation("testPlayer"));
        verify(producer).sendResponse(gameCaptor.capture());
        GameCreationResponse actual = gameCaptor.getValue();
        //then
        assertEquals(actual.playerId(), "testPlayer");
    }
    @Test
    public void shouldCreateGameWithNotEmptyId() {
        //given
        //when
        service.createGame(new GameCreation("testPlayer"));
        verify(producer).sendResponse(gameCaptor.capture());
        GameCreationResponse actual = gameCaptor.getValue();
        //then
        assertNotNull(actual.gameId());
    }
}
