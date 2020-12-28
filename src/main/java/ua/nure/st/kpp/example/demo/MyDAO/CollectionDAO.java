package ua.nure.st.kpp.example.demo.MyDAO;

import ua.nure.st.kpp.example.demo.Flowers.Cactus;
import ua.nure.st.kpp.example.demo.Flowers.Plant;
import ua.nure.st.kpp.example.demo.Flowers.Rose;
import ua.nure.st.kpp.example.demo.Flowers.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*


public class CollectionDAO implements IDAO{

    private static ArrayList<Plant> plants = new ArrayList<Plant>();

    static
    {
        plants.add(new Rose("Красная роза", 20.0, "Песок", "Влажная почва", 27, 38, 7, 8, new String[]{"Цветы"}, "Red", false));
        plants.add(new Cactus("Пещаный кактус", 33.0, "Песок", "Рыхлая почва", 34, 25.0, 3, 6, new String[]{"Цветы"}, 0.8, "Yellow"));
        plants.add(new Tree("Ель простая", 10.0, "Чернозём", "Родючая почва", 15, 25, "Brown", 3, "Пушистая", "Тёмное зелёные"));
    }


    @Override
    public void Insert_Plant(Plant plant) {
        plants.add(plant);
    }

    @Override
    public ArrayList<Plant> Get_Plants() {
        return plants;
    }

    @Override
    public ArrayList<Plant> SelectByGroundName(String name) {
        ArrayList<Plant> result = new ArrayList<Plant>();
        for (Plant i : plants) {
            if (i.getGround_name().equals(name)) {
                result.add(i);
            }
        }
        if (result.size() == 0) {
            return null;
        } else {
            return result;
        }
    }

    @Override
    public void ChangeGround(String plant_name, String ground_name, String ground_desription) {
        for (Plant i : plants) {
            if (i.getName().equals(plant_name)) {
                i.setGround_name(ground_name);
                i.setGround_description(ground_desription);
            }
        }
    }

    @Override
    public void DeletePlantByName(String plant_name) {
        plants = (ArrayList<Plant>) plants.stream().filter(plant -> !plant.getName().equals(plant_name)).collect(Collectors.toList());
    }

    @Override
    public void DeletePlantByNameProcedure(String plant_name) {
    }
}


*/

