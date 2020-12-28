
package ua.nure.st.kpp.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.nure.st.kpp.example.demo.MyDAO.*;
import ua.nure.st.kpp.example.demo.Flowers.Plant;
import ua.nure.st.kpp.example.demo.Observer.DAOObserver;
import ua.nure.st.kpp.example.demo.Observer.PlantObservable;
import ua.nure.st.kpp.example.demo.Proxy.Status;
import ua.nure.st.kpp.example.demo.StringPlantPair.PlantPair;
import ua.nure.st.kpp.example.demo.form.AddPlantForm;
import ua.nure.st.kpp.example.demo.form.DeletePlantForm;
import ua.nure.st.kpp.example.demo.form.EditPlantGroundForm;
import ua.nure.st.kpp.example.demo.form.GetPlantByGround;


@Controller
public class PlantsController {
	private MySQLDAO dao = new MySQLDAO();

	PlantsController()
	{
		dao.events.registerObserver(new DAOObserver());
	}


	@RequestMapping(value = { "/", "/plants" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String showAllPlants(Model model) {
		List<Plant> list = dao.Get_Plants();
		model.addAttribute("allPlants", list);
		return "plantsPage";
	}

	@RequestMapping(value = {"/log" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String showLog(Model model) {
		ArrayList<PlantPair> list = dao.events.Sumary();
		model.addAttribute("allPlants", list);
		return "logPage";
	}

	@RequestMapping(value = { "/addplant" }, method = RequestMethod.GET)
	public String showAddPlantView(Model model) {
		AddPlantForm plant = new AddPlantForm();
		model.addAttribute("addPlantForm", plant);
		return "addPlantPage";
	}

	@RequestMapping(value = { "/addplant" }, method = RequestMethod.POST)
	public String addPlant(Model model, AddPlantForm plant) {

		dao.Insert_Plant(new Plant(0, plant.getPlant_type(), plant.getLeaf_type(), plant.getName(), plant.getTemperature(), plant.getGround_name(), plant.getGround_description(), plant.getAge(), plant.getHeight()), Status.ADMIN);
		return "redirect:/plants";
	}

	@RequestMapping(value = { "/deleteplantbyname" }, method = RequestMethod.GET)
	public String showDeletePlantView(Model model) {

		DeletePlantForm deletePlantForm = new DeletePlantForm();
		model.addAttribute("deletePlantForm", deletePlantForm);

		return "deletePlant";
	}

	@RequestMapping(value = { "/deleteplantbyname" }, method = RequestMethod.POST)
	public String deletePlant(Model model, DeletePlantForm deletePlantForm) {

		dao.DeletePlantByName(deletePlantForm.getPlantName(), Status.ADMIN);

		return "redirect:/plants";
	}

	@RequestMapping(value = { "/editgroundbyname" }, method = RequestMethod.GET)
	public String showEditPlantGroundView(Model model) {

		EditPlantGroundForm editGroundForm = new EditPlantGroundForm();
		model.addAttribute("editPlantGroundForm", editGroundForm);
		return "editGround";
	}

	@RequestMapping(value = { "/editgroundbyname" }, method = RequestMethod.POST)
	public String EditPlantGround(Model model, EditPlantGroundForm editGroundForm) {

		dao.ChangeGround(editGroundForm.getPlantName(), editGroundForm.getGroundName(), editGroundForm.getGroundDescr(), Status.ADMIN);

		return "redirect:/plants";
	}

	@RequestMapping(value = { "/showplantbyground" }, method = RequestMethod.GET)
	public String showGetPlantByGroundView(Model model) {

		GetPlantByGround getPlantByGround = new GetPlantByGround();
		model.addAttribute("showPlantForm", getPlantByGround);

		return "plantsByGround";
	}


	@RequestMapping(value = { "/showplantbyground" }, method = RequestMethod.POST)
	public String showGetPlantByGroundView(Model model, GetPlantByGround getPlantByGround) {
		List<Plant> list = dao.SelectByGroundName(getPlantByGround.getGroundName());
		model.addAttribute("allPlants", list);
		return "plantsPage";
	}
}



