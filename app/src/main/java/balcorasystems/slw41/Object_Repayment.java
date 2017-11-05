package balcorasystems.slw41;


import java.util.ArrayList;
import java.util.Date;

public class Object_Repayment
{
    ArrayList<String>repaymentPlanNames;

    public Object_Repayment(String type, ArrayList<Double> passedPayments, Double passedTotal, double passedForgiveness, double passedTax, Date startDate)
    {
        //version for std repayment
        this.monthlyPayments=passedPayments;
        this.repaymentTotal=passedTotal;
        this.forgivnessTotal=passedForgiveness;
        this.taxTotal=passedTax;
        this.type=type;
        this.repaymentStartDate=startDate;

        calculateMeanMonthlyPayment();
    }

    public void addRepaymentActions (Logic_RepaymentActions actions)
    {
        repaymentActions=actions;
    }

    //constructor with plan switching
//    public Object_Repayment(ArrayList<Double> passedPayments, Double passedTotal, double passedForgiveness, double passedTax, ArrayList<Integer> passedSwitchIndictars, ArrayList<Integer> passedSwitchIDs)
//    {
//        this.monthlyPayments=passedPayments;
//        this.repaymentTotal=passedTotal;
//        this.forgivnessTotal=passedForgiveness;
//        this.taxTotal=passedTax;
//
//        this.monthlyPlanSwitchIndicators=passedSwitchIndictars;
//        this.monthlyPlanSwitchIDs=passedSwitchIDs;
//
//        for (Integer x:monthlyPlanSwitchIDs)
//        {
//            switchedPlanTypes.add(repaymentPlanNames.get(x));
//        }
//
//        repaymentPlanNames.add("Standard");
//        repaymentPlanNames.add("15% IBR - Old");
//        repaymentPlanNames.add("10% IBR - New");
//        repaymentPlanNames.add("15% PAYE");
//        repaymentPlanNames.add("10% REPAYE");
//        repaymentPlanNames.add("20% ICR");
//
//        calculateMeanMonthlyPayment();
//
//    }

    public void calculateMeanMonthlyPayment()
    {
        meanMonthlyRepayment=repaymentTotal/monthlyPayments.size();
    }

    Date repaymentStartDate;
    public ArrayList<Double> monthlyPayments = new ArrayList<>();
    public String type = "";
    public Integer coordinateInArray=0;
    public Double repaymentTotal;
    public Double forgivnessTotal;
    public Double taxTotal;
    public Double meanMonthlyRepayment;
    public Logic_RepaymentActions repaymentActions;
}
