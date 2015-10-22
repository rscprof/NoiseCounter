package ru.rsc_team.noiseCounter.model;

import java.util.ArrayList;

/**
 * Created by rscprof on 14.10.15.
 *
 * This class provide the model of all stored information
 *
 * We store information by groups (of students)
 */
//TODO delete listener's
public class Model// implements Iterable<Group>
{

    private static final Model ourInstance = new Model();
    private final ArrayList<Group> groupList=new ArrayList<>();

    public static Model getInstance() {
        return ourInstance;
    }

    private Model() {
    }

/*    @Override
    //TODO
    public Group next() {
        return null;
    }

    @Override
    //TODO
    public void remove() {

    }

    @Override
    //TODO
    public boolean hasNext() {
        return false;
    }*/

/*    @Override
    //TODO
    public Iterator<Group> iterator() {
        return null;
    }*/

    public void createGroup(String name) {
         createGroup(name,0);

    }

    public int getCount() {
        return groupList.size();
    }

    public Group getGroup(int position) {
        return groupList.get(position);
    }

    public void setGroup(int position,Group group) {
        groupList.set(position, group);
    }


    private final ArrayList<ModelChangeListener> modelChangeListeners = new ArrayList<>();

    @SuppressWarnings("unused")
    public void addModelChangeListener(ModelChangeListener modelChangeListener) {
        modelChangeListeners.add(modelChangeListener);
    }

    private void  changeSignal() {
        for (ModelChangeListener listener:modelChangeListeners) {
            listener.onChange(this);
        }
    }

    public void createGroup(String name, int count) {
        ConcreteGroup group=new ConcreteGroup(name,count);
        groupList.add(group);
        changeSignal();
    }

    public void clear() {
        groupList.clear();
    }
}
