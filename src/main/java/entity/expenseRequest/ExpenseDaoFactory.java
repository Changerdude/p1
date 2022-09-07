package entity.expenseRequest;

public class ExpenseDaoFactory {

    private static ExpenseDao expenseDao;
    private ExpenseDaoFactory(){

    }

    public static ExpenseDao getExpenseDao(){
        if(expenseDao == null){
            expenseDao = new ExpenseDaoImpl();
        }
        return expenseDao;
    }
}
