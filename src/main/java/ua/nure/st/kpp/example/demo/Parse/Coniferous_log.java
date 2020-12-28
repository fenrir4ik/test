package ua.nure.st.kpp.example.demo.Parse;

public class Coniferous_log {
    public String cones_color;
    public Double cones_len;
    public Plant plant;

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Coniferous_log(String cones_color, Double cones_len, Plant plant) {
        this.cones_color = cones_color;
        this.cones_len = cones_len;
        this.plant = plant;
    }

    public String getCones_color() {
        return cones_color;
    }

    public void setCones_color(String cones_color) {
        this.cones_color = cones_color;
    }

    public Double getCones_len() {
        return cones_len;
    }

    public void setCones_len(Double cones_len) {
        this.cones_len = cones_len;
    }
}
