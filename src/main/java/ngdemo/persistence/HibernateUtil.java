package ngdemo.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;// AnnotationConfiguration;
import org.hibernate.service.ServiceRegistry;

//http://www.mkyong.com/hibernate/maven-hibernate-annonation-mysql-example/
//http://stackoverflow.com/questions/13759894/entitymanager-persist-method-does-not-insert-record-to-database
// AnnotationConfiguration, buildSessionFactory are deprecated and used below link to fix it
//http://stackoverflow.com/questions/3942880/hibernate-annotationconfiguration-deprecated
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            //return new AnnotationConfiguration().configure().buildSessionFactory();
        	Configuration cfg=new Configuration();
        	cfg.configure();
        	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
        	cfg.getProperties()).build();

        	return new Configuration().configure().buildSessionFactory(serviceRegistry);
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
    	// Close caches and connection pools
    	getSessionFactory().close();
    }

}