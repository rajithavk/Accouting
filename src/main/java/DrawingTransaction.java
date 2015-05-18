/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accountingsample;

/**
 *
 * @author UDARA
 */
import javax.persistence.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;


@Entity
@Table(name="accTransactions")
public class DrawingTransaction{

    @Id
    @GeneratedValue    
    private int id = -1;    
 

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "acc_no_credit")
    private Account cash;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "acc_no_debit")
    private Account drawing;

    @Column(name="amount")
    private double amount;

    @Column(name="description")
    private String description;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private DEntry dEntryInst ;

    public DrawingTransaction() {
    }

    public DrawingTransaction(Account cash, Account drawing, double amount, String describe ){
    
        this.cash = cash;
        this.drawing = drawing;
        
        this.amount = amount;        
        this.description = describe;

    
        this.cash.setBalance(this.cash.getBalance() - this.amount);
        this.drawing.setBalance(this.drawing.getBalance() - this.amount);

        dEntryInst = new DEntry(this.cash.getName(), this.drawing.getName(), this.amount);

    } 
    
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String describe){
        this.description = describe;
    }   
    
    public int save(){
        return saveDrawingTransaction(this);
    }  
    
    public int delete(){
        return deleteDrawingTransaction(this);
    }   
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }



    private static int saveDrawingTransaction(DrawingTransaction drawingTransaction){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            tx = session.beginTransaction();
            if(drawingTransaction.getId() == -1) {
                bid = (Integer) session.save(drawingTransaction);
                drawingTransaction.setId(bid);
            }else{
                session.merge(drawingTransaction);
                bid = drawingTransaction.getId();
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



    private static int deleteDrawingTransaction(DrawingTransaction drawingTransaction){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            bid = drawingTransaction.getId();
            if(bid != -1) {
                tx = session.beginTransaction();
                session.delete(drawingTransaction);
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