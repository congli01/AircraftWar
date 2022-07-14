package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.EliteFactory;
import edu.hitsz.prop.BaseProp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EliteEnemyTest {

    EliteEnemy eliteEnemy;
    EliteFactory eliteFactory = new EliteFactory();

    @BeforeEach
    void setUp() {
        eliteEnemy = (EliteEnemy) eliteFactory.creatEnemy();
    }

    @AfterEach
    void tearDown() {
        eliteEnemy = null;
    }

    @DisplayName("Test decrease method")
    @ParameterizedTest
    @CsvSource({"10,50","40,20"})
    void decreaseHp(int decrease, int result) {
        System.out.println("**---Test decrease method executed---**");
        eliteEnemy.decreaseHp(decrease);
        assertEquals(result, eliteEnemy.getHp());
    }

    @DisplayName("Test createProp method")
    @Test
    void createProp() {
        for(int i = 0; i < 10; i++){
            System.out.println("**---Test createProp method executed---**");
            BaseProp baseProp = eliteEnemy.createProp();
            if (baseProp != null) {
                System.out.println("A prop has been created!");   //产生了一个道具
            } else if (baseProp == null) {
                System.out.println("No prop!");     //未产生道具
            } else {
                assertTrue(false);      //若出现其他情况则方法存在错误
            }
        }
    }
}