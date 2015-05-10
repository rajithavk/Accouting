import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import javax.persistence.*;

/**
 * Created by romba on 5/10/15.
 */

@Entity
@Table(name="bank_account")
public class BankAccount {
    @Id
    @GeneratedValue
    private int id = -1;

    @Column(name="account_no")
    private String accountNo;

    @Column(name="bank")
    private int bank;

    @Column(name="bank_branch")
    private int bankBranch;

    public BankAccount(){}
    public BankAccount(String accountNo, int bank,int bankBranch){
        this.accountNo = accountNo;
        this.bank = bank;
        this.bankBranch = bankBranch;
    }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }

    public void setAccountNo(String accountNo){
        this.accountNo = accountNo;
    }

    public void setBank(int bank){
        this.bank = bank;
    }

    public int getBank(){
        return this.bank;
    }

    public void setBankBranch(int bankBranch){
        this.bankBranch = bankBranch;
    }

    public int save(){
        return saveAccount(this);
    }

    public int delete(){
        return deleteAccount(this);
    }

    private static int saveAccount(BankAccount bankAccount){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            tx = session.beginTransaction();
            if(bankAccount.getId() == -1) {
                bid = (Integer) session.save(bankAccount);
                bankAccount.setId(bid);
            }else{
                session.merge(bankAccount);
                bid = bankAccount.getId();
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

    private static int deleteAccount(BankAccount bankAccount){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            bid = bankAccount.getId();
            if(bid != -1) {
                tx = session.beginTransaction();
                session.delete(bankAccount);
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
