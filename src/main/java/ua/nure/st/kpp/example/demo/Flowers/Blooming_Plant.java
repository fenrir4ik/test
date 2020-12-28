package ua.nure.st.kpp.example.demo.Flowers;

public class Blooming_Plant extends Plant  // цветущие
{
    private int blooming_start; // месяц начала цветения 1-12
    private int blooming_end; // месяц конца цветения 1-12
    private String[] blooming_type; // как цветёт появление цветов, ягод

    public Blooming_Plant() {
        super();
    }

    public int getBlooming_start() {
        return blooming_start;
    }

    public void setBlooming_start(int blooming_start) {
        if (blooming_start > 0 && blooming_start <= 12)
        {
            this.blooming_start = blooming_start;
        }
        else
        {
            throw new IllegalArgumentException("Bad input");
        }
    }

    public int getBlooming_end() {
        return blooming_end;
    }

    public void setBlooming_end(int blooming_end) {
        if (blooming_end > 0 && blooming_end <= 12)
        {
            this.blooming_end = blooming_end;
        }
        else
        {
            throw new IllegalArgumentException("Bad input");
        }
    }

    public String[] getBlooming_type() {
        return blooming_type;
    }

    public void setBlooming_type(String[] blooming_type) {
        this.blooming_type = blooming_type;
    }


    public Blooming_Plant(long id, String plant_type, String leaf_type, String name, double temperature, String ground_name, String ground_description, int age, double height, int blooming_start, int blooming_end, String[] blooming_type)
    {
        super(id, plant_type, leaf_type, name, temperature, ground_name, ground_description, age, height);
        this.blooming_start = blooming_start;
        this.blooming_end = blooming_end;
        this.blooming_type = blooming_type;
    }

    public void get_blooming_info()
    {
        String message = "Признаки цветения " + getName() + ": ";
        for (int i = 0; i < blooming_type.length; i++) {
            if (i != blooming_type.length - 1) {
                message += blooming_type[i] + " + ";
            }
            else
            {
                message += blooming_type[i] + ".";
            }
        }
        System.out.println(message);
    }
}