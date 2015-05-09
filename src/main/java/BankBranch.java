/**
 * Created by romba on 5/10/15.
 */
public class BankBranch{
    private int id;
    private String branch;
    private String location;
    private String code;

    public BankBranch(){}

    public BankBranch(int id, String branch, String location, String code){
        this.id     = id;
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

}
