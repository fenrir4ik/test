package ua.nure.st.kpp.example.demo.Parse;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.*;
import org.bson.Document;
import java.sql.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class MySQLDAO {
    private MongoDatabase database;
    private MongoClient mongoClient;
    private static MySQLDAO instance = null;
    private static Connection con = null;
    private static int counter = 0;
    private static String get_plant_look_ground = "select plant_name, temperature, plant_type, height, age, leaf_type, ground_name, ground_description " +
            "from plant join look on look_id = look.id " +
            "join ground on ground_id = ground.id";

    private static String get_look = "select * from look";
    private static String get_ground = "select * from ground";
    private static String get_blooming_type = "select * from blooming_type";
    private static String get_blooming_record = "select blooming_name, plant_name, temperature, plant_type, height, age, leaf_type, ground_name, ground_description \n" +
            "from plant join blooming_record on plant_id = plant.id\n" +
            "join ground on ground_id = ground.id\n" +
            "join look on look_id = look.id\n" +
            "join blooming_type on blooming_type.id = blooming_id;";
    private static String get_coniferous_log = "select plant_name, cones_len, cones_color, temperature, plant_type, height, age, leaf_type, ground_name, ground_description \n" +
            "from plant join ground on ground_id = ground.id\n" +
            "join look on look_id = look.id\n" +
            "join coniferous_log on plant_id = plant.id;";

    public MySQLDAO() {
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

    public static MySQLDAO getInstance()
    {
        if (instance == null)
        {
            instance = new MySQLDAO();
        }
        return instance;
    }

    public void find_all()
    {
        MongoCollection coll = database.getCollection("plant");
        long n = coll.count();
        System.out.println("Найдено " + n + " записей");
    }

    public void parse_data() {
        try {
            PreparedStatement get_plant = con.prepareStatement(get_plant_look_ground);
            ResultSet resultSet = get_plant.executeQuery();
            Plant plant;
            while (resultSet.next()) {
                plant = new Plant(resultSet.getString("plant_name"),
                        resultSet.getDouble("temperature"),
                        resultSet.getString("plant_type"),
                        resultSet.getDouble("height"),
                        resultSet.getInt("age"),
                        new Look(resultSet.getString("leaf_type")),
                        new Ground(resultSet.getString("ground_name"), resultSet.getString("ground_description")));
                try {
                    MongoCollection todoCollection = database.getCollection("plant");
                    Document todoDocument = new Document("plant_name", plant.getPlant_name())
                            .append("temperature", plant.getTemperature())
                            .append("height", plant.getHeight())
                            .append("age", plant.getAge())
                            .append("plant_type", plant.getPlant_type())
                            .append("look", new Document("leaf_type", plant.look.leaf_type))
                            .append("ground", new Document("ground_name", plant.ground.getGround_name()).append("ground_description", plant.ground.getGround_description()));
                    todoCollection.insertOne(todoDocument);
                }
                catch (Exception e)
                {
                    if (counter < 3){
                        counter++;
                        TimeUnit.SECONDS.sleep(1);
                        resultSet.previous();
                    }
                    else
                    {
                        System.out.println("Превышено количество попыток записи");
                        System.exit(-1);
                    }
                }
            }
            counter = 0;
        } catch (Exception ex) {
            System.out.println("Query failed while parsing plant...");
            System.out.println(ex);
        }

        try {
            PreparedStatement get_plant = con.prepareStatement(get_look);
            ResultSet resultSet = get_plant.executeQuery();
            Look look;
            while (resultSet.next()) {
                look = new Look(resultSet.getString("leaf_type"));
                try {
                    MongoCollection todoCollection = database.getCollection("look");
                    Document todoDocument = new Document("leaf_type", look.leaf_type);
                    todoCollection.insertOne(todoDocument);
                }
                catch (Exception e)
                {
                    if (counter < 3){
                        counter++;
                        TimeUnit.SECONDS.sleep(1);
                        resultSet.previous();
                    }
                    else
                    {
                        System.out.println("Превышено количество попыток записи");
                        System.exit(-1);
                    }
                }
            }
        counter = 0;
        } catch (Exception ex) {
            System.out.println("Query failed while parsing look...");
            System.out.println(ex);
        }

        try {
            PreparedStatement get_plant = con.prepareStatement(get_ground);
            ResultSet resultSet = get_plant.executeQuery();
            Ground ground;
            while (resultSet.next()) {
                ground = new Ground(resultSet.getString("ground_name"), resultSet.getString("ground_description"));
                try {
                    MongoCollection todoCollection = database.getCollection("ground");
                    Document todoDocument = new Document("ground_name", ground.ground_name).append("ground_description", ground.ground_description);
                    todoCollection.insertOne(todoDocument);
                }
                catch (Exception e)
                {
                    if (counter < 3){
                        counter++;
                        TimeUnit.SECONDS.sleep(1);
                        resultSet.previous();
                    }
                    else
                    {
                        System.out.println("Превышено количество попыток записи");
                        System.exit(-1);
                    }
                }
            }
        counter = 0;
        } catch (Exception ex) {
            System.out.println("Query failed while parsing ground...");
            System.out.println(ex);
        }

        try {
            PreparedStatement get_plant = con.prepareStatement(get_blooming_type);
            ResultSet resultSet = get_plant.executeQuery();
            Blooming_type type;
            while (resultSet.next()) {
                type = new Blooming_type(resultSet.getString("blooming_name"));
                try {
                    MongoCollection todoCollection = database.getCollection("blooming_type");
                    Document todoDocument = new Document("blooming_name", type.name);
                    todoCollection.insertOne(todoDocument);
                }
                catch (Exception e)
                {
                    if (counter < 3){
                        counter++;
                        TimeUnit.SECONDS.sleep(1);
                        resultSet.previous();
                    }
                    else
                    {
                        System.out.println("Превышено количество попыток записи");
                        System.exit(-1);
                    }
                }
            }
        counter = 0;
        } catch (Exception ex) {
            System.out.println("Query failed while parsing blooming type...");
            System.out.println(ex);
        }

        try {
            PreparedStatement get_plant = con.prepareStatement(get_blooming_record);
            ResultSet resultSet = get_plant.executeQuery();
            Plant plant;
            Blooming_type type;
            while (resultSet.next()) {
                plant = new Plant(resultSet.getString("plant_name"),
                        resultSet.getDouble("temperature"),
                        resultSet.getString("plant_type"),
                        resultSet.getDouble("height"),
                        resultSet.getInt("age"),
                        new Look(resultSet.getString("leaf_type")),
                        new Ground(resultSet.getString("ground_name"),
                                resultSet.getString("ground_description")));
                type = new Blooming_type(resultSet.getString("blooming_name"));
                try {
                    MongoCollection todoCollection = database.getCollection("blooming_record");
                    Document todoDocument = new Document("plant", new Document("plant_name", plant.getPlant_name())
                            .append("temperature", plant.getTemperature())
                            .append("height", plant.getHeight())
                            .append("age", plant.getAge())
                            .append("plant_type", plant.getPlant_type())
                            .append("look", new Document("leaf_type", plant.look.leaf_type))
                            .append("ground", new Document("ground_name", plant.ground.getGround_name()).append("ground_description", plant.ground.getGround_description())))
                            .append("blooming_type", new Document("blooming_name", type.name));
                    todoCollection.insertOne(todoDocument);
                }
                catch (Exception e)
                {
                    if (counter < 3){
                        counter++;
                        TimeUnit.SECONDS.sleep(1);
                        resultSet.previous();
                    }
                    else
                    {
                        System.out.println("Превышено количество попыток записи");
                        System.exit(-1);
                    }
                }
            }
        counter = 0;
        } catch (Exception ex) {
            System.out.println("Query failed while parsing blooming_record...");
            System.out.println(ex);
        }

        try {
            PreparedStatement get_plant = con.prepareStatement(get_coniferous_log);
            ResultSet resultSet = get_plant.executeQuery();
            Plant plant;
            Coniferous_log log;
            while (resultSet.next()) {
                plant = new Plant(resultSet.getString("plant_name"),
                        resultSet.getDouble("temperature"),
                        resultSet.getString("plant_type"),
                        resultSet.getDouble("height"),
                        resultSet.getInt("age"),
                        new Look(resultSet.getString("leaf_type")),
                        new Ground(resultSet.getString("ground_name"), resultSet.getString("ground_description")));
                log = new Coniferous_log(resultSet.getString("cones_color"), resultSet.getDouble("cones_len"), plant);
                try {
                    MongoCollection todoCollection = database.getCollection("coniferous_log");
                    Document todoDocument = new Document("plant", new Document("plant_name", plant.getPlant_name())
                            .append("temperature", plant.getTemperature())
                            .append("height", plant.getHeight())
                            .append("age", plant.getAge())
                            .append("plant_type", plant.getPlant_type())
                            .append("look", new Document("leaf_type", plant.look.leaf_type))
                            .append("ground", new Document("ground_name", plant.ground.getGround_name()).append("ground_description", plant.ground.getGround_description())))
                            .append("cones_color", log.cones_color)
                            .append("cones_len", log.cones_len);
                    todoCollection.insertOne(todoDocument);
                }
                catch (Exception e)
                {
                    if (counter < 3){
                        counter++;
                        TimeUnit.SECONDS.sleep(1);
                        resultSet.previous();
                    }
                    else
                    {
                        System.out.println("Превышено количество попыток записи");
                        System.exit(-1);
                    }
                }
            }
        counter = 0;
        } catch (Exception ex) {
            System.out.println("Query failed while parsing coniferous_log...");
            System.out.println(ex);
        }
    }
}
