package com.gamedesire.pszemek.recruitment.actors.interfaces;

/**
 * Created by Ciemek on 01/05/16.
 */
public interface IDamageable {

    float maxHealthPoints = 10;
    float actualHealthPoints = 10;

    float maxShieldPoints = 0;
    float actualShieldPoints = 0;



    void    onSpawn();

    float   onHit();

    void    onDeath();

}
