package ua.nure.st.kpp.example.demo.MyDAO;
import ua.nure.st.kpp.example.demo.Flowers.Plant;
import ua.nure.st.kpp.example.demo.Proxy.Status;

import java.util.ArrayList;


public interface IDAO {
	Plant Insert_Plant(Plant plant, Status st);
	ArrayList<Plant> Get_Plants();
	ArrayList<Plant> SelectByGroundName(String name);
	void ChangeGround(String plant_name, String ground_name, String ground_desription, Status st);
	void DeletePlantByName(String plant_name, Status st);
	ArrayList<Plant> Get_Plants_By_Name(String name);
}
