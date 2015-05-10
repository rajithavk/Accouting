import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import javax.persistence.*;

/**
 * Created by romba on 5/10/15.
 */
@Entity
@Table(name="dentry")
public class DEntry {
    @Id
    @GeneratedValue
    private int id = -1;

    @Column(name="entry1")
    private int entry1;

    @Column(name="entry2")
    private int entry2;

    public DEntry(){}
    public DEntry(int entry1,int entry2){
        this.entry1 = entry1;
        this.entry2 = entry2;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }

    public void setEntry1(int entry1){
        this.entry1 = entry1;
    }

    public int getEntry1(){
        return this.entry1;
    }

    public void setEntry2(int entry2){
        this.entry2 = entry2;
    }

    public int getEntry2(){
        return this.entry2;
    }

    public int save(){
        return saveDEntry(this);
    }


    private static int saveDEntry(DEntry dEntry){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            tx = session.beginTransaction();
            if(dEntry.getId() == -1) {
                bid = (Integer) session.save(dEntry);
                dEntry.setId(bid);
            }else{
                session.merge(dEntry);
                bid = dEntry.getId();
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

    public int delete(){
        return deleteDEntry(this);
    }

    private static int deleteDEntry(DEntry dEntry){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            bid = dEntry.getId();
            if(bid != -1) {
                tx = session.beginTransaction();
                session.delete(dEntry);
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
}
