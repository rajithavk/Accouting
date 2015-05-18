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
@Table(name="accTransactions")
public class GRNTransaction{

    @Id
    @GeneratedValue    
    private int id = -1;    
 

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "acc_no_credit")
    private Account pendingGRN;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "acc_no_debit")
    private Account stock;

    @Column(name="amount")
    private double amount;

    @Column(name="description")
    private String description;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private DEntry dEntryInst ;

    public GRNTransaction() {
    }


    public GRNTransaction(Account pendingGRN, Account stock, double amount, String describe ){
    
        this.pendingGRN = pendingGRN;
        this.stock  = stock;

        this.amount = amount;
        this.description = describe;


        this.pendingGRN.setBalance(this.pendingGRN.getBalance() + this.amount);
        this.stock.setBalance(this.stock.getBalance() + this.amount);

        dEntryInst = new DEntry(this.pendingGRN.getName(), this.stock.getName(), this.amount);

    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String describe){
        this.description = describe;
    }   
    
    public int save(){
        return saveGRNTransaction(this);
    }    
    
    public int delete(){
        return deleteGRNTransaction(this);
    }    

    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }



    private static int saveGRNTransaction(GRNTransaction gRNTransaction){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            tx = session.beginTransaction();
            if(gRNTransaction.getId() == -1) {
                bid = (Integer) session.save(gRNTransaction);
                gRNTransaction.setId(bid);
            }else{
                session.merge(gRNTransaction);
                bid = gRNTransaction.getId();
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



    private static int deleteGRNTransaction(GRNTransaction gRNTransaction){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            bid = gRNTransaction.getId();
            if(bid != -1) {
                tx = session.beginTransaction();
                session.delete(gRNTransaction);
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
