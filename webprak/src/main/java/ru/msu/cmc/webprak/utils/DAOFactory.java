package ru.msu.cmc.webprak.utils;

import ru.msu.cmc.webprak.DAO.AnimalsDAO;
import ru.msu.cmc.webprak.DAO.EmployeesDAO;
import ru.msu.cmc.webprak.DAO.LabelsDAO;
import ru.msu.cmc.webprak.DAO.DiseasesDAO;
import ru.msu.cmc.webprak.DAO.functions.AnimalsDAOFunc;
import ru.msu.cmc.webprak.DAO.functions.EmployeesDAOFunc;
import ru.msu.cmc.webprak.DAO.functions.LabelsDAOFunc;
import ru.msu.cmc.webprak.DAO.functions.DiseasesDAOFunc;
import ru.msu.cmc.webprak.models.Labels;


public class DAOFactory {

    private static AnimalsDAO animalDAO = null;
    private static EmployeesDAO employeeDAO = null;
    private static LabelsDAO labelDAO = null;
    private static DiseasesDAO diseaseDAO = null;
    private static DAOFactory instance = null;

    public static synchronized DAOFactory getInstance(){
        if (instance == null){
            instance = new DAOFactory();
        }
        return instance;
    }

    public AnimalsDAO getAnimalDAO(){
        if (animalDAO == null){
            animalDAO = new AnimalsDAOFunc();
        }
        return animalDAO;
    }

    public EmployeesDAO getEmployeeDAO(){
        if (employeeDAO == null){
            employeeDAO = new EmployeesDAOFunc();
        }
        return employeeDAO;
    }

    public LabelsDAO getLabelDAO(){
        if (labelDAO == null){
            labelDAO = new LabelsDAOFunc();
        }
        return labelDAO;
    }

    public DiseasesDAO getDiseaseDAO(){
        if (diseaseDAO == null){
            diseaseDAO = new DiseasesDAOFunc();
        }
        return diseaseDAO;
    }
}