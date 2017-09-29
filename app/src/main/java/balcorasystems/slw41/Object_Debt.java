package balcorasystems.slw41;

import java.lang.annotation.Target;
import java.util.ArrayList;



public class Object_Debt
{
    public static ArrayList<Object_Loan> loanPortfolio = new ArrayList<>();
    public static ArrayList<Object_Repayment> repaymentPortfolio = new ArrayList<>();
    static Integer selectedRepaymentPlan=0;

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



    public static void addRepaymentWithSwitching(String type, ArrayList<Double> passedPayments, Double passedTotal, double passedForgiveness, double passedTax, ArrayList<Integer> passedSwitchIndictars, ArrayList<Integer> passedSwitchIDs)
    {
        Object_Repayment newRepayment = new Object_Repayment(passedPayments, passedTotal, passedForgiveness, passedTax, passedSwitchIndictars, passedSwitchIDs);
        repaymentPortfolio.add(newRepayment);
    }

    public static void addRepaymentNoSwitching(String type, ArrayList<Double> passedPayments, Double passedTotal, double passedForgiveness, double passedTax)
    {
        Object_Repayment newRepayment = new Object_Repayment(type, passedPayments, passedTotal, passedForgiveness, passedTax);
        repaymentPortfolio.add(newRepayment);
        repaymentPortfolio.get(repaymentPortfolio.size()-1).coordinateInArray=repaymentPortfolio.size()-1;
    }
}
