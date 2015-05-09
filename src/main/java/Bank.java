/**
 * Created by romba on 5/10/15.
 */
public class Bank {
    private int id=0;
    private String name;

    public Bank() {}

    public Bank(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }




    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
