package com.github.harboat.battleships.shot;

import com.github.harboat.battleships.NotificationProducer;
import com.github.harboat.battleships.fleet.FleetService;
import com.github.harboat.clients.core.shot.ShotRequest;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.clients.notification.NotificationRequest;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static org.mockito.BDDMockito.*;

@Listeners({MockitoTestNGListener.class})
public class ShotServiceTest {

    @Mock
    private ShotRepository repository;
    @Mock
    private FleetService fleetService;
    @Mock
    private NotificationProducer notificationProducer;
    private ShotService service;
    @Captor
    private ArgumentCaptor<NotificationRequest> captor;

    @BeforeMethod
    public void setUp() {
        service = new ShotService(repository, fleetService, notificationProducer);
    }

    @Test
    public void shouldTakeAShotWithProperUsername() {
        //given
        ShotRequest shotRequest = new ShotRequest("test", "testUsername", 1);
        //when
        service.takeAShoot(shotRequest);
        verify(notificationProducer).sendNotification(captor.capture());
        var actual = captor.getValue();
        //then
        assertEquals(actual.userId(), "testUsername");
    }

    @Test
    public void shouldTakeAShotWithProperEventType() {
        //given
        ShotRequest shotRequest = new ShotRequest("test", "testUsername", 1);
        //when
        service.takeAShoot(shotRequest);
        verify(notificationProducer).sendNotification(captor.capture());
        var actual = captor.getValue();
        //then
        assertEquals(actual.type(), EventType.HIT);
    }

    @Test
    public void shouldTakeAShotWithProperBody() {
        //given
        ShotRequest shotRequest = new ShotRequest("test", "testUsername", 1);
        Shot expected = Shot.builder()
                .gameId("test")
                .playerId("testUsername")
                .cellId(1)
                .shotResult(null).build();
        //when
        service.takeAShoot(shotRequest);
        verify(notificationProducer).sendNotification(captor.capture());
        var actual = captor.getValue();
        //then
        assertEquals(actual.body().toString(), expected.toString());
    }
}
