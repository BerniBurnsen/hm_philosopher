package de.hm.vss.philosopher.threads;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joncn on 15.04.2015.
 */
public class Overseer extends Thread
{
    public boolean run;
    public int maxDifferenz;
    public List<Philosopher> philosophers;

    public Overseer(List<Philosopher> philosophers, int maxDifferenz)
    {
        run = true;
        this.maxDifferenz = maxDifferenz;
        this.philosophers = philosophers;
    }

    @Override
    public void run()
    {
        int[] pCount = new int[philosophers.size()];
        while(run)
        {
            int minCount = Integer.MAX_VALUE;

            //get all eatCounts
            for(Philosopher p : philosophers)
            {
                pCount[p.getIndex()] = p.getEatcounter();
            }

            //get smallest eatcount
            for(int i = 0; i < philosophers.size(); i++)
            {
                if(pCount[i] < minCount)
                {
                    minCount = pCount[i];
                }
            }

            //punish all philosopher eating to much
            for(int i = 0; i < philosophers.size(); i++)
            {
                if(pCount[i] >= (minCount + maxDifferenz) && philosophers.get(i).isAllowedToEat())
                {
                    System.err.println("Punish philosopher " + philosophers.get(i));
                    philosophers.get(i).setAllowedToEat(false);
                }
            }
        }
    }

    @Override
    public void interrupt()
    {
        run = false;
    }
}
