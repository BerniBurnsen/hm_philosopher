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
    private Philosopher p = null;

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

    public int getIndex()
    {
        return index;
    }

    /*public void waitForForks(Philosopher p) throws InterruptedException
    {
        boolean obtained = false;
        int trys;
        while(!obtained)
        {
            trys = getRandomAmountOfTrys();
            synchronized (leftFork)
            {
                while (leftFork.isReserved())
                {
                    leftFork.wait();
                }
                synchronized (rightFork)
                {
                    for(int i = 0; i < trys; i++)
                    {
                        if (rightFork.isReserved())
                        {
                            rightFork.wait(2);
                        }
                        else
                        {
                            obtained = true;
                            rightFork.setIsReserved(true, p);
                            leftFork.setIsReserved(true, p);
                        }
                    }
                }
            }
            Thread.sleep(getIndex()%2 +1);
        }
        System.out.println(p + " obtained both" + leftFork + " " + rightFork);
    }*/

    public void waitForForks(Philosopher p) throws InterruptedException
    {
        Fork firstFork;
        Fork secondFork;
        if(this.getIndex() %2 == 0)
        {
            firstFork = leftFork;
            secondFork = rightFork;
        }
        else
        {
            firstFork = rightFork;
            secondFork = leftFork;
        }

        boolean obtained = false;
        int cnt = 0;
        while(!obtained)
        {
/*            if(cnt >= 0 && cnt < 5)
            {
                System.out.println(p + " " +cnt + ". try");
            }
            else if(cnt >= 5)
            {
                System.err.println(p + " " +cnt + ". try");
            }*/
            cnt++;
            synchronized (firstFork)
            {
                while (firstFork.isReserved())
                {
                    firstFork.wait(2);
                }
                synchronized (secondFork)
                {
                    if (secondFork.isReserved())
                    {
                        secondFork.wait(2);

                    }
                    else
                    {
                        obtained = true;
                        firstFork.setIsReserved(true, p);
                        secondFork.setIsReserved(true, p);
                    }

                }
            }
            //System.out.println(p + " left sync");
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
            rightFork.setIsReserved(false, p);
            rightFork.notify();
        }
    }

    private void releaseLeftFork()
    {
        synchronized (leftFork)
        {
            leftFork.setIsReserved(false, p);
            leftFork.notify();
        }
    }

    private int getRandomAmountOfTrys()
    {
        return getIndex()%2 +1;
    }

    public Fork getLeftFork()
    {
        return leftFork;
    }

    public Fork getRightFork()
    {
        return rightFork;
    }

    public String toString()
    {
        return "Plate " + getIndex();
    }

    public Philosopher getPhilosopher()
    {
        return p;
    }
}
