package ua.nure.st.kpp.example.demo.Flowers;

public class Coniferous extends Plant // хвойные
{
    private String cones_color; // цвет шишек
    private double cones_len; // длина шишек

    public Coniferous() {
        super();
    }

    public String getCones_color() {
        return cones_color;
    }

    public void setCones_color(String cones_color) {
        this.cones_color = cones_color;
    }

    public double getCones_len() {
        return cones_len;
    }

    public void setCones_len(double cones_len) {
        if (cones_len >= 0)
        {
            this.cones_len = cones_len;
        }
        else
        {
            throw new IllegalArgumentException("Bad input");
        }
    }

    public Coniferous(long id, String plant_type, String leaf_type, String name, double temperature, String ground_name, String ground_description, int age, double height, String cones_color, double cones_len)
    {
        super(id, plant_type, leaf_type, name, temperature, ground_name, ground_description, age, height);
        this.cones_len = cones_len;
        this.cones_color = cones_color;
    }

    public void get_coniferous_info()
    {
        String message = "";
        message+= "Длинна шишек " + cones_len + '\n';
        message+= "Цвет шишек " + cones_color + '\n';
        System.out.println(message);
    }
}

