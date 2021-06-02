package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;

public class Hunter extends Actor {

    /**
     * This is what the hunter does most of the time - it runs around and hunts an animal.
     */
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
            // No animal found - try to move to a free location.
            newLocation = field.freeAdjacentLocation(location);
        }
        return newLocation;
    }

    /**
     * Look for rabbits, foxes and tigers adjacent to the current location. Only the first live
     * rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location findPrey() {
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object creature = field.getObjectAt(where);
            if (creature instanceof Animal) {
                Animal animal = (Animal) creature;
                if(animal.isAlive()) {
                    animal.setDead();
                    return where;
                }
            }
        }
        return null;
    }

}
