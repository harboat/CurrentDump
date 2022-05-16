package com.github.harboat.battleships.game;

import com.github.harboat.battleships.CoreQueueProducer;
import com.github.harboat.battleships.board.BoardService;
import com.github.harboat.clients.core.game.GameCreation;
import com.github.harboat.clients.core.game.GameCreationResponse;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

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
    ArgumentCaptor<GameCreationResponse> captor;

    @BeforeMethod
    public void setUp() {
        service = new GameService(repository, boardService, producer);
        given(repository.save(any())).willReturn(null);
    }
//
//    @Test
//    public void shouldCreateGameWithProperPlayerId() {
//        //given
//        //when
//        service.createGame(new GameCreation("testPlayer"));
//        verify(producer).sendResponse(captor.capture());
//        GameCreationResponse actual = captor.getValue();
//        //then
//        assertEquals(actual.playerId(), "testPlayer");
//    }

}