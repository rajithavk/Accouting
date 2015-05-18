/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accountingsample;

/**
 *
 * @author UDARA
 */

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import javax.persistence.*;




@Entity
@Table(name="dentry")

public class DEntry{
    
    @Id
    @GeneratedValue    
    private int id = -1;
    
    @Column(name="credit")
    private String credit;
    
    @Column(name="debit")
    private String debit;
    
    @Column(name="amount")
    private double amount;

    public DEntry() {
    }
    
    public DEntry(String credit, String debit, double amount) {   
    
        this.credit = credit;    
        this.debit = debit;
        this.amount = amount;
    
    }
    
    public int save(){
        return saveDEntry(this);
    }
    
    public int delete(){
        return deleteDEntry(this);
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return this.id;
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
