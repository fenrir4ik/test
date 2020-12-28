package ua.nure.st.kpp.example.demo.Flowers;

public class Tree extends Coniferous // ель
{
    private String needles_density; // густота хвои
    private String needles_colour; // цвет хвои

    public Tree() {
        super();
    }

    public String getNeedles_density() {
        return needles_density;
    }

    public void setNeedles_density(String needles_density) {
        this.needles_density = needles_density;
    }

    public String getNeedles_colour() {
        return needles_colour;
    }

    public void setNeedles_colour(String needles_colour) {
        this.needles_colour = needles_colour;
    }

    public Tree(long id, String name, double temperature, String ground_name, String ground_description, int age, double height, String cones_color, double cones_len, String needles_density, String needles_colour)
    {
        super(id, "Ель", "Хвоя", name, temperature, ground_name, ground_description, age, height, cones_color, cones_len);
        this.needles_density = needles_density;
        this.needles_colour = needles_colour;
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
        message+="Густота хвои: " + needles_density + '\n';
        message+="Цвет хвои: " + needles_colour + '\n';
        System.out.print(message);
    }
}