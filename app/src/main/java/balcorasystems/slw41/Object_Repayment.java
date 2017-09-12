package balcorasystems.slw41;


import java.util.ArrayList;

public class Object_Repayment
{
    ArrayList<String>repaymentPlanNames;

    //constructor without any plan switching
    public Object_Repayment(ArrayList<Double> passedPayments, Double passedTotal, double passedForgiveness, double passedTax)
    {
        this.monthlyPayments=passedPayments;
        this.repaymentTotal=passedTotal;
        this.forgivnessTotal=passedForgiveness;
        this.taxTotal=passedTax;

        calculateMeanMonthlyPayment();
    }

    //constructor with plan switching
    public Object_Repayment(ArrayList<Double> passedPayments, Double passedTotal, double passedForgiveness, double passedTax, ArrayList<Integer> passedSwitchIndictars, ArrayList<Integer> passedSwitchIDs)
    {
        this.monthlyPayments=passedPayments;
        this.repaymentTotal=passedTotal;
        this.forgivnessTotal=passedForgiveness;
        this.taxTotal=passedTax;

        this.monthlyPlanSwitchIndicators=passedSwitchIndictars;
        this.monthlyPlanSwitchIDs=passedSwitchIDs;

        for (Integer x:monthlyPlanSwitchIDs)
        {
            switchedPlanTypes.add(repaymentPlanNames.get(x));
        }

        repaymentPlanNames.add("Standard");
        repaymentPlanNames.add("15% IBR - Old");
        repaymentPlanNames.add("10% IBR - New");
        repaymentPlanNames.add("15% PAYE");
        repaymentPlanNames.add("10% REPAYE");
        repaymentPlanNames.add("20% ICR");

        calculateMeanMonthlyPayment();;

    }

    public void calculateMeanMonthlyPayment()
    {
        meanMonthlyRepayment=repaymentTotal/monthlyPayments.size();
    }

    public ArrayList<Double> monthlyPayments = new ArrayList<>();
    public Double repaymentTotal;
    public Double forgivnessTotal;
    public Double taxTotal;
    public Double meanMonthlyRepayment;
    public ArrayList<Integer> monthlyPlanSwitchIndicators = new ArrayList<>();
    public ArrayList<Integer> monthlyPlanSwitchIDs = new ArrayList<>();
    public ArrayList<String> switchedPlanTypes = new ArrayList<>();
}
