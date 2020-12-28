package ua.nure.st.kpp.example.demo.Parse;

import com.mongodb.client.*;
import org.bson.Document;
import ua.nure.st.kpp.example.demo.Parse.Plant;

import java.sql.*;


public class MongoDBDAO implements IDAO{
    private MongoDatabase database;
    private MongoClient mongoClient;
    private static MongoDBDAO instance = null;
    private static Connection con = null;
    private static String add_ground = "INSERT INTO Ground (ground_name, ground_description) select ?, ? WHERE NOT EXISTS (select id from ground where ground_name = ? and ground_description = ?)";
    private static String add_look = "INSERT INTO Look (leaf_type) select ? WHERE NOT EXISTS(select id from Look where leaf_type = ?)";
    private static String add_plant = "INSERT INTO Plant (look_id, ground_id, plant_name, temperature, plant_type, height, age) select ?, ?, ?, ?, ?, ?, ? where not exists" +
            "(select id from plant where look_id = ? and ground_id = ? and plant_name = ? and temperature = ? and plant_type = ? and height = ? and age = ?)";
    private static String get_look_id = "select id from look where leaf_type = ? LIMIT 1";
    private static String get_ground_id = "select id from ground where ground_name = ? and ground_description = ? LIMIT 1";
    private static String add_type = "INSERT INTO blooming_type (blooming_name) select ? WHERE NOT EXISTS(select id from blooming_type where blooming_name = ?)";
    private static String select_plant = "select id from plant where look_id = ? and ground_id = ? and plant_name = ? and temperature = ? and plant_type = ? and height = ? and age = ? LIMIT 1";
    private static String select_type = "select id from blooming_type where blooming_name = ? LIMIT 1";
    private static String insert_blooming_record = "insert into blooming_record (blooming_id, plant_id) select ?, ? where not exists (select id from blooming_record where blooming_id = ? and plant_id = ?)";
    private static String insert_log = "insert into coniferous_log (plant_id, cones_color, cones_len) select ?, ?, ? where not exists (select id from coniferous_log where plant_id = ? and cones_color = ? and cones_len = ?)";


    public MongoDBDAO() {
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flowers?serverTimezone=UTC", "root", "12121212");
            mongoClient = MongoClients.create();
            database = mongoClient.getDatabase("flowers");
        }
        catch (Exception ex)
        {
            System.out.println("Connection faliure...");
            System.out.println(ex);
        }
    }

    public static MongoDBDAO getInstance()
    {
        if (instance == null)
        {
            instance = new MongoDBDAO();
        }
        return instance;
    }

    @Override
    public void parse_data() {
        try {
            PreparedStatement insert_ground = con.prepareStatement(add_ground, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement insert_look = con.prepareStatement(add_look, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement insert_plant = con.prepareStatement(add_plant);
            PreparedStatement get_look = con.prepareStatement(get_look_id);
            PreparedStatement get_ground = con.prepareStatement(get_ground_id);
            PreparedStatement insert_type = con.prepareStatement(add_type);
            PreparedStatement get_plant = con.prepareStatement(select_plant);
            PreparedStatement select_type_blooming = con.prepareStatement(select_type);
            PreparedStatement insert_b_record = con.prepareStatement(insert_blooming_record);
            PreparedStatement insert_coniferous_record = con.prepareStatement(insert_log);

            MongoCollection coll = database.getCollection("plant");
            FindIterable<Document> cursor = coll.find();
            Plant plant;
            Look look;
            Ground ground;
            for (Document i : cursor) {
                look = new Look((String) ((Document) i.get("look")).get("leaf_type"));
                ground = new Ground((String) ((Document) i.get("ground")).get("ground_name"),
                        (String) ((Document) i.get("ground")).get("ground_description"));
                plant = new Plant((String) i.get("plant_name"),
                        (double) i.get("temperature"),
                        (String) i.get("plant_type"),
                        (double) i.get("height"),
                        (int) i.get("age"), look, ground);
                try {
                    insert_ground.setString(1, ground.ground_name);
                    insert_ground.setString(2, ground.ground_description);
                    insert_ground.setString(3, ground.ground_name);
                    insert_ground.setString(4, ground.ground_description);
                    insert_ground.executeUpdate();

                    insert_look.setString(1, look.leaf_type);
                    insert_look.setString(2, look.leaf_type);
                    insert_look.executeUpdate();

                    get_look.setString(1, look.leaf_type);
                    ResultSet resultSet = get_look.executeQuery();
                    resultSet.next();
                    int look_id = resultSet.getInt("id");
                    get_ground.setString(1, ground.ground_name);
                    get_ground.setString(2, ground.ground_description);
                    resultSet = get_ground.executeQuery();
                    resultSet.next();
                    int ground_id = resultSet.getInt("id");

                    insert_plant.setInt(1, look_id);
                    insert_plant.setInt(2, ground_id);
                    insert_plant.setString(3, plant.plant_name);
                    insert_plant.setDouble(4, plant.temperature);
                    insert_plant.setString(5, plant.plant_type);
                    insert_plant.setDouble(6, plant.height);
                    insert_plant.setInt(7, plant.age);
                    insert_plant.setInt(8, look_id);
                    insert_plant.setInt(9, ground_id);
                    insert_plant.setString(10, plant.plant_name);
                    insert_plant.setDouble(11, plant.temperature);
                    insert_plant.setString(12, plant.plant_type);
                    insert_plant.setDouble(13, plant.height);
                    insert_plant.setInt(14, plant.age);
                    insert_plant.executeUpdate();
                }
                catch (Exception ex) {
                    System.out.println("Error while inserting into mysql");
                    System.out.println(ex);
                }
            }
            coll = database.getCollection("look");
            cursor = coll.find();
            for (Document i : cursor) {
                look = new Look((String)i.get("leaf_type"));
                try{
                    insert_look.setString(1, look.leaf_type);
                    insert_look.setString(2, look.leaf_type);
                    insert_look.executeUpdate();
                }
                catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            coll = database.getCollection("ground");
            cursor = coll.find();
            for (Document i : cursor) {
                ground = new Ground((String)i.get("ground_name"),
                        (String)i.get("ground_description"));
                try{
                    insert_ground.setString(1, ground.ground_name);
                    insert_ground.setString(2, ground.ground_description);
                    insert_ground.setString(3, ground.ground_name);
                    insert_ground.setString(4, ground.ground_description);
                    insert_ground.executeUpdate();
                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
            coll = database.getCollection("blooming_type");
            Blooming_type type;
            cursor = coll.find();
            for (Document i : cursor) {
                type = new Blooming_type((String)i.get("blooming_name"));
                try{
                    insert_type.setString(1, type.name);
                    insert_type.setString(2, type.name);
                    insert_type.executeUpdate();
                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }

            coll = database.getCollection("blooming_record");
            String blooming_name;
            cursor = coll.find();
            for (Document i : cursor) {
                type = new Blooming_type((String) ((Document) i.get("blooming_type")).get("blooming_name"));
                look = new Look((String) ((Document)((Document)i.get("plant")).get("look")).get("leaf_type"));
                ground = new Ground(
                        (String) ((Document)((Document)i.get("plant")).get("ground")).get("ground_name"),
                        (String) ((Document)((Document)i.get("plant")).get("ground")).get("ground_description"));
                plant = new Plant((String) ((Document) i.get("plant")).get("plant_name"),
                        (double) ((Document) i.get("plant")).get("temperature"),
                        (String) ((Document) i.get("plant")).get("plant_type"),
                        (double) ((Document) i.get("plant")).get("height"),
                        (int) ((Document) i.get("plant")).get("age"), look, ground);

                blooming_name = (String) ((Document) i.get("blooming_type")).get("blooming_name");
                try{
                    insert_ground.setString(1, ground.ground_name);
                    insert_ground.setString(2, ground.ground_description);
                    insert_ground.setString(3, ground.ground_name);
                    insert_ground.setString(4, ground.ground_description);
                    insert_ground.executeUpdate();

                    insert_look.setString(1, look.leaf_type);
                    insert_look.setString(2, look.leaf_type);
                    insert_look.executeUpdate();

                    get_look.setString(1, look.leaf_type);
                    ResultSet resultSet = get_look.executeQuery();
                    resultSet.next();
                    int look_id = resultSet.getInt("id");
                    get_ground.setString(1, ground.ground_name);
                    get_ground.setString(2, ground.ground_description);
                    resultSet = get_ground.executeQuery();
                    resultSet.next();
                    int ground_id = resultSet.getInt("id");

                    insert_plant.setInt(1, look_id);
                    insert_plant.setInt(2, ground_id);
                    insert_plant.setString(3, plant.plant_name);
                    insert_plant.setDouble(4, plant.temperature);
                    insert_plant.setString(5, plant.plant_type);
                    insert_plant.setDouble(6, plant.height);
                    insert_plant.setInt(7, plant.age);
                    insert_plant.setInt(8, look_id);
                    insert_plant.setInt(9, ground_id);
                    insert_plant.setString(10, plant.plant_name);
                    insert_plant.setDouble(11, plant.temperature);
                    insert_plant.setString(12, plant.plant_type);
                    insert_plant.setDouble(13, plant.height);
                    insert_plant.setInt(14, plant.age);
                    insert_plant.executeUpdate();

                    get_plant.setInt(1, look_id);
                    get_plant.setInt(2, ground_id);
                    get_plant.setString(3, plant.plant_name);
                    get_plant.setDouble(4, plant.temperature);
                    get_plant.setString(5, plant.plant_type);
                    get_plant.setDouble(6, plant.height);
                    get_plant.setInt(7, plant.age);

                    resultSet = get_plant.executeQuery();
                    resultSet.next();
                    int plant_id = resultSet.getInt("id");

                    select_type_blooming.setString(1, blooming_name);
                    resultSet = select_type_blooming.executeQuery();
                    resultSet.next();
                    int blooming = resultSet.getInt("id");

                    insert_b_record.setInt(1, blooming);
                    insert_b_record.setInt(2, plant_id);
                    insert_b_record.setInt(3, blooming);
                    insert_b_record.setInt(4, plant_id);
                    insert_b_record.executeUpdate();
                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }

            coll = database.getCollection("coniferous_log");
            Coniferous_log log;
            cursor = coll.find();
            for (Document i : cursor) {
                look = new Look((String) ((Document)((Document)i.get("plant")).get("look")).get("leaf_type"));
                ground = new Ground(
                        (String) ((Document)((Document)i.get("plant")).get("ground")).get("ground_name"),
                        (String) ((Document)((Document)i.get("plant")).get("ground")).get("ground_description"));
                plant = new Plant((String) ((Document) i.get("plant")).get("plant_name"),
                        (double) ((Document) i.get("plant")).get("temperature"),
                        (String) ((Document) i.get("plant")).get("plant_type"),
                        (double) ((Document) i.get("plant")).get("height"),
                        (int) ((Document) i.get("plant")).get("age"), look, ground);
                log = new Coniferous_log((String) i.get("cones_color"),
                        (double)i.get("cones_len"), plant);
                try{
                    insert_ground.setString(1, ground.ground_name);
                    insert_ground.setString(2, ground.ground_description);
                    insert_ground.setString(3, ground.ground_name);
                    insert_ground.setString(4, ground.ground_description);
                    insert_ground.executeUpdate();

                    insert_look.setString(1, look.leaf_type);
                    insert_look.setString(2, look.leaf_type);
                    insert_look.executeUpdate();

                    get_look.setString(1, look.leaf_type);
                    ResultSet resultSet = get_look.executeQuery();
                    resultSet.next();
                    int look_id = resultSet.getInt("id");
                    get_ground.setString(1, ground.ground_name);
                    get_ground.setString(2, ground.ground_description);
                    resultSet = get_ground.executeQuery();
                    resultSet.next();
                    int ground_id = resultSet.getInt("id");

                    insert_plant.setInt(1, look_id);
                    insert_plant.setInt(2, ground_id);
                    insert_plant.setString(3, plant.plant_name);
                    insert_plant.setDouble(4, plant.temperature);
                    insert_plant.setString(5, plant.plant_type);
                    insert_plant.setDouble(6, plant.height);
                    insert_plant.setInt(7, plant.age);
                    insert_plant.setInt(8, look_id);
                    insert_plant.setInt(9, ground_id);
                    insert_plant.setString(10, plant.plant_name);
                    insert_plant.setDouble(11, plant.temperature);
                    insert_plant.setString(12, plant.plant_type);
                    insert_plant.setDouble(13, plant.height);
                    insert_plant.setInt(14, plant.age);
                    insert_plant.executeUpdate();

                    get_plant.setInt(1, look_id);
                    get_plant.setInt(2, ground_id);
                    get_plant.setString(3, plant.plant_name);
                    get_plant.setDouble(4, plant.temperature);
                    get_plant.setString(5, plant.plant_type);
                    get_plant.setDouble(6, plant.height);
                    get_plant.setInt(7, plant.age);

                    resultSet = get_plant.executeQuery();
                    resultSet.next();
                    int plant_id = resultSet.getInt("id");
                    insert_coniferous_record.setInt(1, plant_id);
                    insert_coniferous_record.setString(2, log.cones_color);
                    insert_coniferous_record.setDouble(3, log.cones_len);
                    insert_coniferous_record.setInt(4, plant_id);
                    insert_coniferous_record.setString(5, log.cones_color);
                    insert_coniferous_record.setDouble(6, log.cones_len);
                    insert_coniferous_record.executeUpdate();
                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error while parsing from mongodb to mysql");
            System.out.println(ex);
        }
    }
}
