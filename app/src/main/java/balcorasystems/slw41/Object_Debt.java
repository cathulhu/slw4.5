package balcorasystems.slw41;

import java.util.ArrayList;
import java.util.Date;


public class Object_Debt
{
    public static ArrayList<Object_Loan> loanPortfolio = new ArrayList<>();
    public static ArrayList<Object_Repayment> repaymentPortfolio = new ArrayList<>();
    static Integer selectedRepaymentPlan=0;

    public static void addBlankLoan()
    {
        Object_Loan blankLoan = new Object_Loan();
        loanPortfolio.add(blankLoan);
    }

    public static void addFullLoan(double startingBal, double currentBal, double APY, String type, String servicer, String dispDate)
    {
        Object_Loan fullLoan = new Object_Loan();
        fullLoan.startingBalance=startingBal;
        fullLoan.currentBalance=currentBal;
        fullLoan.interestRate=APY;
        fullLoan.type=type;
        fullLoan.servicer=servicer;
        fullLoan.dispersementDate=dispDate;

        fullLoan.updateTraits();

        loanPortfolio.add(fullLoan);
    }

    public static void deleteLoan(int delTarget)
    {
        //notice that the this is int ^ not Integer, pay attention to functions whose arguments require a primitive, not an object type
        loanPortfolio.remove(delTarget);
    }



//    public static void addRepaymentWithSwitching(String type, ArrayList<Double> passedPayments, Double passedTotal, double passedForgiveness, double passedTax, ArrayList<Integer> passedSwitchIndictars, ArrayList<Integer> passedSwitchIDs)
//    {
//        Object_Repayment newRepayment = new Object_Repayment(passedPayments, passedTotal, passedForgiveness, passedTax, passedSwitchIndictars, passedSwitchIDs);
//        repaymentPortfolio.add(newRepayment);
//    }

    public static void addRepaymentNoSwitching(String type, ArrayList<Double> passedPayments, Double passedTotal, double passedForgiveness, double passedTax, Date repaymentDate)
    {
        Object_Repayment newRepayment = new Object_Repayment(type, passedPayments, passedTotal, passedForgiveness, passedTax, repaymentDate);
        repaymentPortfolio.add(newRepayment);
//        repaymentPortfolio.get(repaymentPortfolio.size()-1).coordinateInArray=repaymentPortfolio.size()-1;
    }

    public static void addStdRepayment(String type, ArrayList<Double> passedPayments, Double passedTotal, double passedForgiveness, double passedTax, Date repaymentDate)
    {
        Object_Repayment newRepayment = new Object_Repayment(type, passedPayments, passedTotal, passedForgiveness, passedTax, repaymentDate);
        repaymentPortfolio.add(newRepayment);
//        repaymentPortfolio.get(repaymentPortfolio.size()-1).coordinateInArray=repaymentPortfolio.size()-1;
    }
}
