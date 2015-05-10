import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by romba on 5/10/15.
 */
public class BankBranch{
    private int id = -1;
    private String branch;
    private String location;
    private String code;

    public BankBranch(){}

    public BankBranch(String branch, String location, String code){
        this.branch   = branch;
        this.location = location;
        this.code     = code;
    }

    public int getId(){
        return id;
    }
    public String getLocation(){
        return location;
    }
    public String getBranch(){
        return branch;
    }
    public String getCode(){
        return code;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setBranch(String branch){
        this.branch = branch;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setCode(String code){
            this.code = code;
    }

    public int save(){
        return saveBranch(this);
    }
    public int delete(){
        return deleteBranch(this);
    }

    private static int saveBranch(BankBranch branch){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            tx = session.beginTransaction();
            if(branch.getId()==-1){
                bid = (Integer) session.save(branch);
                branch.setId(bid);
            }else{
                session.merge(branch);
                bid = branch.getId();
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

    private static int deleteBranch(BankBranch branch){
    SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bid = null;
        try{
            bid = branch.getId();
            if(bid!=-1){
                tx = session.beginTransaction();
                session.delete(branch);
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
