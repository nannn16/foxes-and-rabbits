package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Tiger extends Animal {

    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 20;
    // The age to which a tiger can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a tiger breeding.
    private static final double BREEDING_PROBABILITY = 0.03;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;
    // The food value of a single fox. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int TIGER_FOOD_VALUE = 20;
    // Random generator
    private static final Random RANDOM = new Random();

    public void initialize(boolean randomAge, Field field, Location location) {
        super.initialize(randomAge, field, location);
        this.foodLevel = RANDOM.nextInt(9+15);
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
     * This is what the tiger does most of the time: it hunts for foxes and rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     * @param newTigers A list to return newly born foxes.
     */
    @Override
    public void act(List<Actor> newTigers) {
        incrementHunger();
        super.act(newTigers);
    }

    /**
     * Make this tiger more hungry. This could result in the tiger's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    private Location findPrey() {
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object creature = field.getObjectAt(where);
            if (creature instanceof Fox || creature instanceof Rabbit) {
                Animal animal = (Animal) creature;
                if (animal.isAlive()) {
                    animal.setDead();
                    foodLevel = animal.getFoodLevel();
                    return where;
                }
            }
        }
        return null;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    @Override
    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    @Override
    protected int getBreedingAge() {
        return BREEDING_AGE;
    }

    @Override
    protected int getFoodLevel() { return TIGER_FOOD_VALUE; }
}
