package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Tiger extends Animal{

    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 20;
    // The age to which a tiger can live.
    private static final int MAX_AGE = 70;
    // The likelihood of a tiger breeding.
    private static final double BREEDING_PROBABILITY = 0.03;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 9;
    // The food value of a single fox. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int FOX_FOOD_VALUE = 20;
    // A shared random number generator to control breeding.
    private static final Random RANDOM = new Random();

    // The fox's food level, which is increased by eating foxes and rabbits.
    private int foodLevel;

    public Tiger(boolean randomAge, Field field, Location location) {
        age = 0;
        setAlive(true);
        this.field = field;
        setLocation(location);
        if (randomAge) {
            age = RANDOM.nextInt(MAX_AGE);
        }
        foodLevel = RANDOM.nextInt(RABBIT_FOOD_VALUE) + RANDOM.nextInt(FOX_FOOD_VALUE);
    }

    /**
     * This is what the tiger does most of the time: it hunts for foxes and rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     *  @param field The field currently occupied.
     * @param newTigers A list to return newly born foxes.
     */
    @Override
    public void act(List<Animal> newTigers) {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            giveBirth(newTigers);
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if (newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = field.freeAdjacentLocation(location);
            }
            // See if it was possible to move.
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
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

    /**
     * Look for rabbits adjacent to the current location. Only the first live
     * rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood() {
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
            else if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    foodLevel = FOX_FOOD_VALUE;
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
    protected Animal createYoung(boolean randomAge, Field field, Location location) {
        return new Tiger(randomAge, field, location);
    }
}
