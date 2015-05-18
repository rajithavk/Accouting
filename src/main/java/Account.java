/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accountingsample;

/**
 *
 * @author UDARA
 */
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import javax.persistence.*;



@Entity
//@Table(name="account")
public class Account{
    
   @Id
   @GeneratedValue     
   private int accNo = -1; 
   
   @Column(name="balance")
   private double balance; 
   
   @Column(name="owner_type")
   private String ownerType; 
   
//   @Column(name="credit_account")
//   private String accType;
//   
//   @Column(name="credit_account")
//   private String status;
   
   @Column(name="acc_name")
   private String accName;


    public Account() {
    }
          
    
    public Account( double amount, String ownerType, String accName){
        this.balance = amount;
        this.ownerType = ownerType;
        this.accName = accName;
    }    
    
    public double getBalance(){
        return balance;  
    }    


    public void setBalance(double amount){
        this.balance = amount;
    }

    public String getOwnerType(){
        return ownerType;    
    }
    public void setOwnerType(String ownerType){
        this.ownerType = ownerType;    
    }    

    public int save(){
        return saveAccount(this);
    }
    
    public int delete(){
        return deleteAccount(this);
    }

    public int getId(){
        return this.accNo;
    }
    
    public void setId(int accNo){
        this.accNo = accNo;
    }  

    public String getName(){
        return accName;
    }


    
    private static int saveAccount(Account account){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            tx = session.beginTransaction();
            if(account.getId() == -1) {
                bid = (Integer) session.save(account);
                account.setId(bid);
            }else{
                session.merge(account);
                bid = account.getId();
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


    private static int deleteAccount(Account account){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            bid = account.getId();
            if(bid != -1) {
                tx = session.beginTransaction();
                session.delete(account);
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