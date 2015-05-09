/**
 * Created by romba on 5/10/15.
 */

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Accounting {
    private static SessionFactory factory;
    public static void main(String args[]) {
        try {
            factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create session factory object");
            throw new ExceptionInInitializerError(ex);
        }

        Accounting acc = new Accounting();

        Integer xx = acc.addBank("BOC",1);
        Integer yy = acc.addBank("Sampath",2);

    }
        public int addBank(String name, int id ){
            Session session = factory.openSession();
            Transaction tx = null;
            Integer bid = null;
            try{
                tx = session.beginTransaction();
                Bank bank = new Bank(id, name);
                bid = (Integer) session.save(bank);
                tx.commit();
                System.out.println(bid.toString());
            }catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                e.printStackTrace();
            }finally {
                session.close();
            }
            return bid;
        }
    }
