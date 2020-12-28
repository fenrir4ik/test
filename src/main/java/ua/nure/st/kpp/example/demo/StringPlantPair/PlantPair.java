package ua.nure.st.kpp.example.demo.StringPlantPair;

import ua.nure.st.kpp.example.demo.Flowers.Plant;

public class PlantPair {
    public String getEvent() {
        return event;
    }

    public String event;
    public Plant plant;

    public PlantPair(String event, Plant plant) {
        this.event = event;
        this.plant = plant;
    }
}
