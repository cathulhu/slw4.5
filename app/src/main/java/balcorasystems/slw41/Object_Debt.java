package balcorasystems.slw41;

import java.lang.annotation.Target;
import java.util.ArrayList;



public class Object_Debt
{
    public static ArrayList<Object_Loan> loanPortfolio = new ArrayList<>();
    public static ArrayList<Long> projectedMonthlyPayments;
    public static ArrayList<Long> actualMonthlyPayments;
    public static ArrayList<Double> loanRepaymentDates;     //not sure if I'll need a matrix of all the dates but probably will, need some kind of function to populate this
                                                            //maybe I'll just use calendar instead of unix time codes...

    public static void addBlankLoan()
    {
        Object_Loan newLoan = new Object_Loan();
        loanPortfolio.add(newLoan);
    }

    public static void deleteLoan(int delTarget)
    {
        //notice that the this is int ^ not Integer, pay attention to functions whose arguments require a primitive, not an object type
        loanPortfolio.remove(delTarget);
    }
}
