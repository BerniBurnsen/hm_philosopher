package de.hm.vss.philosopher.model;

import de.hm.vss.philosopher.threads.Philosopher;

import java.util.Random;

/**
 * Created by Joncn on 08.04.2015.
 */
public class Table
{
    private final int seats;
    private final Fork[] forks;
    private final Plate[] plates;
    private final Random random = new Random();

    public Table(int seats)
    {
        this.seats = seats;
        forks = new Fork[seats];
        plates = new Plate[seats];
        for(int i = 0; i < seats; i++)
        {
            forks[i] = new Fork(i);
        }
        for(int i = 0; i < seats; i++)
        {
            plates[i] = new Plate(this, forks[i], forks[i == (seats-1) ? 0 : (i+1)], i);
        }
    }

    public Plate getPlate(Philosopher p) throws InterruptedException
    {
        int startIndex = p.getIndex() % seats;
        Plate plate = plates[startIndex];
        for (int i = 0; i < seats; i++)
        {
            synchronized (plate)
            {
                if (!plate.isReserved())
                {
                    System.out.println(p + " got " + plate);
                    plate.setIsReserved(true, p);
                    return plate;
                }
            }
            plate = plates[(startIndex + i) % seats];
        }
        synchronized (plate)
        {
            while(plate.isReserved())
            {
                plate.wait();
            }
            plate.setIsReserved(true, p);
            return plate;
        }
    }

    public void releasePlate(Plate plate, Philosopher p)
    {
        synchronized (plate)
        {
            System.out.println(plate + " is now free");
            plate.setIsReserved(false, p);
            plate.notify();
        }
    }

    /*public synchronized Plate getPlate() throws InterruptedException
    {
        for(int i = 0; i < seats; i++)
        {
            if(!plates[i].isReserved())
            {
                return plates[i];
            }
        }
        wait();
        return getPlate();
    }


    public synchronized void releasePlate(Plate plate)
    {
        plate.setIsReserved(false);
        notify();
    }*/

    public String toString()
    {
        //table stats for debug purpose
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < forks.length ; i++)
        {
            final Fork f = forks[i];
            sb.append("fork: " + f.getIndex() + " isReserved: " + f.isReserved() + " by " + f.getPhilosopher());
            sb.append("\n");
        }

        for(int i = 0 ; i < plates.length ; i++)
        {
            final Plate p = plates[i];
            sb.append("Plate: " + p.getIndex() + " isReserved: " + p.isReserved() + " by " + p.getPhilosopher());
            sb.append("\n");
        }
        return sb.toString();
    }

    public int getSeats()
    {
        return seats;
    }
}
