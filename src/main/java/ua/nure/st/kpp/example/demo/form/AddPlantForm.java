package ua.nure.st.kpp.example.demo.form;

public class AddPlantForm {

	private int age; // возраст растения в днях или сколько дней оно уже засохло
	private double height; // высота в см
	private String name; // название растения
	private double temperature; // температура окружающей среды требуемая для роста
	private String ground_name;
	private String ground_description;
	private String plant_type;
	private String leaf_type;

	public AddPlantForm() {
		// all null
	}

	public AddPlantForm(String plant_type, String leaf_type, String name, double temperature, String ground_name, String ground_description, int age, double height)
	{
		this.plant_type = plant_type;
		this.leaf_type = leaf_type;
		this.name = name;
		this.temperature = temperature;
		this.ground_description = ground_description;
		this.ground_name = ground_name;
		setAge(age);
		setHeight(height);
		setTemperature(temperature);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if (age >= 0)
		{
			this.age = age;
		}
		else
		{
			throw new IllegalArgumentException("Bad input");
		}
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		if (height >= 0)
		{
			this.height = height;
		}
		else
		{
			throw new IllegalArgumentException("Bad input");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		if (temperature > -40 && temperature < 80)
		{
			this.temperature = temperature;
		}
		else
		{
			throw new IllegalArgumentException("Bad input");
		}
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

	public String getPlant_type() {
		return plant_type;
	}

	public void setPlant_type(String plant_type) {
		this.plant_type = plant_type;
	}

	public String getLeaf_type() {
		return leaf_type;
	}

	public void setLeaf_type(String leaf_type) {
		this.leaf_type = leaf_type;
	}
}
