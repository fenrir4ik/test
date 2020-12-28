package ua.nure.st.kpp.example.demo.Flowers;

public class Rose extends Blooming_Plant
{
    private String petal_colour; // цвет лепестков
    private boolean spikes_availibe;

    public Rose() {
        super();
    }

    public String getPetal_colour() {
        return petal_colour;
    }

    public void setPetal_colour(String petal_colour) {
        this.petal_colour = petal_colour;
    }

    public boolean isSpikes_availibe() {
        return spikes_availibe;
    }

    public void setSpikes_availibe(boolean spikes_availibe) {
        this.spikes_availibe = spikes_availibe;
    }

    public Rose(long id, String name, double temperature, String ground_name, String ground_description, int age, double height, int blooming_start, int blooming_end, String[] blooming_type, String petal_colour, boolean spikes_availible)
    {
        super(id, "Роза", "Лепестки", name, temperature, ground_name, ground_description, age, height, blooming_start, blooming_end, blooming_type);
        this.petal_colour = petal_colour;
        this.spikes_availibe = spikes_availible;
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
        message+="Наличие шипов: " + spikes_availibe + '\n';
        message+="Цвет лепестков: " + petal_colour + '\n';
        System.out.print(message);
    }
}
