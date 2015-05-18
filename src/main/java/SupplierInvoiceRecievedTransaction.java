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

public class SupplierInvoiceRecievedTransaction{

    @Id
    @GeneratedValue    
    private int id = -1;    
 

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "acc_no_credit")
    private Account accountsPayable;


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

    public SupplierInvoiceRecievedTransaction() {
    }


    public SupplierInvoiceRecievedTransaction(Account accountsPayable, Account stock, double amount, String describe ){
    
        this.accountsPayable = accountsPayable;
        this.stock = stock;

        this.amount = amount;
        this.description = describe;


        this.accountsPayable.setBalance(this.accountsPayable.getBalance() - this.amount);
        this.stock.setBalance(this.stock.getBalance() - this.amount);

        dEntryInst = new DEntry(this.accountsPayable.getName(), this.stock.getName(), this.amount);

    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String describe){
        this.description = describe;
    }     
 
    public int save(){
        return saveSupplierInvoiceRecievedTransaction(this);
    }
    
    public int delete(){
        return deleteSupplierInvoiceRecievedTransaction(this);
    }

    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }  




    private static int saveSupplierInvoiceRecievedTransaction(SupplierInvoiceRecievedTransaction supplierIRT){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            tx = session.beginTransaction();
            if(supplierIRT.getId() == -1) {
                bid = (Integer) session.save(supplierIRT);
                supplierIRT.setId(bid);
            }else{
                session.merge(supplierIRT);
                bid = supplierIRT.getId();
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


    private static int deleteSupplierInvoiceRecievedTransaction(SupplierInvoiceRecievedTransaction supplierIRT){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            bid = supplierIRT.getId();
            if(bid != -1) {
                tx = session.beginTransaction();
                session.delete(supplierIRT);
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
