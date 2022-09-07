package entity.user;

import entity.expenseRequest.ExpenseDao;
import entity.expenseRequest.ExpenseDaoImpl;

public class UserDaoFactory {
    private static UserDao userDao;
    private UserDaoFactory(){

    }

    public static UserDao getUserDao(){
        if(userDao == null){
            userDao = new UserDaoImpl();
        }
        return userDao;
    }
}
