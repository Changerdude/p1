package entity.expenseRequest;

import hibernate.HibernateConf;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ExpenseDaoImpl implements ExpenseDao{

    private SessionFactory factory = HibernateConf.getFactory();
    @Override
    public void saveExpense(ExpenseRequest expenseRequest) {
        Transaction transaction = null;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(expenseRequest);
            transaction.commit();
        } catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    @Override
    public ExpenseRequest getExpenseById(int id) {
        Transaction transaction = null;
        ExpenseRequest expenseRequest= null;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            expenseRequest = session.get(ExpenseRequest.class, id);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        return expenseRequest;
    }

    @Override
    public List<ExpenseRequest> getAllExpenses() {
        Transaction transaction = null;
        List<ExpenseRequest> expenseRequests= null;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            expenseRequests = session.createQuery("from ExpenseRequest").list();
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        return expenseRequests;
    }

    @Override
    public List<ExpenseRequest> getExpensesByUser(int id) {
        Transaction transaction = null;
        List<ExpenseRequest> expenseRequests= null;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            expenseRequests = session.createQuery("from ExpenseRequest where submitId = :id")
                    .setParameter("id", id).list();
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        return expenseRequests;
    }

    @Override
    public List<ExpenseRequest> getExpensesByStatus(String status) {
        Transaction transaction = null;
        List<ExpenseRequest> expenseRequests= null;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            expenseRequests = session.createQuery("from ExpenseRequest where status = :status")
                    .setParameter("status", status).list();
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        return expenseRequests;
    }

    @Override
    public void updateExpenseRequest(ExpenseRequest expenseRequest) {
        Transaction transaction = null;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(expenseRequest);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    @Override
    public void deleteExpenseRequest(int id) {
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            ExpenseRequest expenseRequest = session.get(ExpenseRequest.class, id);
            session.delete(expenseRequest);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
    }
}
