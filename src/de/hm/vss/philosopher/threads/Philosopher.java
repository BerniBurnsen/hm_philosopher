package de.hm.vss.philosopher.threads;

import de.hm.vss.philosopher.model.Fork;
import de.hm.vss.philosopher.model.Plate;
import de.hm.vss.philosopher.model.Table;
import jdk.nashorn.internal.objects.NativeJava;

import java.util.Random;

/**
 * Created by Joncn on 08.04.2015.
 */
public class Philosopher extends Thread
{
    private final Table table;
    private final int index;

    public Philosopher(Table table, int index)
    {
        this.table = table;
        this.index = index;
    }

    public void run()
    {
        Plate plate;
        Fork leftFork;
        Fork rightFork;

        while(true)
        {
            meditate();
            try
            {
                System.out.println(this + " waiting for place");
                plate = table.getPlate();
                System.out.println(this + " got plate " + plate.getIndex());
                System.out.println(this + " waiting for left fork");
                leftFork = plate.getLeftFork();
                System.out.println(this + " got left fork");
                System.out.println(this + " waiting for right fork");
                rightFork = plate.getRightFork();
                System.out.println(this + " got right fork");
                eat();
                plate.releaseLeftFork();
                plate.releaseRightFork();
                table.releasePlate(plate);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void meditate()
    {
        try
        {
            System.out.println(toString() + " sleeping");
            this.sleep(new Random().nextInt((2000 - 1000) + 1) + 1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void eat()
    {
        try
        {
            System.out.println(toString() + " eating");
            this.sleep(new Random().nextInt((1000 - 500) + 1) + 500);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public String toString()
    {
        return "Philosopher " + index;
    }
}
