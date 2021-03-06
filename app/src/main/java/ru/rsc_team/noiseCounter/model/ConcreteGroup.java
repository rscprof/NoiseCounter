package ru.rsc_team.noiseCounter.model;

import java.util.ArrayList;

/**
 * Created by rscprof on 14.10.15.
 * ConcreteGroup is an implementation of interface Group
 */
public class ConcreteGroup implements  Group {
    // --Commented out by Inspection (22.10.15 17:24):private static final long serialVersionUID = 0L;

// --Commented out by Inspection START (22.10.15 17:24):
//    public ConcreteGroup(String name) {
//        init(name, 0);
//    }
// --Commented out by Inspection STOP (22.10.15 17:24)

    private final ArrayList<GroupTickListener> groupTickListeners = new ArrayList<>();

  /*  private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.writeInt(count);
        out.writeObject(name);
    }


        private void readObject(java.io.ObjectInputStream in)
        throws IOException, ClassNotFoundException {
            // populate the fields of 'this' from the data in 'in'...
            count=in.readInt();
            name=(String)in.readObject();
        }*/


        private int count=0;
    private String name;

    public ConcreteGroup(String name, int count) {
        init (name,count);

    }

    private void init (String name,int count) {
        this.name = name;
        this.count=count;

    }

    @Override
    public void tick() {
        count++;
        for (GroupTickListener listener:groupTickListeners) {
            if (null != listener) {
                listener.tick(this);
            }
        }
    }

    @Override
    public void clear() {
        count=0;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int addTickListener(GroupTickListener listener) {
        groupTickListeners.add(listener);
        return groupTickListeners.size()-1;
    }

    public void dropTickListener (int position) {
        groupTickListeners.set(position,null);
    }

    @Override
    public void addBorderListener(GroupBorderListener listener) {
        //TODO
    }

    @Override
    public String getName() {
        return name;
    }

}
