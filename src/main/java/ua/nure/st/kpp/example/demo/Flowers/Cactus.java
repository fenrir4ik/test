package ua.nure.st.kpp.example.demo.Flowers;

public class Cactus extends Blooming_Plant
{
    private double needles_len; // длинна иголок
    private String needles_colour; // цвет иголок

    public Cactus() {
        super();
    }

    public double getNeedles_len() {
        return needles_len;
    }

    public void setNeedles_len(double needles_len) {
        if (needles_len >= 0)
        {
            this.needles_len = needles_len;
        }
        else
        {
            throw new IllegalArgumentException("Bad input");
        }
    }

    public String getNeedles_colour() {
        return needles_colour;
    }

    public void setNeedles_colour(String needles_colour) {
        this.needles_colour = needles_colour;
    }


    public Cactus(long id, String name, double temperature, String ground_name, String ground_description, int age, double height, int blooming_start, int blooming_end, String[] blooming_type, double needles_len, String needles_colour)
    {
        super(id, "Кактус", "Иглы", name, temperature, ground_name, ground_description, age, height, blooming_start, blooming_end, blooming_type);
        this.needles_colour = needles_colour;
        this.needles_len = needles_len;
    }

    @Override
    public void get_Description()
    {
        String message = "";
        message+="Информация растения " + getName() + '\n';
        message+="Тип растения " + getPlant_type() + '\n';
        message+="Тип листьев " + getLeaf_type() + '\n';
        message+="Возраст растения: " + getAge() + '\n';
        message+="Высота растения: " + getHeight() + '\n';
        message+="Комфортная температура: " + getTemperature() + '\n';
        message+="Длинна иголок: " + needles_len + '\n';
        message+="Цвет иголок: " + needles_colour + '\n';
        System.out.print(message);
    }
}