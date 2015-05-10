import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by romba on 5/10/15.
 */
@Entity
@Table(name="bank")
public class Bank {
    @Id
    @GeneratedValue
    private int id=-1;

    @Column(name="bank")
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
