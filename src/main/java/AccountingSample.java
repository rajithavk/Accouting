/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accountingsample;

import org.hibernate.SessionFactory;

/**
 *
 * @author UDARA
 */
public class AccountingSample {

    /**
     * @param args the command line arguments
     */
    
//    private static SessionFactory factory = HibernateUtil.getSessionFactory();
    
    public static void main(String[] args) {
        // TODO code application logic here
        Account cash = new Account(500.00, "permenant", "cash");
        Account drawing = new Account(300, "permenant" , "drawing");
        cash.save();
        drawing.save();
        
        DrawingTransaction buyBicycle = new DrawingTransaction(cash,drawing,100,"Money is drawn to buy a bicycle"); 
        buyBicycle.save();
        
        
        Account pendingGRN = new Account(300.00, "permenant", "Pending GRN account");
        Account stock = new Account(500.00, "permenant", "stock account");   
        
        GRNTransaction GRN = new GRNTransaction(pendingGRN,stock,100,"this is a goods return note for recieved goods"); 
        GRN.save();   
        
        GRNTransaction GRN2 = new GRNTransaction(pendingGRN,stock,100,"this is a goods return note2 for recieved goods 2"); 
        GRN2.save();        
        
    }
}


