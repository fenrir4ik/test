package ua.nure.st.kpp.example.demo.form;

public class EditPlantGroundForm {
    private String groundName;
    private String plantName;
    private String groundDescr;

    public EditPlantGroundForm() {

    }

    public EditPlantGroundForm(String groundName, String plantName, String groundDescr) {

        this.groundName = groundName;
        this.plantName = plantName;
        this.groundDescr = groundDescr;
    }

    public String getGroundDescr() {
        return groundDescr;
    }

    public void setGroundDescr(String groundDescr) {
        this.groundDescr = groundDescr;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }


    public String getGroundName() {

        return groundName;
    }

    public void setGroundName(String groundName) {

        this.groundName = groundName;
    }
}
