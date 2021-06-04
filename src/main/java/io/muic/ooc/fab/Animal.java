package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class Animal extends Actor {
    // Whether the animal is alive or not.
    private boolean alive;

    // Individual characteristics (instance fields).
    // The animal's age.
    protected int age;
    protected int foodLevel;

    private static final Random RANDOM = new Random();

    public void initialize(boolean randomAge, Field field, Location location) {
        super.initialize(randomAge, field, location);
        this.age = 0;
        if (randomAge) {
            this.age = RANDOM.nextInt(getMaxAge());
        }
    }

    /**
     * This is what the animal does most of the time - it runs around or may be hunts for preys. Sometimes
     * it will breed or die of old age.
     *
     * @param newActors A list to return newly born animals.
     */
    public void act(List<Actor> newActors) {
        incrementAge();
        if (isAlive()) {
            giveBirth(newActors);
            // Try to move into a free location.
            Location newLocation = moveToLocation();
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }

    public abstract int getMaxAge();

    /**
     * Increase the age. This could result in the animal's death.
     */
    protected void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }


    /**
     * Indicate that the animal is no longer alive. It is removed from the field.
     */
    protected void setDead() {
        setAlive(false);
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Generate a number representing the number of births, if it can breed.
     *
     * @return The number of births (may be zero).
     */
    protected int breed() {
        int births = 0;
        if (canBreed() && RANDOM.nextDouble() <= getBreedingProbability()) {
            births = RANDOM.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    protected abstract double getBreedingProbability();

    protected abstract int getMaxLitterSize();

    protected abstract int getFoodLevel();

    /**
     * A animal can breed if it has reached the breeding age.
     *
     * @return true if the rabbit can breed, false otherwise.
     */
    private boolean canBreed() {
        return age >= getBreedingAge();
    }

    protected abstract int getBreedingAge();

    private Actor createYoung(boolean randomAge, Field field, Location location) {
        return ActorFactory.createActor(getClass(), field, location);
    }

    /**
     * Check whether or not this animal is to give birth at this step. New
     * births will be made into free adjacent locations.
     *
     * @param newActors A list to return newly born animals.
     */
    protected void giveBirth(List<Actor> newActors) {
        // New animals are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = field.getFreeAdjacentLocations(location);
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Actor young = createYoung(false, field, loc);
            newActors.add(young);
        }
    }
}
