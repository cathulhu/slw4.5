package balcorasystems.slw41;

import java.lang.annotation.Target;
import java.util.ArrayList;



public class Object_Debt
{
    public static ArrayList<Object_Loan> loanPortfolio = new ArrayList<>();
    public static ArrayList<Object_Repayment> repaymentPortfolio = new ArrayList<>();

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


    public static void addRepayment()
    {
        Object_Repayment newRepayment = new Object_Repayment();
        repaymentPortfolio.add(newRepayment);
    }
}
