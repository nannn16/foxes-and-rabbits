package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

public abstract class Actor {

    // Whether the actor is alive or not.
    private boolean alive;

    // The actor's position.
    protected Location location;
    // The field occupied.
    protected Field field;

    public void initialize(boolean randomAge, Field field, Location location) {
        this.field = field;
        setAlive(true);
        setLocation(location);
    }

    public abstract void act(List<Actor> newActor);

    /**
     * Check whether the actor is alive or not.
     *
     * @return true if the actor is still alive.
     */
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    protected abstract Location moveToLocation();

    /**
     * Place the actor at the new location in the given field.
     *
     * @param newLocation The actor's new location.
     */
    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
}
