package com.gamedesire.pszemek.recruitment.actors.interfaces;

/**
 * Created by Ciemek on 01/05/16.
 */
public interface IDamageable {

    //todo: set healthPoints and shields
    void    onSpawn();

    //todo: paint red for 1-2 frames
    float   onHit(float damageDealt);

    //todo: remove (implement callbacks or sth?)
    void    onDeath();


}
