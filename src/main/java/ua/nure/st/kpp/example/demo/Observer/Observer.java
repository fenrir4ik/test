package ua.nure.st.kpp.example.demo.Observer;
import ua.nure.st.kpp.example.demo.Flowers.Plant;
import ua.nure.st.kpp.example.demo.StringPlantPair.PlantPair;

import java.util.ArrayList;

public interface Observer {
    void update(String eventType, ArrayList<Plant> plant);
    ArrayList<PlantPair> getLog();
}