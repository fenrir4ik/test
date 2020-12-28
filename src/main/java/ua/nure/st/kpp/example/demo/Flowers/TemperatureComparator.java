package ua.nure.st.kpp.example.demo.Flowers;

import java.util.Comparator;

public class TemperatureComparator implements Comparator<Plant> {
    public int compare(Plant first, Plant second) {
        if (first.getTemperature() == second.getTemperature()) {
            return 0;
        }
        if (first.getTemperature() < second.getTemperature()) {
            return -1;
        }
        else {
            return 1;
        }
    }
}