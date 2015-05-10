import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Created by romba on 5/10/15.
 * Creates a static SessionFactory for the DB access through Hibernate.
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        try{
            return new AnnotationConfiguration()
                    .configure()
                    .buildSessionFactory();

        }catch(Throwable ex){
            System.err.println("Session Factory Creation Error");
            throw new ExceptionInInitializerError(ex);
        }
    }
    public  static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
