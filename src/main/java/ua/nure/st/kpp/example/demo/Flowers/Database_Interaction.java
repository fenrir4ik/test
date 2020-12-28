package ua.nure.st.kpp.example.demo.Flowers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Database_Interaction {
    public static void Insert_Blooming_Plant(Blooming_Plant plant) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flowers", "root", "12121212");
            PreparedStatement add_ground = con.prepareStatement("INSERT INTO Ground (ground_name, ground_description) VALUES(?, ?)");
            PreparedStatement add_look = con.prepareStatement("INSERT INTO Look (leaf_type) VALUES(?)");
            PreparedStatement add_plant = con.prepareStatement("INSERT INTO Plant (look_id, ground_id, plant_name, temperature, plant_type, height, age) VALUES(?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement add_blooming_type = con.prepareStatement("INSERT INTO Blooming_type (blooming_name) VALUES(?)");
            PreparedStatement add_blooming_record = con.prepareStatement("INSERT INTO Blooming_record (blooming_id, plant_id) VALUES(?, ?)");

            PreparedStatement get_look_id = con.prepareStatement("SELECT * FROM Look order by id DESC LIMIT 1");
            PreparedStatement get_ground_id = con.prepareStatement("SELECT * FROM Ground order by id DESC LIMIT 1");
            PreparedStatement get_plant_id = con.prepareStatement("SELECT * FROM Plant order by id DESC LIMIT 1");
            PreparedStatement get_blooming_id = con.prepareStatement("SELECT * FROM Blooming_type order by id DESC LIMIT 1");

            add_ground.setString(1, plant.getGround_name());
            add_ground.setString(2, plant.getGround_description());
            add_ground.executeUpdate();

            add_look.setString(1, plant.getLeaf_type());
            add_look.executeUpdate();

            ResultSet resultSet = get_look_id.executeQuery();
            resultSet.next();
            int look_id = resultSet.getInt("id");
            resultSet = get_ground_id.executeQuery();
            resultSet.next();
            int ground_id = resultSet.getInt("id");

            add_plant.setInt(1, look_id);
            add_plant.setInt(2, ground_id);
            add_plant.setString(3, plant.getName());
            add_plant.setDouble(4, plant.getTemperature());
            add_plant.setString(5, plant.getPlant_type());
            add_plant.setDouble(6, plant.getHeight());
            add_plant.setInt(7, plant.getAge());
            add_plant.executeUpdate();

            for (String item : plant.getBlooming_type()) {
                add_blooming_type.setString(1, item);
                add_blooming_type.executeUpdate();
                resultSet = get_blooming_id.executeQuery();
                resultSet.next();
                int blooming_id = resultSet.getInt("id");
                resultSet = get_plant_id.executeQuery();
                resultSet.next();
                int plant_id = resultSet.getInt("id");
                add_blooming_record.setInt(1, blooming_id);
                add_blooming_record.setInt(2, plant_id);
                add_blooming_record.executeUpdate();
            }
            con.close();
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }

    }

    public static void Insert_Coniferous_Plant(Coniferous plant) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flowers", "root", "12121212");
            PreparedStatement add_ground = con.prepareStatement("INSERT INTO Ground (ground_name, ground_description) VALUES(?, ?)");
            PreparedStatement add_look = con.prepareStatement("INSERT INTO Look (leaf_type) VALUES(?)");
            PreparedStatement add_plant = con.prepareStatement("INSERT INTO Plant (look_id, ground_id, plant_name, temperature, plant_type, height, age) VALUES(?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement add_coniferous_log = con.prepareStatement("INSERT INTO Coniferous_log (plant_id, cones_color, cones_len) VALUES(?, ?, ?)");

            PreparedStatement get_look_id = con.prepareStatement("SELECT * FROM Look order by id DESC LIMIT 1");
            PreparedStatement get_ground_id = con.prepareStatement("SELECT * FROM Ground order by id DESC LIMIT 1");
            PreparedStatement get_plant_id = con.prepareStatement("SELECT * FROM Plant order by id DESC LIMIT 1");

            add_ground.setString(1, plant.getGround_name());
            add_ground.setString(2, plant.getGround_description());
            add_ground.executeUpdate();

            add_look.setString(1, plant.getLeaf_type());
            add_look.executeUpdate();

            ResultSet resultSet = get_look_id.executeQuery();
            resultSet.next();
            int look_id = resultSet.getInt("id");
            resultSet = get_ground_id.executeQuery();
            resultSet.next();
            int ground_id = resultSet.getInt("id");

            add_plant.setInt(1, look_id);
            add_plant.setInt(2, ground_id);
            add_plant.setString(3, plant.getName());
            add_plant.setDouble(4, plant.getTemperature());
            add_plant.setString(5, plant.getPlant_type());
            add_plant.setDouble(6, plant.getHeight());
            add_plant.setInt(7, plant.getAge());
            add_plant.executeUpdate();

            resultSet = get_plant_id.executeQuery();
            resultSet.next();
            int plant_id = resultSet.getInt("id");
            add_coniferous_log.setInt(1, plant_id);
            add_coniferous_log.setString(2, plant.getCones_color());
            add_coniferous_log.setDouble(3, plant.getCones_len());
            add_coniferous_log.executeUpdate();
            con.close();
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    public static ArrayList<Plant> SelectByGroundName(String name) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flowers", "root", "12121212");
            PreparedStatement get_plant_by_type = con.prepareStatement("select plant_type, leaf_type, plant_name, temperature, ground_name, ground_description, age, height from plant join look on plant.look_id = look.id join Ground on ground.id = plant.ground_id where ground_name = ?");
            get_plant_by_type.setString(1, name);
            ResultSet resultSet = get_plant_by_type.executeQuery();
            ArrayList<Plant> result = new ArrayList<Plant>();
            while (resultSet.next()) {
                result.add(new Plant(0, resultSet.getString("plant_type"),
                        resultSet.getString("leaf_type"),
                        resultSet.getString("plant_name"),
                        resultSet.getDouble("temperature"),
                        resultSet.getString("ground_name"),
                        resultSet.getString("ground_description"),
                        resultSet.getInt("age"),
                        resultSet.getDouble("height")));
            }
            con.close();
            return result;
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
            return null;
        }
    }

    public static void ChangeGround(String plant_name, String ground_name, String ground_desription)
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flowers", "root", "12121212");
            PreparedStatement edit_plant_ground_by_plant_name = con.prepareStatement("UPDATE Ground SET ground_name = ?, ground_description = ? WHERE id IN (select ground_id from Plant where plant_name = ?)");
            edit_plant_ground_by_plant_name.setString(1, ground_name);
            edit_plant_ground_by_plant_name.setString(2, ground_desription);
            edit_plant_ground_by_plant_name.setString(3, plant_name);
            edit_plant_ground_by_plant_name.executeUpdate();
            con.close();
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }
    public static void DeletePlantByName(String plant_name)
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flowers", "root", "12121212");
            PreparedStatement delete_plant_by_name = con.prepareStatement("DELETE FROM Plant WHERE plant_name = ?;");
            delete_plant_by_name.setString(1, plant_name);
            delete_plant_by_name.executeUpdate();
            con.close();
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }
}


