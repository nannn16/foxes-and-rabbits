package io.muic.ooc.fab.observer;

import io.muic.ooc.fab.Field;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    private List<Observer> observers = new ArrayList<>();

    public final void addObserver(Observer observer) {
        observers.add(observer);
    }

    public final void notifyAllObserver(int step, Field field) {
        for (Observer observer: observers) {
            observer.update(step, field);
        }
    }
}
