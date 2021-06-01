package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

public abstract class Actor {

    // Whether the actor is alive or not.
    private boolean alive;

    // The fox's position.
    protected Location location;
    // The field occupied.
    protected Field field;

    private static final Random RANDOM = new Random();

    public void initialize(boolean randomAge, Field field, Location location) {
        this.field = field;
        setAlive(true);
        setLocation(location);
    }

    public abstract void act(List<Actor> newActor);

    /**
     * Check whether the animal is alive or not.
     *
     * @return true if the animal is still alive.
     */
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    protected abstract Location moveToLocation();

    /**
     * Place the rabbit at the new location in the given field.
     *
     * @param newLocation The rabbit's new location.
     */
    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
}
