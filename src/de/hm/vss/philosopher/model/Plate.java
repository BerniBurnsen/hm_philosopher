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

    public synchronized Fork getLeftFork() throws InterruptedException
    {
        if(leftFork.isReserved())
        {
            this.wait();
            return getLeftFork();
        }
        else
        {
            leftFork.setIsReserved(true);
            return leftFork;
        }
    }

    public synchronized Fork getRightFork() throws InterruptedException
    {
        if(rightFork.isReserved())
        {
            for(int i = 0; i < 10; i++)
            {
                this.wait(200);
            }
            return getRightFork();
        }
        else
        {
            rightFork.setIsReserved(true);
            return rightFork;
        }
    }

    public synchronized void releaseLeftFork()
    {
        leftFork.setIsReserved(false);
        this.notifyAll();
    }

    public synchronized void releaseRightFork()
    {
        rightFork.setIsReserved(false);
        this.notifyAll();
    }
}
