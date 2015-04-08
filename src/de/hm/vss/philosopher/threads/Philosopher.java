package de.hm.vss.philosopher.threads;

import de.hm.vss.philosopher.model.Table;

/**
 * Created by Joncn on 08.04.2015.
 */
public class Philosopher extends Thread
{
    private final Table table;

    public Philosopher(Table table)
    {
        this.table = table;
    }

    public void run()
    {

    }
}
