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
        execute(args);
    }

    private static void execute(String[] args)
    {
        int numberOfPhilosophers = Integer.parseInt(args[0]);
        int numberOfHungryPhilosophers = Integer.parseInt(args[1]);
        int numberOfPlaces = Integer.parseInt(args[2]);
        Thread mainThread = Thread.currentThread();

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

        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                System.out.println("timer run out");
                mainThread.interrupt();
            }
        }, 62 * 1000);

        try
        {
            for(Philosopher p : philosophers)
            {
                    p.join();
            }
            overseer.join();
        } catch (InterruptedException e)
        {

        }

        System.out.println("================== TABLE ==================");
        System.out.println(table);

        System.out.println("================== STATS ==================");
        philosophers.forEach((p) -> System.out.println(p + " eats " + p.getEatcounter() + " times ::: STATE: " + p.getStateOfPhilosopher() ));

        try
        {
            Thread.sleep(1);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
