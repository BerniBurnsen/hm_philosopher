package de.hm.vss.philosopher;

import de.hm.vss.philosopher.model.Table;
import de.hm.vss.philosopher.threads.Philosopher;

public class Main
{

    public static void main(String[] args)
    {
        int numberOfPlaces = Integer.parseInt(args[0]);
        int numberOfPhilosophers = Integer.parseInt(args[1]);

        Table table = new Table(numberOfPlaces);

        for(int i = 0; i < numberOfPhilosophers; i++)
        {
            new Philosopher(table, i);
        }
    }
}
