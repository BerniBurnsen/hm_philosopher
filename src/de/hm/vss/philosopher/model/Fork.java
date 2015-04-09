package de.hm.vss.philosopher.model;

/**
 * Created by Joncn on 08.04.2015.
 */
public class Fork
{
    private final int index;
    private boolean isReserved = false;

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

    public void setIsReserved(boolean isReserved)
    {
        this.isReserved = isReserved;
    }
}
