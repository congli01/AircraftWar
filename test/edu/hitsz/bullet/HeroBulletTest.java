package edu.hitsz.bullet;

import edu.hitsz.aircraft.HeroAircraft;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroBulletTest {
    HeroAircraft heroAircraft = HeroAircraft.getHeroAircraft();
    private final List<BaseBullet> heroBullets = new LinkedList<>();

    @BeforeEach
    void setUp() {
        heroBullets.addAll(heroAircraft.shoot());
    }

    @AfterEach
    void tearDown() {
        heroBullets.clear();
    }

    @DisplayName("Test getPower method")
    @Test
    void getPower() {
        System.out.println("**---Test getPower method executed---**");
        BaseBullet heroBullet = heroBullets.get(0);
        assertEquals(30, heroBullet.getPower());
    }

    @DisplayName("Test crash method")
    @Test
    void crash() {
        System.out.println("**---Test crash method executed---**");
        heroBullets.addAll(heroAircraft.shoot());
        BaseBullet heroAircraft1 = heroBullets.get(0);
        BaseBullet heroAircraft2 = heroBullets.get(1);
        assertTrue(heroAircraft1.crash(heroAircraft2));
    }
}