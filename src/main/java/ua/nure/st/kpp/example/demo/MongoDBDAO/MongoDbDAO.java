package ua.nure.st.kpp.example.demo.MongoDBDAO;


import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;
import ua.nure.st.kpp.example.demo.Flowers.Plant;
import ua.nure.st.kpp.example.demo.MyDAO.IDAO;
import com.mongodb.client.model.Aggregates.*;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import ua.nure.st.kpp.example.demo.Proxy.Status;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Accumulators.max;
import static com.mongodb.client.model.Aggregates.count;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;


public class MongoDbDAO implements IDAO {
    private static MongoDbDAO instance = null;
    private MongoDatabase database;
    private MongoClient mongoClient;

    public MongoDbDAO() {
        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase("flowers");
        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ERROR);
    }

    public static MongoDbDAO getInstance()
    {
        if (instance == null)
        {
            instance = new MongoDbDAO();
        }
        return instance;
    }

    @Override
    public Plant Insert_Plant(Plant plant, Status st) {
        try {
            MongoCollection todoCollection = database.getCollection("plant");

            Document todoDocument = new Document("plant_name", plant.getName())
                    .append("temperature", plant.getTemperature())
                    .append("height", plant.getHeight())
                    .append("age", plant.getAge())
                    .append("plant_type", plant.getPlant_type())
                    .append("look", new Document("leaf_type", plant.getLeaf_type()))
                    .append("ground", new Document("ground_name", plant.getGround_name()).append("ground_description", plant.getGround_description()));
            todoCollection.insertOne(todoDocument);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return plant;
    }

    @Override
    public ArrayList<Plant> Get_Plants() {
        MongoCollection coll = database.getCollection("plant");
        FindIterable<Document> cursor = coll.find();
        ArrayList<Plant> result = new ArrayList<Plant>();
        for (Document i: cursor) {
            result.add(new Plant(0,
                    (String)i.get("plant_type"),
                    (String)((Document)i.get("look")).get("leaf_type"),
                    (String)i.get("plant_name"),
                    (double)i.get("temperature"),
                    (String)((Document)i.get("ground")).get("ground_name"),
                    (String)((Document)i.get("ground")).get("ground_description"),
                    (int)i.get("age"),
                    (double)i.get("height")));

        }
        return result;
    }

    @Override
    public ArrayList<Plant> SelectByGroundName(String name) {
        MongoCollection coll = database.getCollection("plant");
        FindIterable<Document> cursor = coll.find(new Document("ground.ground_name", name));

        ArrayList<Plant> result = new ArrayList<Plant>();

        for (Document i: cursor) {
            result.add(new Plant(0,
                    (String)i.get("plant_type"),
                    (String)((Document)i.get("look")).get("leaf_type"),
                    (String)i.get("plant_name"),
                    (double)i.get("temperature"),
                    (String)((Document)i.get("ground")).get("ground_name"),
                    (String)((Document)i.get("ground")).get("ground_description"),
                    (int)i.get("age"),
                    (double)i.get("height")));

        }
        return result;
    }

    @Override
    public void ChangeGround(String plant_name, String ground_name, String ground_desription, Status st) {
        MongoCollection coll = database.getCollection("plant");
        coll.updateOne(new Document("plant_name", plant_name),
                new Document("$set", new Document("ground.ground_name", ground_name).append("ground.ground_description", ground_desription)));
    }

    @Override
    public void DeletePlantByName(String plant_name, Status st) {
        MongoCollection coll = database.getCollection("plant");
        coll.deleteMany(new Document("plant_name", plant_name));
    }

    @Override
    public ArrayList<Plant> Get_Plants_By_Name(String name) {
        MongoCollection coll = database.getCollection("plant");
        FindIterable<Document> cursor = coll.find(new Document("plant_name", name));

        ArrayList<Plant> result = new ArrayList<Plant>();
        for (Document i: cursor) {
            result.add(new Plant(0,
                    (String)i.get("plant_type"),
                    (String)((Document)i.get("look")).get("leaf_type"),
                    (String)i.get("plant_name"),
                    (double)i.get("temperature"),
                    (String)((Document)i.get("ground")).get("ground_name"),
                    (String)((Document)i.get("ground")).get("ground_description"),
                    (int)i.get("age"),
                    (double)i.get("height")));

        }
        return result;
    }

    public double AverageTemperatureRose()
    {
        MongoCollection coll = database.getCollection("plant");
        FindIterable<Document> cursor = coll.find(new Document("plant_type", "Роза"));
        double result = 0;
        int counter = 0;
        for (Document i: cursor) {
            counter++;
            result+=i.getDouble("temperature");
        }
        return result/counter;
    }

    public int SumDaysCactus()
    {
        MongoCollection coll = database.getCollection("plant");
        FindIterable<Document> cursor = coll.find(new Document("plant_type", "Кактус"));
        int result = 0;
        for (Document i: cursor) {
            result+=i.getInteger("age");
        }
        return result;
    }

    public int CountSandPlants()
    {
        MongoCollection coll = database.getCollection("plant");
        FindIterable<Document> cursor = coll.find(new Document("ground.ground_name", "Песок"));
        int result = 0;
        for (Document i: cursor) {
            result++;
        }
        return result;
    }

    public double AverageHeightForPlantsWithNeedles()
    {
        MongoCollection coll = database.getCollection("plant");
        FindIterable<Document> cursor = coll.find(new Document("look.leaf_type", "Иглы"));
        double result = 0;
        int counter = 0;
        for (Document i: cursor) {
            counter++;
            result+=i.getDouble("height");
        }
        return result/counter;
    }

    public double MaxHeightOfRoses()
    {
        MongoCollection coll = database.getCollection("plant");
        FindIterable<Document> cursor = coll.find(new Document("plant_type", "Роза"));
        double max_height = -1;
        for (Document i: cursor) {
            if (i.getDouble("height") > max_height)
            {
                max_height = i.getDouble("height");
            }
        }
        return max_height;
    }

    public double AF_AverageTemperatureRose()
    {
        MongoCollection coll = database.getCollection("plant");
        Bson project = Aggregates.project(fields(excludeId(), include("temperature"), include("plant_type")));
        Bson match = match(eq("plant_type", "Роза"));
        Bson group = Aggregates.group("averageTemperature", avg("average", "$temperature"));
        Document result = (Document) (coll.aggregate(Arrays.asList(project, match, group))).first();
        if (result != null)
        {
            return result.getDouble("average");
        }
        else
        {
            return -1;
        }
    }

    public int AF_SumDaysCactus()
    {
        MongoCollection coll = database.getCollection("plant");
        Bson project = Aggregates.project(fields(excludeId(), include("age"), include("plant_type")));
        Bson match = match(eq("plant_type", "Кактус"));
        Bson group = Aggregates.group("sumDays", Accumulators.sum("sum", "$age"));
        Document result = (Document) (coll.aggregate(Arrays.asList(project, match, group))).first();
        if (result != null)
        {
            return result.getInteger("sum");
        }
        else
        {
            return -1;
        }
    }

    public int AF_CountSandPlants()
    {
        MongoCollection coll = database.getCollection("plant");
        Bson project = Aggregates.project(fields(include("ground.ground_name")));
        Bson match = match(eq("ground.ground_name", "Песок"));
        Bson group = Aggregates.group("countPlants", Accumulators.sum("count", 1));
        Document result = (Document) (coll.aggregate(Arrays.asList(project, match, group))).first();
        if (result != null)
        {
            return result.getInteger("count");
        }
        else
        {
            return -1;
        }
    }

    public double AF_AverageHeightForPlantsWithNeedles()
    {
        MongoCollection coll = database.getCollection("plant");
        Bson project = Aggregates.project(fields(excludeId(), include("look.leaf_type"), include("height")));
        Bson match = match(eq("look.leaf_type", "Иглы"));
        Bson group = Aggregates.group("averageHeight", avg("average", "$height"));
        Document result = (Document) (coll.aggregate(Arrays.asList(project, match, group))).first();
        if (result != null)
        {
            return result.getDouble("average");
        }
        else
        {
            return -1;
        }
    }

    public double AF_MaxHeightOfRoses()
    {
        MongoCollection coll = database.getCollection("plant");
        Bson project = Aggregates.project(fields(excludeId(), include("plant_type"), include("height")));
        Bson match = match(eq("plant_type", "Роза"));
        Bson group = Aggregates.group("maxHeight", max("max", "$height"));
        Document result = (Document) (coll.aggregate(Arrays.asList(project, match, group))).first();
        if (result != null)
        {
            return result.getDouble("max");
        }
        else
        {
            return -1;
        }
    }
}
