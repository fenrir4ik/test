package ua.nure.st.kpp.example.demo.Observer;

import ua.nure.st.kpp.example.demo.Flowers.Plant;
import ua.nure.st.kpp.example.demo.StringPlantPair.PlantPair;

import java.util.ArrayList;

public class DAOObserver implements Observer {
    private ArrayList<PlantPair> log = new ArrayList<PlantPair>();

    @Override
    public ArrayList<PlantPair> getLog() {
        return log;
    }

    @Override
    public void update(String eventType, ArrayList<Plant> plant) {
        for (Plant p: plant) {
            System.out.println(eventType + " | " + p);
            log.add(new PlantPair(eventType, p));
        }
    }
}