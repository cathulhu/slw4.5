package balcorasystems.slw41;


import java.util.ArrayList;

public class Calculations
{
    static Object_Loan targetLoan;

    //    public static Integer income =6;
//    public static Integer debt =6;
//    public static double IBRPayment =0.66;
//    public static double StandardPayment =0.66;
//    public static double Savings =0.66;
    static double defaultMonthlyInterestRate = (0.0470 / 12);

    Calculations(Object_Loan passedLoan)
    {
        targetLoan = passedLoan;
        targetLoan.debt=MainActivity.simpleDebt;
        targetLoan.income=MainActivity.simpleIncome;
    }


    public static Double simpleStandardRepaymentCalc(double passedDebt) {
        double repaymentTermMonhts = 120;

        double paymentCalcNumerator = defaultMonthlyInterestRate * passedDebt;
        double paymentCalcDenominator = 1 - Math.pow(1 + defaultMonthlyInterestRate, -repaymentTermMonhts);
        double fixedPayment = paymentCalcNumerator / paymentCalcDenominator;
        MainActivity.currentStdPayment = fixedPayment;

        return fixedPayment;
    }


    public ArrayList<Double> standardIBRCalc() {

        ArrayList<Double> payments = new ArrayList<>();
        double povertyLevelsingle48states2017 = 12060;
        double annualRaise = 1.05;         //assuming 1.5% per year income, average is 2.5 with most unlucky getting 1%, so a conservative estimate.
        double annualPovertyInflation = 1.02;      //assuming 2% inflation per year, historical average is higher but in recent years it's been about 1.5% so this should be safe.
        int repaymentYear = 0;
        int repaymentYearLimit = 25;
        double runningIncome = targetLoan.income;
        double runningDebt = targetLoan.debt;
        double runningInterest = 0;
        double totalSpent = 0;
        double forgiveness = 0;
        double totalUnpaidInterest = 0;
        double totalAccruedInterest = 0;
        double financialHardshipLine = povertyLevelsingle48states2017 * 1.5;
        double discretionaryIncome = runningIncome - financialHardshipLine;
        double ibrPayment;
        double netChange=0;
        boolean financialHardship= true;
        double standardPayment = simpleStandardRepaymentCalc(runningDebt);
        double monthlyAccruedInterest;
        double forgivenessTaxDue;


        while (runningDebt > 0 && repaymentYear < repaymentYearLimit)     //built in edge case detection if IBR gets more expensive than standard
        {
            discretionaryIncome = runningIncome - financialHardshipLine;


            for (int m = 0; m < 12 && runningDebt > 0; m++)
            {
                ibrPayment = (discretionaryIncome * 0.15) / 12;

                if (ibrPayment > standardPayment && financialHardship==true)
                {
                    ibrPayment=standardPayment;
                    financialHardship=true;
                    //not sure if losing PFH just puts you at the standard repayment level, so doesn't increase the time you're repaying.

                }

                if (ibrPayment < 0)
                {
                    ibrPayment = 0;
                }

                if (financialHardship==false)
                {
                    ibrPayment = standardPayment;
                }

                monthlyAccruedInterest = defaultMonthlyInterestRate * runningDebt;
                netChange = ibrPayment - monthlyAccruedInterest;

                //monthly interest section

                //negative amortization payment for subsidized loans. For now just assume these loans are subsidized, later implement a check.
                if (financialHardship==true && repaymentYear < 3 && netChange < 0)
                {
                    netChange=0;
                }

                if (netChange < 0)
                {
                    totalUnpaidInterest += (netChange*-1);
                }

                if (totalUnpaidInterest < 0)
                {
                    totalUnpaidInterest=0;
                }

                runningDebt -= netChange;

                if (runningDebt <= 0)
                {
                    //so the last payment should be just what is required to zero out balance
                    ibrPayment=ibrPayment+runningDebt;
                    runningDebt=0;
                }

                //not currently used for anything just a statistic measurement
                totalAccruedInterest += monthlyAccruedInterest - ibrPayment;
                if (totalAccruedInterest < 0)
                {
                    totalAccruedInterest = 0;
                }

                totalSpent += ibrPayment;
                payments.add(ibrPayment);
            }

            povertyLevelsingle48states2017=povertyLevelsingle48states2017*annualPovertyInflation;
            runningIncome = runningIncome * annualRaise;

            repaymentYear++;
        }

        forgiveness = runningDebt;
        MainActivity.simpleForgiveness.add(forgiveness);

        //adding extra blank value as hacky way to avoid crashing on the other plans that don't have forgiveness for now
//        forgivenessTaxDue=taxCalc(runningIncome, forgiveness)


        MainActivity.payments = payments;
        return payments;
    }











    public ArrayList<Double> opportunisticIBRCalc() {

        ArrayList<Double> payments = new ArrayList<>();
        double povertyLevelsingle48states2017 = 12060;
        double annualRaise = 1.05;         //assuming 1.5% per year income, average is 2.5 with most unlucky getting 1%, so a conservative estimate.
        double annualPovertyInflation = 1.02;      //assuming 2% inflation per year, historical average is higher but in recent years it's been about 1.5% so this should be safe.
        int repaymentYear = 0;
        int repaymentYearLimit = 25;
        double runningIncome = targetLoan.income;
        double runningDebt = targetLoan.debt;
        double runningInterest = 0;
        double totalSpent = 0;
        double forgiveness = 0;
        double totalUnpaidInterest = 0;
        double totalAccruedInterest = 0;
        double financialHardshipLine = povertyLevelsingle48states2017 * 1.5;
        double discretionaryIncome = runningIncome - financialHardshipLine;
        double ibrPayment;
        double runningStandardPayment;
        boolean financialHardship= true;
        boolean oportunisticSwitch=false;
        double storedOportunisticValue=0;
        double originalStandardPayment = simpleStandardRepaymentCalc(runningDebt);
        double monthlyAccruedInterest;
        double netChange=0;


        while (runningDebt > 0 && repaymentYear < repaymentYearLimit)     //built in edge case detection if IBR gets more expensive than standard
        {
            discretionaryIncome = runningIncome - financialHardshipLine;


            for (int m = 0; m < 12 && runningDebt > 0; m++)
            {
                ibrPayment = (discretionaryIncome * 0.15) / 12;

                if (ibrPayment > originalStandardPayment && financialHardship==true)  //hardship must be true so this can only be triggered once
                {
                    ibrPayment=originalStandardPayment;
                    financialHardship=false;
                    //the opportunistic switch means repayment isn't really limited, its just 120 more payments to whenever it was triggered
                    repaymentYearLimit =9999;
                }




                //opportunistic part, as soon as IBR equals or exceeds standard calculated at any time, not just begining, switch to standard
                runningStandardPayment=simpleStandardRepaymentCalc(runningDebt);
                if (ibrPayment >= runningStandardPayment && oportunisticSwitch==false)  //&& PFH is false? this depends on if PFH kicks you out or not of IBR
                {
                    storedOportunisticValue=runningStandardPayment;
                    ibrPayment=runningStandardPayment;
                    oportunisticSwitch=true;
                }

                if (oportunisticSwitch==true)
                {
                    ibrPayment=storedOportunisticValue;
                }




                if (ibrPayment < 0)
                {
                    ibrPayment = 0;
                }

                monthlyAccruedInterest = defaultMonthlyInterestRate * runningDebt;
                netChange = ibrPayment - monthlyAccruedInterest;

                //monthly interest section

                //negative amortization payment for subsidized loans. For now just assume these loans are subsidized, later implement a check.
                if (financialHardship==true && repaymentYear < 3 && netChange < 0)
                {
                    netChange=0;
                }

                if (netChange < 0)
                {
                    totalUnpaidInterest += (netChange*-1);
                }

                if (totalUnpaidInterest < 0)
                {
                    totalUnpaidInterest=0;
                }

                runningDebt -= netChange;

                if (runningDebt <= 0)
                {
                    //so the last payment should be just what is required to zero out balance
                    ibrPayment=ibrPayment+runningDebt;
                    runningDebt=0;
                }

                //not currently used for anything just a statistic measurement
                totalAccruedInterest += monthlyAccruedInterest - ibrPayment;
                if (totalAccruedInterest < 0)
                {
                    totalAccruedInterest = 0;
                }

                totalSpent += ibrPayment;
                payments.add(ibrPayment);
            }

            povertyLevelsingle48states2017=povertyLevelsingle48states2017*annualPovertyInflation;
            runningIncome = runningIncome * annualRaise;

            repaymentYear++;
        }

        forgiveness=runningDebt;

        if (oportunisticSwitch==true)
        {
            MainActivity.simpleForgiveness.add(0.0);
        }
        else
        {
            MainActivity.simpleForgiveness.add(forgiveness);
        }

        MainActivity.payments = payments;
        return payments;
    }


}
