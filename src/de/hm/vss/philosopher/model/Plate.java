package de.hm.vss.philosopher.model;

import de.hm.vss.philosopher.threads.Philosopher;

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

    public void waitForForks(Philosopher p) throws InterruptedException
    {
        boolean obtained = false;
        int cnt = 0;
        while(!obtained)
        {
            if(cnt >= 0 && cnt < 5)
            {
                System.out.println(p + " " +cnt + ". try");
            }
            else if(cnt >= 5)
            {
                System.err.println(p + " " +cnt + ". try");
            }
            cnt++;
            synchronized (leftFork)
            {
                if(leftFork.isReserved())
                {
                    leftFork.wait(2);
                }
                else
                {
                    synchronized (rightFork)
                    {
                        if(rightFork.isReserved())
                        {
                            rightFork.wait(2);
                        }
                        else
                        {
                            obtained=true;
                            rightFork.isReserved();
                            leftFork.isReserved();
                        }
                    }
                }
            }

        }
        System.out.println(p + " obtained both" + leftFork + " " + rightFork);
    }

    public void releaseForks()
    {
        releaseLeftFork();
        releaseRightFork();
    }
    private void releaseRightFork()
    {
        synchronized (rightFork)
        {
            rightFork.setIsReserved(false);
            rightFork.notify();
        }
    }

    private void releaseLeftFork()
    {
        synchronized (leftFork)
        {
            leftFork.setIsReserved(false);
            leftFork.notify();
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
