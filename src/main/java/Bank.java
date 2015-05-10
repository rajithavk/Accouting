import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/**
 * Created by romba on 5/10/15.
 */
public class Bank {
    private int id=-1;
    private String name;

    public Bank() {}

    public Bank(String name){
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }


    public int save(){
       return saveBank(this);
    }
    public static int saveBank(Bank bank){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            tx = session.beginTransaction();
            if(bank.getId() == -1) {
                bid = (Integer) session.save(bank);
                bank.setId(bid);
            }else{
                session.merge(bank);
                bid = bank.getId();
            }
            tx.commit();
        }catch (HibernateException e){
            if(tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return bid;
    }

    public static int deleteBank(Bank bank){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            bid = bank.getId();
            if(bid != -1) {
                tx = session.beginTransaction();
                session.delete(bank);
                tx.commit();
            }
        }catch (HibernateException e){
            if(tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return bid;
    }

    public int delete(){
        return deleteBank(this);
    }
}
