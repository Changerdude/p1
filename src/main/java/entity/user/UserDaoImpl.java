package entity.user;

import entity.expenseRequest.ExpenseRequest;
import hibernate.HibernateConf;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoImpl implements UserDao{
    private SessionFactory factory = HibernateConf.getFactory();
    @Override
    public void saveUser(User user) {
        Transaction transaction = null;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    @Override
    public User getUserById(int id) {
        Transaction transaction = null;
        User user= null;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            user = session.get(User.class, id);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        Transaction transaction = null;
        User user= null;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            List users = session.createQuery("from User where email = :email")
                    .setParameter("email", email).list();
            if(!users.isEmpty()) {
                user = (User) users.get(0);
            }
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users= null;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User").list();
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public List<User> getUsersByPosition(Boolean isManager) {
        Transaction transaction = null;
        List<User> users= null;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User where isManager = :isManager")
                    .setParameter("isManager", isManager).list();
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void updateUser(User user) {
        Transaction transaction = null;
        try(Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    @Override
    public void deleteUser(int id) {
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
    }
}
