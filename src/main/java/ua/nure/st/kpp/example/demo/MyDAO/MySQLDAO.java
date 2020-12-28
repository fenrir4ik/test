package ua.nure.st.kpp.example.demo.MyDAO;

import org.jvnet.hk2.spring.bridge.api.SpringIntoHK2Bridge;
import ua.nure.st.kpp.example.demo.Flowers.Plant;
import ua.nure.st.kpp.example.demo.Observer.PlantObservable;
import ua.nure.st.kpp.example.demo.Proxy.Status;
import ua.nure.st.kpp.example.demo.Proxy.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ua.nure.st.kpp.example.demo.Proxy.Status.*;


public class MySQLDAO implements IDAO{
	public PlantObservable events;
	private static MySQLDAO instance = null;
	private static Connection con = null;
	private static String add_ground = "INSERT INTO Ground (ground_name, ground_description) select ?, ? WHERE NOT EXISTS (select id from ground where ground_name = ? and ground_description = ?)";
	private static String add_look = "INSERT INTO Look (leaf_type) select ? WHERE NOT EXISTS(select id from Look where leaf_type = ?)";
	private static String add_plant = "INSERT INTO Plant (look_id, ground_id, plant_name, temperature, plant_type, height, age) VALUES(?, ?, ?, ?, ?, ?, ?)";
	private static String get_look_id = "select id from look where leaf_type = ? LIMIT 1";
	private static String get_ground_id = "select id from ground where ground_name = ? and ground_description = ? LIMIT 1";
	private static String get_plant_by_type = "select plant.id, plant_type, leaf_type, plant_name, temperature, ground_name, ground_description, age, height from plant join look on plant.look_id = look.id join Ground on ground.id = plant.ground_id where ground_name = ?";
	private static String edit_plant_ground_by_plant_name = "update plant set ground_id = ? where plant_name = ?";
	private static String delete_plant_by_name = "DELETE FROM Plant WHERE plant_name = ?;";
	private static String get_all_plants = "select plant.id, plant_type, leaf_type, plant_name, temperature, ground_name, ground_description, age, height from plant join look on plant.look_id = look.id join Ground on ground.id = plant.ground_id";
	private static String get_plants_by_name = "select plant.id, plant_type, leaf_type, plant_name, temperature, ground_name, ground_description, age, height from plant join look on plant.look_id = look.id join Ground on ground.id = plant.ground_id where plant_name = ?";
	private static String restore_plant = "UPDATE PLANT set id = ?, look_id = ?, ground_id = ?, plant_name = ?, temperature = ?, plant_type = ?, height = ?, age = ? where id = ?";
	private static String reg_usr = "insert into user(login, password) select ?, ? where not exists(select id from user where login = ?)";
	private static String get_usr = "select id, login, password, fk_status from user where login = ? and password = ?";


	public MySQLDAO() {
		events = new PlantObservable();
		try
		{
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flowers?serverTimezone=UTC", "root", "12121212");
		}
		catch (Exception ex)
		{
			System.out.println("Connection faliure...");
			System.out.println(ex);
		}
	}

	public static MySQLDAO getInstance()
	{
		if (instance == null)
		{
			instance = new MySQLDAO();
		}
		return instance;
	}

	@Override
	public Plant Insert_Plant(Plant plant, Status st) {
		try {
			if (st == ADMIN) {
			PreparedStatement insert_ground = con.prepareStatement(add_ground);
			PreparedStatement insert_look = con.prepareStatement(add_look);
			PreparedStatement insert_plant = con.prepareStatement(add_plant, Statement.RETURN_GENERATED_KEYS);

			PreparedStatement get_look = con.prepareStatement(get_look_id);
			PreparedStatement get_ground = con.prepareStatement(get_ground_id);

			insert_ground.setString(1, plant.getGround_name());
			insert_ground.setString(2, plant.getGround_description());
			insert_ground.setString(3, plant.getGround_name());
			insert_ground.executeUpdate();

			insert_look.setString(1, plant.getLeaf_type());
			insert_look.setString(2, plant.getLeaf_type());
			insert_look.executeUpdate();

			get_look.setString(1, plant.getLeaf_type());
			ResultSet resultSet = get_look.executeQuery();
			resultSet.next();
			int look_id = resultSet.getInt("id");
			get_ground.setString(1, plant.getGround_name());
			resultSet = get_ground.executeQuery();
			resultSet.next();
			int ground_id = resultSet.getInt("id");

			insert_plant.setInt(1, look_id);
			insert_plant.setInt(2, ground_id);
			insert_plant.setString(3, plant.getName());
			insert_plant.setDouble(4, plant.getTemperature());
			insert_plant.setString(5, plant.getPlant_type());
			insert_plant.setDouble(6, plant.getHeight());
			insert_plant.setInt(7, plant.getAge());
			insert_plant.executeUpdate();
			try (ResultSet generatedKeys = insert_plant.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					plant.setId(generatedKeys.getLong(1));
				}
				else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
			ArrayList<Plant> plants = new ArrayList<Plant>();
			plants.add(plant);
			events.notifyObservers("Add", plants);
			}
			else
			{
				System.out.println("Недостаточно прав для соверешения операции");
			}
		} catch (Exception ex) {
			System.out.println("Query failed...");
			System.out.println(ex);
		}
		return plant;
	}

	void restore_plant(Plant plant) {
		try {
			PreparedStatement sql_restore = con.prepareStatement(restore_plant);
			PreparedStatement insert_look = con.prepareStatement(add_look);
			PreparedStatement insert_ground = con.prepareStatement(add_ground);
			PreparedStatement get_look = con.prepareStatement(get_look_id);
			PreparedStatement get_ground = con.prepareStatement(get_ground_id);

			insert_ground.setString(1, plant.getGround_name());
			insert_ground.setString(2, plant.getGround_description());
			insert_ground.setString(3, plant.getGround_name());
			insert_ground.setString(4, plant.getGround_description());
			insert_ground.executeUpdate();

			insert_look.setString(1, plant.getLeaf_type());
			insert_look.setString(2, plant.getLeaf_type());
			insert_look.executeUpdate();

			get_look.setString(1, plant.getLeaf_type());
			ResultSet resultSet = get_look.executeQuery();
			resultSet.next();
			int look_id = resultSet.getInt("id");
			get_ground.setString(1, plant.getGround_name());
			get_ground.setString(2, plant.getGround_description());
			resultSet = get_ground.executeQuery();
			resultSet.next();
			int ground_id = resultSet.getInt("id");

			sql_restore.setLong(1, plant.getId());
			sql_restore.setInt(2, look_id);
			sql_restore.setInt(3, ground_id);
			sql_restore.setString(4, plant.getName());
			sql_restore.setDouble(5, plant.getTemperature());
			sql_restore.setString(6, plant.getPlant_type());
			sql_restore.setDouble(7, plant.getHeight());
			sql_restore.setInt(8, plant.getAge());
			sql_restore.setLong(9, plant.getId());
			sql_restore.executeUpdate();
			ArrayList<Plant> plants = new ArrayList<Plant>();
			plants.add(plant);
			events.notifyObservers("Edit", plants);

		} catch (Exception ex) {
			System.out.println("Query failed...");
			System.out.println(ex);
		}
	}

	@Override
	public ArrayList<Plant> Get_Plants()
	{
		try {
			PreparedStatement get_plant = con.prepareStatement(get_all_plants);
			ResultSet resultSet = get_plant.executeQuery();
			ArrayList<Plant> result = new ArrayList<Plant>();
			while (resultSet.next()) {
				result.add(new Plant(resultSet.getInt("id"),
						resultSet.getString("plant_type"),
						resultSet.getString("leaf_type"),
						resultSet.getString("plant_name"),
						resultSet.getDouble("temperature"),
						resultSet.getString("ground_name"),
						resultSet.getString("ground_description"),
						resultSet.getInt("age"),
						resultSet.getDouble("height")));
			}
			return result;
		} catch (Exception ex) {
			System.out.println("Query failed...");
			System.out.println(ex);
			return null;
		}
	}

	@Override
	public ArrayList<Plant> Get_Plants_By_Name(String name)
	{
		try {
			PreparedStatement get_plant = con.prepareStatement(get_plants_by_name);
			get_plant.setString(1, name);
			ResultSet resultSet = get_plant.executeQuery();
			ArrayList<Plant> result = new ArrayList<Plant>();
			while (resultSet.next()) {
				result.add(new Plant(resultSet.getInt("plant.id"),
						resultSet.getString("plant_type"),
						resultSet.getString("leaf_type"),
						resultSet.getString("plant_name"),
						resultSet.getDouble("temperature"),
						resultSet.getString("ground_name"),
						resultSet.getString("ground_description"),
						resultSet.getInt("age"),
						resultSet.getDouble("height")));
			}
			return result;
		} catch (Exception ex) {
			System.out.println("Query failed...");
			System.out.println(ex);
			return null;
		}
	}

	@Override
	public ArrayList<Plant> SelectByGroundName(String name) {
		try {
			PreparedStatement get_plant = con.prepareStatement(get_plant_by_type);
			get_plant.setString(1, name);
			ResultSet resultSet = get_plant.executeQuery();
			ArrayList<Plant> result = new ArrayList<Plant>();
			while (resultSet.next()) {
				result.add(new Plant(resultSet.getInt("plant.id"),
						resultSet.getString("plant_type"),
						resultSet.getString("leaf_type"),
						resultSet.getString("plant_name"),
						resultSet.getDouble("temperature"),
						resultSet.getString("ground_name"),
						resultSet.getString("ground_description"),
						resultSet.getInt("age"),
						resultSet.getDouble("height")));
			}
			return result;
		} catch (Exception ex) {
			System.out.println("Query failed...");
			System.out.println(ex);
			return null;
		}
	}

	@Override
	public void ChangeGround(String plant_name, String ground_name, String ground_desription, Status st) {
		try {
			if (st == ADMIN) {
			PreparedStatement edit_ground = con.prepareStatement(edit_plant_ground_by_plant_name);
			ArrayList<Plant> plants = Get_Plants_By_Name(plant_name);
			PreparedStatement insert_ground = con.prepareStatement(add_ground);
			PreparedStatement get_ground = con.prepareStatement(get_ground_id);

			insert_ground.setString(1, ground_name);
			insert_ground.setString(2, ground_desription);
			insert_ground.setString(3, ground_name);
			insert_ground.setString(4, ground_desription);
			insert_ground.executeUpdate();

			get_ground.setString(1, ground_name);
			get_ground.setString(2, ground_desription);
			ResultSet resultSet = get_ground.executeQuery();
			resultSet.next();
			int ground_id = resultSet.getInt("id");

			edit_ground.setInt(1, ground_id);
			edit_ground.setString(2, plant_name);
			edit_ground.executeUpdate();
			events.notifyObservers("Edit", plants);
			}
			else
			{
				System.out.println("Недостаточно прав для соверешения операции");
			}
		} catch (Exception ex) {
			System.out.println("Query failed...");
			System.out.println(ex);
		}
	}

	@Override
	public void DeletePlantByName(String plant_name, Status st) {
		try {
			if (st == ADMIN) {
				ArrayList<Plant> plants = Get_Plants_By_Name(plant_name);
				PreparedStatement delete_plant = con.prepareStatement(delete_plant_by_name);
				delete_plant.setString(1, plant_name);
				delete_plant.executeUpdate();
				events.notifyObservers("Delete", plants);
			}
			else
			{
				System.out.println("Недостаточно прав для соверешения операции");
			}
		} catch (Exception ex) {
			System.out.println("Query failed...");
			System.out.println(ex);
		}
	}

	public void DeletePlantByNameProcedure(String plant_name) {
		try
		{
			ArrayList<Plant> plants = Get_Plants_By_Name(plant_name);
			String query = "{CALL del_plants(?)}";
			CallableStatement stmt = con.prepareCall(query);
			stmt.setString(1, plant_name);
			stmt.executeQuery();
			events.notifyObservers("Delete", plants);
		}
		catch (SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	public void register_user(User usr)
	{
		try {
			PreparedStatement query = con.prepareStatement(reg_usr);
			query.setString(1, usr.getLogin());
			query.setString(2, usr.getPass());
			query.setString(3, usr.getLogin());
			query.executeUpdate();

		} catch (Exception ex) {
			System.out.println("Query failed...");
			System.out.println(ex);
		}
	}

	public Status login_user(User usr)
	{
		try {
			PreparedStatement query = con.prepareStatement(get_usr);
			query.setString(1, usr.getLogin());
			query.setString(2, usr.getPass());

			ResultSet resultSet = query.executeQuery();
			int counter = 0;
			if (resultSet.next()) {
				//select id, login, password, fk_status
				counter++;
				if (resultSet.getInt("fk_status") == 1){
					return USER;
				}
				else {
					return ADMIN;
				}
			}
			else
			{
				return None;
			}
		} catch (Exception ex) {
			System.out.println("Query failed...");
			System.out.println(ex);
			return None;
		}
	}
}
