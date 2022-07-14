package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private HeroAircraft heroAircraft;

    @BeforeEach
    void setUp() {
        heroAircraft = HeroAircraft.getHeroAircraft();
    }

    @AfterEach
    void tearDown() {
        heroAircraft = null;
    }

    @DisplayName("Test getHeroAircraft method")
    @Test
    void getHeroAircraft() {
        System.out.println("**---Test getHeroAircraft method executed---**");
        assertTrue(HeroAircraft.getHeroAircraft() instanceof HeroAircraft);
    }

    @DisplayName("Test getMaxHp method")
    @Test
    void getMaxHp() {
        System.out.println("**---Test getMaxHp method executed---**");
        assertEquals(100, heroAircraft.getMaxHp());
    }

    @DisplayName("Test shoot method")
    @Test
    void shoot() {
        System.out.println("**---Test shoot method executed---**");
        List<BaseBullet> baseBullets = heroAircraft.shoot();
        for(BaseBullet heroBullet:baseBullets) {
            assertTrue(heroBullet instanceof HeroBullet);
        }
    }

}