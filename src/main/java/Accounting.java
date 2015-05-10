/**
 * Created by romba on 5/10/15.
 */

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class Accounting {
    private static SessionFactory factory = HibernateUtil.getSessionFactory();
    public static void main(String args[]) {

        Bank xx = new Bank("Bank of Ceylon");
        System.out.println(xx.save());
        System.out.println(xx.getName());
        xx.setName("Sampath");
        System.out.println(Bank.saveBank(xx));
        System.out.println(xx.getName());
        xx.delete();

        BankBranch yy = new BankBranch("Kurunegala","Kurunegala","BOC6666");
        yy.save();

        BankBranch zz = new BankBranch("Colombo","Colombo","BOC1111");
        zz.save();
        zz.setCode("BOC0000");
        zz.save();

//        try {
//            factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
//        } catch (Throwable ex) {
//            System.err.println("Failed to create session factory object");
//            throw new ExceptionInInitializerError(ex);
//        }


    }
//        public int addBank(String name, int id ){
//            Session session = factory.openSession();
//            Transaction tx = null;
//            Integer bid = null;
//            try{
//                tx = session.beginTransaction();
//                Bank bank = new Bank(id, name);
//                bid = (Integer) session.save(bank);
//                tx.commit();
//                System.out.println(bid.toString());
//            }catch (HibernateException e) {
//                if (tx!=null) tx.rollback();
//                e.printStackTrace();
//            }finally {
//                session.close();
//            }
//            return bid;
//        }
    }
