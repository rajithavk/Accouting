/**
 * Created by romba on 5/10/15.
 */

import org.hibernate.SessionFactory;


public class Accounting {
    private static SessionFactory factory = HibernateUtil.getSessionFactory();

    public static void main(String args[]) {

//        Bank xx = new Bank("Bank of Ceylon");
//        System.out.println(xx.save());
//        System.out.println(xx.getName());
//        xx.setName("Sampath");
//        System.out.println(Bank.saveBank(xx));
//        System.out.println(xx.getName());
//        xx.delete();
//
//        BankBranch yy = new BankBranch("Kurunegala","Kurunegala","BOC6666");
//        yy.save();
//
//        BankBranch zz = new BankBranch("Colombo","Colombo","BOC1111");
//        zz.save();
//        zz.setCode("BOC0000");
//        zz.save();

        BankAccount ba = new BankAccount("765110",14,3);
        ba.save();
        ba.delete();


    }
}
