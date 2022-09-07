package entity.expenseRequest;

import java.util.List;

public interface ExpenseDao {

    void saveExpense(ExpenseRequest expenseRequest);

    ExpenseRequest getExpenseById(int id);

    List<ExpenseRequest> getAllExpenses();

    List<ExpenseRequest> getExpensesByUser(int id);

    List<ExpenseRequest> getExpensesByStatus(String status);

    void updateExpenseRequest(ExpenseRequest expenseRequest);

    void deleteExpenseRequest(int id);
}
