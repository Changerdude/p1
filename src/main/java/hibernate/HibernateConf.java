package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConf {
    private static SessionFactory factory;

    private HibernateConf(){}

    public static SessionFactory getFactory(){
        if(factory == null){
            Configuration config = new Configuration();
            config.configure("hibernate.cfg.xml");
            factory = config.buildSessionFactory();
        }
        return factory;
    }
}
