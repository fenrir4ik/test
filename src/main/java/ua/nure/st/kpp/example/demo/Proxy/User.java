package ua.nure.st.kpp.example.demo.Proxy;

import ua.nure.st.kpp.example.demo.Flowers.Plant;
import ua.nure.st.kpp.example.demo.MyDAO.MySQLDAO;

import java.util.ArrayList;

public class User {
    private String login;
    private String pass;
    private Status status;
    private boolean auth;
    private MySQLDAO dao;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User(String login, String pass) {
        this.login = login;
        this.pass = pass;
        status = Status.None;
        dao = new MySQLDAO();
    }

    public User()
    {
        status = Status.None;
        dao = new MySQLDAO();
    }

    public void login()
    {
        status = dao.login_user(this);
    }

    public void register()
    {
        dao.register_user(this);
    }

    public void changeStatus(Status st)
    {
        status = st;
    }

    public Plant Insert_Plant(Plant plant)
    {
        return dao.Insert_Plant(plant, status);
    }

    public ArrayList<Plant> Get_Plants()
    {
        return dao.Get_Plants();
    }

    public ArrayList<Plant> SelectByGroundName(String name)
    {
        return dao.SelectByGroundName(name);
    }
    public void ChangeGround(String plant_name, String ground_name, String ground_desription)
    {
        dao.ChangeGround(plant_name, ground_name, ground_desription, status);
    }
    public void DeletePlantByName(String plant_name)
    {
        dao.DeletePlantByName(plant_name, status);
    }
    public ArrayList<Plant> Get_Plants_By_Name(String name)
    {
        return dao.Get_Plants_By_Name(name);
    }
}
