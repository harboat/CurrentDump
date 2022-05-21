package com.github.harboat.placement;

import com.github.harboat.clients.configuration.SetShipsPosition;
import com.github.harboat.clients.game.Size;
import com.github.harboat.clients.placement.GeneratePlacement;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.mockito.BDDMockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Listeners({MockitoTestNGListener.class})
public class PlacementServiceTest {

    @Mock
    private PlacementRepository repository;
    @Mock
    private ConfigQueueProducer sender;
    private PlacementService service;

    @BeforeMethod
    public void setUp() {
        service = new PlacementService(repository, sender);
    }

    @Test
    public void shouldGenerateWithProperRoomId() {
        //given
        ArgumentCaptor<SetShipsPosition> captor = ArgumentCaptor.forClass(SetShipsPosition.class);
        Size size = new Size(10, 10);
        GeneratePlacement placement = new GeneratePlacement("testRoom", "testPlayer", size);
        //when
        service.generate(placement);
        verify(sender).sendPlacement(captor.capture());
        var actual = captor.getValue();
        //then
        assertEquals(actual.roomId(), "testRoom");
    }

    @Test
    public void shouldGenerateWithProperPlayerId() {
        //given
        ArgumentCaptor<SetShipsPosition> captor = ArgumentCaptor.forClass(SetShipsPosition.class);
        Size size = new Size(10, 10);
        GeneratePlacement placement = new GeneratePlacement("testRoom", "testPlayer", size);
        //when
        service.generate(placement);
        verify(sender).sendPlacement(captor.capture());
        var actual = captor.getValue();
        //then
        assertEquals(actual.playerId(), "testPlayer");
    }

    @Test
    public void shouldGenerateNotNullFleet() {
        //given
        ArgumentCaptor<SetShipsPosition> captor = ArgumentCaptor.forClass(SetShipsPosition.class);
        Size size = new Size(10, 10);
        GeneratePlacement placement = new GeneratePlacement("testRoom", "testPlayer", size);
        given(repository.save(any())).willReturn(null);
        //when
        service.generate(placement);
        verify(sender).sendPlacement(captor.capture());
        var actual = captor.getValue();
        //then
        assertNotNull(actual.ships());
    }

    @Test
    public void shouldGenerate10ShipsFleet() {
        //given
        ArgumentCaptor<SetShipsPosition> captor = ArgumentCaptor.forClass(SetShipsPosition.class);
        Size size = new Size(10, 10);
        GeneratePlacement placement = new GeneratePlacement("testRoom", "testPlayer", size);
        given(repository.save(any())).willReturn(null);
        //when
        service.generate(placement);
        verify(sender).sendPlacement(captor.capture());
        var actual = captor.getValue();
        //then
        assertEquals(actual.ships().size(), 10);
    }
}
