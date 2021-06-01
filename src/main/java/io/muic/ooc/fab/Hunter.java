package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;

public class Hunter extends Actor {

    public void initialize(boolean randomAge, Field field, Location location) {
        super.initialize(randomAge, field, location);
    }

    public void act(List<Actor> newActors) {
        Location newLocation = moveToLocation();
        if (newLocation != null) {
            setLocation(newLocation);
        }
    }

    @Override
    protected Location moveToLocation() {
        Location newLocation = findPrey();
        if (newLocation == null) {
            // No food found - try to move to a free location.
            newLocation = field.freeAdjacentLocation(location);
        }
        return newLocation;
    }

    /**
     * Look for rabbits adjacent to the current location. Only the first live
     * rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location findPrey() {
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    return where;
                }
            }
            else if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    return where;
                }
            }
            else if (animal instanceof Tiger) {
                Tiger tiger = (Tiger) animal;
                if (tiger.isAlive()) {
                    tiger.setDead();
                    return where;
                }
            }
        }
        return null;
    }

}
