package de.hm.vss.philosopher.model;

/**
 * Created by Joncn on 08.04.2015.
 */
public class Plate
{
    private final Table table;
    private final Fork leftFork;
    private final Fork rightFork;
    private final int index;

    private boolean isReserved = false;

    public Plate(Table table, Fork leftFork, Fork rightFork, int index)
    {
        this.table = table;
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
        synchronized(table)
        {
            while(getRightFork().isReserved() && getLeftFork().isReserved())
            {
                table.wait();
            }
            leftFork.setIsReserved(true);
            rightFork.setIsReserved(true);
        }
    }

    public void releaseForks()
    {
        synchronized(table)
        {
            leftFork.setIsReserved(false);
            rightFork.setIsReserved(false);
            table.notifyAll();
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
