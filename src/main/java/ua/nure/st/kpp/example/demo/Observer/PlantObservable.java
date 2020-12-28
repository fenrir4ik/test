package ua.nure.st.kpp.example.demo.Observer;

import ua.nure.st.kpp.example.demo.Flowers.Plant;
import ua.nure.st.kpp.example.demo.StringPlantPair.PlantPair;

import java.util.*;

public class PlantObservable implements Observable{
    private ArrayList<Observer> observers = new ArrayList<Observer>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String eventType, ArrayList<Plant> plant) {
        for (Observer user : observers) {
            user.update(eventType, plant);
        }
    }

    public ArrayList<PlantPair> Sumary() {
        ArrayList<PlantPair> sumary = new ArrayList<PlantPair>();
        for (Observer ob : observers) {
            ArrayList<PlantPair> log = ob.getLog();

            for (int i = log.size()-1; i >= 0; i--)
            {
                sumary.add(log.get(i));
            }
        }
        return sumary;
    }
}
