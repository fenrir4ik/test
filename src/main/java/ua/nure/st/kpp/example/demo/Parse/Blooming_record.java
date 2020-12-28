package ua.nure.st.kpp.example.demo.Parse;

public class Blooming_record {
    public Blooming_type type;
    public Plant plant;

    public Blooming_type getType() {
        return type;
    }

    public void setType(Blooming_type type) {
        this.type = type;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Blooming_record(Blooming_type type, Plant plant) {
        this.type = type;
        this.plant = plant;
    }
}
