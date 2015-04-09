package de.hm.vss.philosopher.threads;

import de.hm.vss.philosopher.model.Fork;
import de.hm.vss.philosopher.model.Plate;
import de.hm.vss.philosopher.model.Table;

import java.util.Random;

/**
 * Created by Joncn on 08.04.2015.
 */
public class Philosopher extends Thread
{
    private final int MEDIATIONTIME = 5;
    private final int SLEEPTIME = 1;
    private final int EATTIME = 1;


    private final Table table;
    private final int index;
    private final boolean isVeryHungry;

    private Integer eatcounter = 0;
    private boolean run = true;

    public Philosopher(Table table, int index, boolean isVeryHungry)
    {
        this.table = table;
        this.index = index;
        this.isVeryHungry = isVeryHungry;
    }
    public Philosopher(Table table, int index)
    {
        this(table, index, false);
    }

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
                eat();
                plate.releaseForks();
                System.out.println(this + " releases forks " + leftFork.getIndex() + " and " + rightFork.getIndex());
                table.releasePlate(plate);
                System.out.println(this + " releases place " + plate.getIndex());
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
            int sleeptime;
            if(isVeryHungry)
            {
                sleeptime = MEDIATIONTIME/2;
            }
            else
            {
                sleeptime = MEDIATIONTIME;
            }
            System.out.println(this + (isVeryHungry ? " sleeping short" : " sleeping") + " (" + sleeptime + ")");
            this.sleep(sleeptime);
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
            synchronized (eatcounter)
            {
                eatcounter++;
            }
            if (getEatcounter() % 3 == 0)
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
        this.sleep(MEDIATIONTIME);
    }

    public String toString()
    {
        return "Philosopher " + index;
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
}
