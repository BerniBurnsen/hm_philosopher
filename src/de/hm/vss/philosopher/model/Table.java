package de.hm.vss.philosopher.model;

/**
 * Created by Joncn on 08.04.2015.
 */
public class Table
{
    private final int seats;
    private final Fork[] forks;
    private final Plate[] plates;

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

    public synchronized Plate getPlate() throws InterruptedException
    {
        Plate freePlate = null;
        for(int i = 0; i < seats; i++)
        {
            if(!plates[i].isReserved())
            {
                freePlate = plates[i];
            }
        }
        if(freePlate != null)
        {
            freePlate.setIsReserved(true);
            return freePlate;
        }
        else
        {
            this.wait();
            return getPlate();
        }
    }

    public synchronized void releasePlate(Plate plate)
    {
        plates[plate.getIndex()].setIsReserved(false);
        this.notifyAll();
    }

    public String toString()
    {
        //table stats for debug purpose
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < forks.length ; i++)
        {
            final Fork f = forks[i];
            sb.append("fork: " + f.getIndex() + " isReserved: " + f.isReserved());
            sb.append("\n");
        }

        for(int i = 0 ; i < plates.length ; i++)
        {
            final Plate p = plates[i];
            sb.append("Plate: " + p.getIndex() + " isReserved: " + p.isReserved());
            sb.append("\n");
        }
        return sb.toString();
    }
}
