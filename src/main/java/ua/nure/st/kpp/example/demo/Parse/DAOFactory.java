package ua.nure.st.kpp.example.demo.Parse;

import ua.nure.st.kpp.example.demo.MongoDBDAO.MongoDbDAO;
import ua.nure.st.kpp.example.demo.MyDAO.IDAO;
import ua.nure.st.kpp.example.demo.MyDAO.MySQLDAO;
import ua.nure.st.kpp.example.demo.MyDAO.TypeDAO;

public class DAOFactory {
    private static ua.nure.st.kpp.example.demo.MyDAO.IDAO dao = null;

    public static IDAO getDAOInstance(TypeDAO type) {
        if (dao == null) {
            if (type == TypeDAO.MySQL)
            {
                dao = new MySQLDAO();
            }
            else
            {
                dao = new MongoDbDAO();
            }
        }
        return dao;
    }
}