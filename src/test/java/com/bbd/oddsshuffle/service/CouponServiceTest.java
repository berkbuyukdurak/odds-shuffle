package com.bbd.oddsshuffle.service;

import com.bbd.oddsshuffle.constants.BetStatus;
import com.bbd.oddsshuffle.model.entity.Bet;
import com.bbd.oddsshuffle.model.entity.Coupon;
import com.bbd.oddsshuffle.model.entity.Match;
import com.bbd.oddsshuffle.repository.CouponRepository;
import com.bbd.oddsshuffle.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.task.TaskExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private CouponExpirationService couponExpirationService;

    @Mock
    private TaskExecutor taskExecutor;

    private static final int COUPON_TIMEOUT = 1; // 1 second for testing

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCoupon_Success() throws Exception {
        // Arrange
        UUID matchId = UUID.randomUUID();
        Match match = new Match();
        match.setMatchId(matchId);
        match.setHomeOdds(2.0);
        match.setDrawOdds(3.5);
        match.setAwayOdds(1.8);

        Bet bet = new Bet();
        bet.setMatch(match);
        bet.setStatus(BetStatus.PENDING);

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(couponRepository.save(any(Coupon.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Coupon coupon = couponService.createCoupon(List.of(bet));

        // Assert
        assertNotNull(coupon);
        assertEquals(1, coupon.getBets().size());
        assertEquals(BetStatus.PENDING, coupon.getBets().get(0).getStatus());
        verify(couponRepository, times(1)).save(any(Coupon.class));
    }

    @Test
    void testCreateCoupon_MatchNotFound() {
        // Arrange
        UUID matchId = UUID.randomUUID();
        Bet bet = new Bet();
        Match match = new Match();
        match.setMatchId(matchId);
        bet.setMatch(match);

        when(matchRepository.findById(matchId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> couponService.createCoupon(List.of(bet)));
        assertEquals("Match not found: " + matchId, exception.getMessage());
        verify(couponRepository, never()).save(any(Coupon.class));
    }

    @Test
    void testMarkCouponAsExpired() {
        // Arrange
        UUID couponId = UUID.randomUUID();
        Coupon coupon = new Coupon();
        coupon.setCouponId(couponId);
        coupon.setExpired(false);

        Bet bet1 = new Bet();
        bet1.setBetId(UUID.randomUUID());
        bet1.setStatus(BetStatus.PENDING);

        Bet bet2 = new Bet();
        bet2.setBetId(UUID.randomUUID());
        bet2.setStatus(BetStatus.PENDING);

        coupon.setBets(List.of(bet1, bet2));
        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));

        // Act
        couponExpirationService.markCouponAsExpired(couponId);

        // Assert
        assertTrue(coupon.isExpired());
        assertEquals(BetStatus.EXPIRED, bet1.getStatus());
        assertEquals(BetStatus.EXPIRED, bet2.getStatus());
        verify(couponRepository, times(1)).save(coupon);
    }

    @Test
    void testScheduleCouponTimeout() throws InterruptedException {
        // Arrange
        UUID couponId = UUID.randomUUID();
        Coupon coupon = new Coupon();
        coupon.setCouponId(couponId);

        doAnswer(invocation -> {
            Runnable task = invocation.getArgument(0);
            new Thread(task).start();
            return null;
        }).when(taskExecutor).execute(any());

        // Act
        couponService.scheduleCouponTimeout(coupon);

        // Sleep to allow the timeout to complete
        Thread.sleep((COUPON_TIMEOUT + 1) * 1000L);

        // Assert
        verify(couponRepository, atLeastOnce()).findById(couponId);
    }

    @Test
    void testCreateCoupon_MaxCouponsReached() {
        // Arrange
        UUID matchId = UUID.randomUUID();
        Match match = new Match();
        match.setMatchId(matchId);

        Bet bet = new Bet();
        bet.setMatch(match);

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(couponRepository.countByBetsMatch(match)).thenReturn(500L);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> couponService.createCoupon(List.of(bet)));
        assertEquals("Maximum coupon limit reached for match: " + matchId, exception.getMessage());
        verify(couponRepository, never()).save(any(Coupon.class));
    }
}