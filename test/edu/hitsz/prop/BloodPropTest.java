package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.factory.BloodPropFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BloodPropTest {
    HeroAircraft heroAircraft = HeroAircraft.getHeroAircraft();
    BloodProp bloodProp;

    @BeforeEach
    void setUp() {
        bloodProp = new BloodProp(Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                0, 0);
    }

    @AfterEach
    void tearDown() {
        bloodProp = null;
    }

    @DisplayName("Test vanish method")
    @Test
    void vanish() {
        System.out.println("**---Test vanish method executed---**");
        bloodProp.vanish();
        assertTrue(bloodProp.notValid());
    }

    @DisplayName("Test func method")
    @ParameterizedTest
    @CsvSource({"40,70","20,90","0,100"})
    void func(int decrease, int hp) {
        System.out.println("**---Test func method executed---**");
        heroAircraft.decreaseHp(decrease);
        bloodProp.func();
        assertEquals(hp, heroAircraft.getHp());
        heroAircraft.decreaseHp(-(decrease-10)); //恢复到英雄机原来的生命值
    }
}