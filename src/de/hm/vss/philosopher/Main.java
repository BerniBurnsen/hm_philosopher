package de.hm.vss.philosopher;

import de.hm.vss.philosopher.model.Table;
import de.hm.vss.philosopher.threads.Overseer;
import de.hm.vss.philosopher.threads.Philosopher;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main
{
    public static void main(String[] args)
    {
        int numberOfPhilosophers = Integer.parseInt(args[0]);
        int numberOfHungryPhilosophers = Integer.parseInt(args[1]);
        int numberOfPlaces = Integer.parseInt(args[2]);

        Table table = new Table(numberOfPlaces);
        List<Philosopher> philosophers = new ArrayList<>();

        for(int i = 0; i < numberOfPhilosophers; i++)
        {
            philosophers.add(new Philosopher(table, i, i >= numberOfPhilosophers - numberOfHungryPhilosophers ? true : false));
        }
        Overseer overseer = new Overseer(philosophers, 10);

        overseer.start();
        philosophers.forEach((philosopher) -> philosopher.start());

        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                System.out.println("timer run out");

                for (Philosopher p : philosophers)
                {
                    p.interrupt();
                }
                overseer.interrupt();
            }
        }, 60 * 1000);

        for(Philosopher p : philosophers)
        {
            try
            {
                p.join();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            overseer.join();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("\n");
        System.out.println("================== FORKS ==================");
        System.out.println(table +"\n");

        System.out.println("================== STATS ==================");
        philosophers.forEach((p) -> System.out.println(p + " eats " + p.getEatcounter() + " times"));
    }
}
