package de.hm.vss.philosopher.model;

/**
 * Created by Joncn on 08.04.2015.
 */
public class Plate
{
    private final Fork leftFork;
    private final Fork rightFork;
    private final int index;

    private boolean isReserved = false;

    public Plate(Fork leftFork, Fork rightFork, int index)
    {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.index = index;
    }

    public boolean isReserved()
    {
        return isReserved;
    }

    public void setIsReserved(boolean isReserved)
    {
        this.isReserved = isReserved;
    }

    public int getIndex()
    {
        return index;
    }

    public void waitForForks() throws InterruptedException
    {
        synchronized (getLeftFork())
        {
            if(getLeftFork().isReserved())
            {
                getLeftFork().wait();
            }
            synchronized (getRightFork())
            {
                if(getRightFork().isReserved())
                {
                    getRightFork().wait();
                }
                getRightFork().setIsReserved(true);
                getLeftFork().setIsReserved(true);
            }
        }
    }

    public void releaseForks()
    {
        synchronized (getLeftFork())
        {
            getLeftFork().setIsReserved(false);
            getLeftFork().notifyAll();
        }
        synchronized (getRightFork())
        {
            getRightFork().setIsReserved(false);
            getRightFork().notifyAll();
        }
    }

    public Fork getLeftFork()
    {
        return leftFork;
    }

    public Fork getRightFork()
    {
        return rightFork;
    }
}
