package ua.nure.st.kpp.example.demo.Parse;

public class Plant {
    public String plant_name;
    public double temperature;
    public String plant_type;
    public double height;
    public int age;
    public Look look;
    public Ground ground;

    public Plant(String plant_name, double temperature, String plant_type, double height, int age, Look look, Ground ground) {
        this.plant_name = plant_name;
        this.temperature = temperature;
        this.plant_type = plant_type;
        this.height = height;
        this.age = age;
        this.look = look;
        this.ground = ground;
    }

    public String getPlant_name() {
        return plant_name;
    }

    public void setPlant_name(String plant_name) {
        this.plant_name = plant_name;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getPlant_type() {
        return plant_type;
    }

    public void setPlant_type(String plant_type) {
        this.plant_type = plant_type;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
