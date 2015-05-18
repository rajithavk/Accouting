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

public class InvoiceCreditPaymentTransaction{

    @Id
    @GeneratedValue    
    private int id = -1;    
 

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "acc_no_credit")
    private Account cash;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "acc_no_debit")
    private Account accountsPayable;

    @Column(name="amount")
    private double amount;

    @Column(name="description")
    private String description;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private DEntry dEntryInst ;

    public InvoiceCreditPaymentTransaction() {
    }


    public InvoiceCreditPaymentTransaction(Account cash, Account accountsPayable, double amount, String describe ){
    
        this.cash = cash;
        this.accountsPayable = accountsPayable;

        this.amount = amount;
        this.description = describe;

        this.cash.setBalance(this.cash.getBalance() - this.amount);
        this.accountsPayable.setBalance(this.accountsPayable.getBalance() - this.amount);

        dEntryInst = new DEntry(this.cash.getName(), this.accountsPayable.getName(), this.amount);

    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String describe){
        this.description = describe;
    }     
    

    public int save(){
        return saveInvoiceCreditPaymentTransaction(this);
    }
    
    public int delete(){
        return deleteInvoiceCreditPaymentTransaction(this);
    }

    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }    


    private static int saveInvoiceCreditPaymentTransaction(InvoiceCreditPaymentTransaction invoiceCPT){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            tx = session.beginTransaction();
            if(invoiceCPT.getId() == -1) {
                bid = (Integer) session.save(invoiceCPT);
                invoiceCPT.setId(bid);
            }else{
                session.merge(invoiceCPT);
                bid = invoiceCPT.getId();
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



    private static int deleteInvoiceCreditPaymentTransaction(InvoiceCreditPaymentTransaction invoiceCPT){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            bid = invoiceCPT.getId();
            if(bid != -1) {
                tx = session.beginTransaction();
                session.delete(invoiceCPT);
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

