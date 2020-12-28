package ua.nure.st.kpp.example.demo.Flowers;

import ua.nure.st.kpp.example.demo.Memento.Memento;
import ua.nure.st.kpp.example.demo.MyDAO.DAOFactory;

public class Plant implements Comparable<Plant>
{

    private long id;
    private int age;
    private double height;
    private String name;
    private double temperature;
    private String ground_name;
    private String ground_description;
    private String plant_type;
    private String leaf_type;

    public static class Builder {
        private long id;
        private String name;
        private int age = 0;
        private double height = 0.0;
        private double temperature = 0.0;
        private String ground_name = "";
        private String ground_description = "";
        private String plant_type = "";
        private String leaf_type = "";

        public Builder(String name)
        {
            this.name = name;
        }

        public Builder buildInt(int id)
        {
            this.id = id;
            return this;
        }

        public Builder buildAge(int age){
            this.age = age;
            return this;
        }

        public Builder buildHeight(double height){
            this.height = height;
            return this;
        }

        public Builder buildTemperature(double temperature){
            this.temperature = temperature;
            return this;
        }

        public Builder buildGroundName(String ground_name){
            this.ground_name = ground_name;
            return this;
        }

        public Builder buildGroundDescription(String ground_description){
            this.ground_description = ground_description;
            return this;
        }

        public Builder buildPlantType(String plant_type){
            this.plant_type = plant_type;
            return this;
        }

        public Builder buildLeafType(String leaf_type){
            this.leaf_type = leaf_type;
            return this;
        }

        public Plant build() {
            return new Plant(this);
        }
    }

    public Plant() {
        // all null
    }

    public Plant(Plant temp)
    {
        copy(temp);
    }

    public Plant(Builder builder)
    {
        this.id = builder.id;
        this.age = builder.age;
        this.height = builder.height;
        this.name = builder.name;
        this.temperature = builder.temperature;
        this.ground_name = builder.ground_name;
        this.ground_description = builder.ground_description;
        this.plant_type = builder.plant_type;
        this.leaf_type = builder.leaf_type;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Memento saveState() {
        return new Memento(this);
    }

    public void restoreState(Memento memento) {
        copy(memento.getState());
    }

    public void copy(Plant temp)
    {
        this.id = temp.id;
        this.age = temp.age;
        this.height = temp.height;
        this.name = temp.name;
        this.temperature = temp.temperature;
        this.ground_name = temp.ground_name;
        this.ground_description = temp.ground_description;
        this.plant_type = temp.plant_type;
        this.leaf_type = temp.leaf_type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 0)
        {
            this.age = age;
        }
        else
        {
            throw new IllegalArgumentException("Bad input");
        }
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height >= 0)
        {
            this.height = height;
        }
        else
        {
            throw new IllegalArgumentException("Bad input");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        if (temperature > -40 && temperature < 80)
        {
            this.temperature = temperature;
        }
        else
        {
            throw new IllegalArgumentException("Bad input");
        }
    }

    public String getGround_name() {
        return ground_name;
    }

    public void setGround_name(String ground_name) {
        this.ground_name = ground_name;
    }

    public String getGround_description() {
        return ground_description;
    }

    public void setGround_description(String ground_description) {
        this.ground_description = ground_description;
    }

    public String getPlant_type() {
        return plant_type;
    }

    public void setPlant_type(String plant_type) {
        this.plant_type = plant_type;
    }

    public String getLeaf_type() {
        return leaf_type;
    }

    public void setLeaf_type(String leaf_type) {
        this.leaf_type = leaf_type;
    }

    public void get_Description()
    {
        String message = "";
        message+="Информация растения " + name + '\n';
        message+="Тип растения " + plant_type + '\n';
        message+="Тип листьев " + leaf_type + '\n';
        message+="Возраст растения: " + age + '\n';
        message+="Высота растения: " + height + '\n';
        message+="Комфоротная температура: " + temperature + '\n';
        message+="Почва: " + ground_name + '\n';
        System.out.print(message);
    }

    @Override
    public String toString()
    {
        return "Name: " + name + ", days: " + age + ", height: " + height + ", temperature: " + temperature + ", ground_name: " + ground_name + ", ground_name: " + ground_description;
    }

    public Plant(long id, String plant_type, String leaf_type, String name, double temperature, String ground_name, String ground_description, int age, double height)
    {
        this.id = id;
        this.plant_type = plant_type;
        this.leaf_type = leaf_type;
        this.name = name;
        this.temperature = temperature;
        this.ground_description = ground_description;
        this.ground_name = ground_name;
        setAge(age);
        setHeight(height);
        setTemperature(temperature);
    }

    @Override
    public int compareTo(Plant other)
    {
        if (this.height == other.height)
        {
            return 0;
        }
        else if (this.height < other.height)
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }
}