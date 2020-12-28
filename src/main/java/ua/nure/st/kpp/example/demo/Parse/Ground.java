package ua.nure.st.kpp.example.demo.Parse;

public class Ground {
    public String ground_name;
    public String ground_description;

    public Ground(String ground_name, String ground_description) {
        this.ground_name = ground_name;
        this.ground_description = ground_description;
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
}
