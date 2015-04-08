package de.hm.vss.philosopher.model;

/**
 * Created by Joncn on 08.04.2015.
 */
public class Fork
{
    private final int index;

    public Fork(int index)
    {
        this.index = index;
    }

    public String toString()
    {
        return "Fork: " + index;
    }
}
