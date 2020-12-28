package ua.nure.st.kpp.example.demo.Memento;

import ua.nure.st.kpp.example.demo.Flowers.Plant;

public class Memento {
    private final Plant state;

    public Memento(Plant state) {
        this.state = new Plant(state);
    }

    public Plant getState() {
        return state;
    }
}