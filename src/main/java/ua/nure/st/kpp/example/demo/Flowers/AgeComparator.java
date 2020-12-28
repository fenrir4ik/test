package ua.nure.st.kpp.example.demo.Flowers;

import java.util.Comparator;

public class AgeComparator implements Comparator<Plant> {
    public int compare(Plant first, Plant second) {
        if (first.getAge() == second.getAge()) {
            return 0;
        }
        if (first.getAge() < second.getAge()) {
            return -1;
        }
        else {
            return 1;
        }
    }
}