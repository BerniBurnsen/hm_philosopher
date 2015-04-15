package de.hm.vss.philosopher.threads;

import de.hm.vss.philosopher.model.Fork;
import de.hm.vss.philosopher.model.Plate;
import de.hm.vss.philosopher.model.Table;

/**
 * Created by Joncn on 08.04.2015.
 */
public class Philosopher extends Thread
{
    private final int MEDITATIONTIME = 5;
    private final int SLEEPTIME = 10;
    private final int PENALTYTIME = 10;
    private final int EATTIME = 1;


    private final Table table;
    private final int index;
    private final boolean isVeryHungry;

    private int eatcounter = 0;
    private boolean run = true;
    private boolean allowedToEat = true;

    public Philosopher(Table table, int index, boolean isVeryHungry)
    {
        this.table = table;
        this.index = index;
        this.isVeryHungry = isVeryHungry;
    }

    @Override
    public void run()
    {
        Plate plate;
        Fork leftFork;
        Fork rightFork;

        while(run)
        {
            meditate();
            try
            {
                System.out.println(this + " waiting for place");
                plate = table.getPlate();
                leftFork = plate.getLeftFork();
                rightFork = plate.getRightFork();
                System.out.println(this + " got place " + plate.getIndex());
                System.out.println(this + " waiting for forks " + leftFork.getIndex() + " and " + rightFork.getIndex());
                plate.waitForForks();
                System.out.println(this + " got forks " + leftFork.getIndex() + " and " + rightFork.getIndex());
                if(isAllowedToEat() && run)
                {
                    eat();
                }
                plate.releaseForks();
                System.out.println(this + " releases forks " + leftFork.getIndex() + " and " + rightFork.getIndex());
                table.releasePlate(plate);
                System.out.println(this + " releases place " + plate.getIndex());
                if(!isAllowedToEat())
                {
                    takePenalty();
                }
            } catch (InterruptedException e)
            {
                run = false;
            }
        }
    }

    private void meditate()
    {
        try
        {
            int meditationTime;
            if(isVeryHungry)
            {
                meditationTime = MEDITATIONTIME /2;
            }
            else
            {
                meditationTime = MEDITATIONTIME;
            }
            System.out.println(this + (isVeryHungry ? " meditate short" : " meditate") + " (" + meditationTime + ")");
            this.sleep(meditationTime);
        } catch (InterruptedException e)
        {
            run = false;
        }
    }

    private void eat()
    {
        try
        {
            System.out.println(this + " eating");
            this.sleep(EATTIME);
            synchronized (this)
            {
                eatcounter++;
            }
            if (getEatcounter() % 3 == 2)
            {
                goSleeping();
            }
        } catch (InterruptedException e)
        {
            run = false;
        }
    }

    private void goSleeping() throws InterruptedException
    {
        System.out.println(this + " sleeping");
        this.sleep(SLEEPTIME);
    }

    private void takePenalty() throws InterruptedException
    {
        System.err.println(this + " taking penalty");
        this.sleep(PENALTYTIME);
        setAllowedToEat(true);
    }

    public synchronized int getEatcounter()
    {
        return eatcounter;
    }

    @Override
    public void interrupt()
    {
        run = false;
    }

    public int getIndex()
    {
        return index;
    }

    public synchronized void setAllowedToEat(boolean allowed)
    {
        allowedToEat = allowed;
    }

    public synchronized boolean isAllowedToEat()
    {
        return allowedToEat;
    }

    public String toString()
    {
        return "Philosopher " + getIndex();
    }
}
