package de.hm.vss.philosopher.model;

/**
 * Created by Joncn on 08.04.2015.
 */
public class Plate
{
    private final Fork leftFork;
    private final Fork rightFork;

    public Plate(Fork leftFork, Fork rightFork)
    {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }
}
