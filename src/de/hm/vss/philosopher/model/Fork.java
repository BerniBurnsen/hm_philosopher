package de.hm.vss.philosopher.model;

import de.hm.vss.philosopher.threads.Philosopher;

/**
 * Created by Joncn on 08.04.2015.
 */
public class Fork
{
    private final int index;
    private boolean isReserved = false;
    private Philosopher p = null;

    public Fork(int index)
    {
        this.index = index;
    }

    public String toString()
    {
        return "Fork: " + index;
    }

    public int getIndex() { return index; }

    public boolean isReserved()
    {
        return isReserved;
    }

    public void setIsReserved(boolean isReserved, Philosopher p)
    {
        this.isReserved = isReserved;
        if(isReserved)
        {
            this.p = p;
        }
        else
        {
            this.p = null;
        }
    }

    public Philosopher getPhilosopher()
    {
        return p;
    }
}
