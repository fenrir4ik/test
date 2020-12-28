package ua.nure.st.kpp.example.demo.Observer;

import ua.nure.st.kpp.example.demo.Flowers.Plant;

import java.util.ArrayList;

public interface Observable {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(String eventType, ArrayList<Plant> plant);
}
