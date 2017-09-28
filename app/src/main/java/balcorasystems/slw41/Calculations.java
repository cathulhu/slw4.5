package balcorasystems.slw41;


import java.util.ArrayList;

public class Calculations
{
    static Object_Borrower targetBorrower;

    Calculations(Object_Borrower passedBorrower)
    {
        targetBorrower = passedBorrower;

    }



    public void StandardRepayCalc()
    {
        ArrayList<Double> stdPayments = new ArrayList<>();
        Double totalPayed=0.0;
        Double individualPayment= 0.0;
        Double totalMonthlyPayment= 0.0;

        for (Object_Loan loan : targetBorrower.debtAndRepaymentObject.loanPortfolio)
        {
            ArrayList<Double> payments= new ArrayList<>();
            double monthlyInterestRate=loan.interestRate/100/12;
            double paymentCalcNumerator = monthlyInterestRate * loan.currentBalance;    //change this to current balance soon and start using that value in other things
            double paymentCalcDenominator = 1 - Math.pow(1 + (monthlyInterestRate), -120);
            individualPayment = paymentCalcNumerator / paymentCalcDenominator;

            totalPayed += individualPayment*120;
            totalMonthlyPayment += individualPayment;
        }

        for (int i = 0; i < 120; i++)
        {
            stdPayments.add(totalMonthlyPayment);
        }

        //passing 0.0 for forgiveness and 0.0 for taxes
        targetBorrower.debtAndRepaymentObject.addRepaymentNoSwitching("Standard", stdPayments, totalPayed, 0.0, 0.0);

        //updating the master borrower object with this data so I don't have to pass one back.
        MainActivity.masterBorrower=targetBorrower;
    }


    public static Double Standard12QuickCheck()
    {
        ArrayList<Double> stdPayments = new ArrayList<>();
        Double totalPayed=0.0;
        Double individualPayment= 0.0;
        Double totalMonthlyPayment= 0.0;

        for (Object_Loan loan : targetBorrower.debtAndRepaymentObject.loanPortfolio)
        {
            ArrayList<Double> payments= new ArrayList<>();
            double monthlyInterestRate=loan.interestRate/100/12;
            double paymentCalcNumerator = monthlyInterestRate * loan.currentBalance;    //change this to current balance soon and start using that value in other things
            double paymentCalcDenominator = 1 - Math.pow(1 + (monthlyInterestRate), -144);
            individualPayment = paymentCalcNumerator / paymentCalcDenominator;

            totalPayed += individualPayment*144;
            totalMonthlyPayment += individualPayment;
        }

        return individualPayment;
    }

    public static Double Standard10QuickCheck()
    {
        ArrayList<Double> stdPayments = new ArrayList<>();
        Double totalPayed=0.0;
        Double individualPayment= 0.0;
        Double totalMonthlyPayment= 0.0;

        for (Object_Loan loan : targetBorrower.debtAndRepaymentObject.loanPortfolio)
        {
            ArrayList<Double> payments= new ArrayList<>();
            double monthlyInterestRate=loan.interestRate/100/12;
            double paymentCalcNumerator = monthlyInterestRate * loan.currentBalance;    //change this to current balance soon and start using that value in other things
            double paymentCalcDenominator = 1 - Math.pow(1 + (monthlyInterestRate), -120);
            individualPayment = paymentCalcNumerator / paymentCalcDenominator;

            totalPayed += individualPayment*120;
            totalMonthlyPayment += individualPayment;
        }

        return individualPayment;
    }






    public ArrayList<String> variableRepaymentCalc(Object_Borrower passedBorrower, Object_Loan passedLoan, Integer repaymentMonth, String pfhStatus, Double discretionaryIncome) {
        Double percent = 0D;

        Boolean iCR20 = false;
        Boolean iBR15 = false;
        Boolean iBR10 = false;
        Boolean repaye10 = false;
        Boolean paye10 = false;
        Boolean std12 = false;

        ArrayList<Double> variablePayments = new ArrayList<>();
        ArrayList<Double> stdPayments = new ArrayList<>();
        ArrayList<Double> comparisonResults = new ArrayList<>();
        ArrayList<String> repaymentPlanNames = new ArrayList<>();
        repaymentPlanNames.add("ICR");
        repaymentPlanNames.add("OLDIBR");
        repaymentPlanNames.add("NEWIBR");
        repaymentPlanNames.add("REPAYE");
        repaymentPlanNames.add("PAYE");

        Double std10yearOriginalPayment = passedBorrower.debtAndRepaymentObject.repaymentPortfolio.get(0).monthlyPayments.get(0);
        Double std12yearPayment = Calculations.Standard12QuickCheck();
        //TODO: soon setup 12 std repayment so its just calculated once at the begining of repayment selection, probably triggered in standard calc

        Double disc20percentPayment = discretionaryIncome/12*0.20;
        Double disc15percentPayment = discretionaryIncome/12*0.15;
        Double disc10percentPayment = discretionaryIncome/12*0.10;


        Boolean stdCap = false;
        Integer timeframe = 0;


        //ICR Check
        if (passedLoan.type.equals("Direct Unsubsidized Consolidation Loan") || passedLoan.type.equals("Direct Subsidized Consolidation Loan")
                || passedLoan.type.equals("Direct Unsubsidized Loan") || passedLoan.type.equals("Direct Subsidized Loan")
                || passedLoan.type.equals("Direct PLUS Loan for Graduate/Professional Students") || passedLoan.type.equals("Direct PLUS Consolidation Loan") )
        {

            variablePayments.add(disc20percentPayment);
            stdPayments.add(std12yearPayment);

            iCR20 = true;

            //ICR lesser of 20% or 12 year std repayment
            //no cap on disc payment
        }
        else
        {
            variablePayments.add(-1D);
            stdPayments.add(-1D);
        }


        //OLD IBR CHECK
        if ( ( passedLoan.type.equals("Direct Unsubsidized Consolidation Loan") || passedLoan.type.equals("Direct Subsidized Consolidation Loan")
                || passedLoan.type.equals("Direct Unsubsidized Loan") || passedLoan.type.equals("Direct Subsidized Loan")
                || passedLoan.type.equals("Direct PLUS Loan for Graduate/Professional Students") || passedLoan.type.equals("Direct PLUS Consolidation Loan")
                || passedLoan.type.equals("FFEL PLUS Loan for Graduate/Professional Students") || passedLoan.type.equals("FFEL Consolidation Loan")
                || passedLoan.type.equals("Subsidized Federal Stafford Loan") || passedLoan.type.equals("Unsubsidized Federal Stafford Loan") )
                && ( pfhStatus.equals("15% PFH") || pfhStatus.equals("10% PFH") )
                )
        {
            //IBR Capped at amount of original std payment

                variablePayments.add(disc15percentPayment);
                stdPayments.add(std10yearOriginalPayment);
                //new ibr
                iBR15=true;


        }
        else
        {
            variablePayments.add(-1D);
            stdPayments.add(-1D);
        }

        //New IBR CHECK
        if ( ( passedLoan.type.equals("Direct Unsubsidized Consolidation Loan") || passedLoan.type.equals("Direct Subsidized Consolidation Loan")
                || passedLoan.type.equals("Direct Unsubsidized Loan") || passedLoan.type.equals("Direct Subsidized Loan")
                || passedLoan.type.equals("Direct PLUS Loan for Graduate/Professional Students") || passedLoan.type.equals("Direct PLUS Consolidation Loan")
                || passedLoan.type.equals("FFEL PLUS Loan for Graduate/Professional Students") || passedLoan.type.equals("FFEL Consolidation Loan")
                || passedLoan.type.equals("Subsidized Federal Stafford Loan") || passedLoan.type.equals("Unsubsidized Federal Stafford Loan") )
                && pfhStatus.equals("10% PFH")
                && passedBorrower.timeAfter14
                )
        {

                variablePayments.add(disc10percentPayment);
                stdPayments.add(std10yearOriginalPayment);
                iBR10=true;
                //old ibr

        }
        else
        {
            variablePayments.add(-1D);
            stdPayments.add(-1D);
        }




        //Repaye check
        if (passedLoan.type.equals("Direct Unsubsidized Consolidation Loan") || passedLoan.type.equals("Direct Subsidized Consolidation Loan")
                || passedLoan.type.equals("Direct Unsubsidized Loan") || passedLoan.type.equals("Direct Subsidized Loan")
                || passedLoan.type.equals("Direct PLUS Loan for Graduate/Professional Students") || passedLoan.type.equals("Direct PLUS Consolidation Loan")
                )
        {
            variablePayments.add(disc10percentPayment);
            stdPayments.add(-1D);
            repaye10=true;
            //REPAYE 10% of income, no cap, 20 years undergrad, 25 grad
        }
        else
        {
            variablePayments.add(-1D);
            stdPayments.add(-1D);
        }

        //PAYE check
        if ( (passedBorrower.timeAfter14 || passedBorrower.timeBetween11to14) && pfhStatus.equals("10% PFH") &&
                ( passedLoan.type.equals("Direct Unsubsidized Consolidation Loan") || passedLoan.type.equals("Direct Subsidized Consolidation Loan")
                || passedLoan.type.equals("Direct Unsubsidized Loan") || passedLoan.type.equals("Direct Subsidized Loan")
                || passedLoan.type.equals("Direct PLUS Loan for Graduate/Professional Students") || passedLoan.type.equals("Direct PLUS Consolidation Loan") )
                )
        {
            //PAYE 10% capped at std
                    variablePayments.add(disc10percentPayment);
                    stdPayments.add(-1D);
            paye10=true;

        }
        else
        {
            variablePayments.add(-1D);
            stdPayments.add(-1D);
        }

        for (int i = 0; i < variablePayments.size(); i++)
        {
            Double variablePayment = variablePayments.get(i);
            Double stdPaymentCap = stdPayments.get(i);

            if (variablePayment < stdPaymentCap && variablePayment > 0)
            {
                comparisonResults.add(variablePayment);
            }
            else if (stdPaymentCap < variablePayment && stdPaymentCap > 0)
            {
                comparisonResults.add(stdPaymentCap);
            }
            else if (stdPaymentCap < 0)
            {
                comparisonResults.add(variablePayment);
            }
        }

        //sorting comparison results by lowest, could do other sorts but this seems like good place to start

        Double lowest=comparisonResults.get(0);
        Integer lowestID=0;


        for (int i = 0; i < comparisonResults.size(); i++)
        {

            if (comparisonResults.get(i) >= 0)
            {
                if (comparisonResults.get(i) < lowest)
                {
                    lowest=comparisonResults.get(i);
                    lowestID=i;

                }
            }
        }

        String lowestType=repaymentPlanNames.get(lowestID);

        ArrayList<String> results = new ArrayList<>();
        results.add(lowestType);
        results.add(String.valueOf(lowest));

        return results;
    }



    public Double DiscIncomeCalc(Integer repaymentMonth, Double currentIncome)
    {
        double povertyLevelsingle48states2017 = 12060;  //will adjust and make this an array with all the propper values later
        double financialHardshipLine = povertyLevelsingle48states2017 * 1.5;
        double discretionaryIncome = currentIncome - financialHardshipLine;

        return discretionaryIncome;
    }



    public String financialHardshipCalc(double discretionaryIncome, Object_Borrower targetBorrower)
    {
        //perhaps later throw in a check to make sure std calc has actually been run first

        Integer coordinatesOfStdRepayment=0;
        String hardshipResult;

        for (Object_Repayment x: targetBorrower.debtAndRepaymentObject.repaymentPortfolio)
        {
            if (x.type.equals("Standard"))
            {
                coordinatesOfStdRepayment=x.coordinateInArray;
            }
        }

        Double stdPayment= targetBorrower.debtAndRepaymentObject.repaymentPortfolio.get(coordinatesOfStdRepayment).monthlyPayments.get(0);
        Double discretionaryIncomeMonthly = discretionaryIncome/12;

        if (stdPayment > discretionaryIncomeMonthly*0.15)
        {
            //15% PFH true
            hardshipResult="15% PFH";
        }
        else if (stdPayment > discretionaryIncomeMonthly*0.10)
        {
            //10% PFH true
            hardshipResult="10% PFH";
        }
        else
        {
            //No PFH
            hardshipResult="No PFH";
        }

        return hardshipResult;

    }



    public void lowestCalc()
    {
        ArrayList<Double> totalPayments = new ArrayList<>();
        ArrayList<ArrayList> individualPayments = new ArrayList<>();
        double forgiveness=0;
        double statTotalSpent = 0;

        for (Object_Loan loan : targetBorrower.debtAndRepaymentObject.loanPortfolio)
        {
            ArrayList<Double> perLoanRepayments = new ArrayList<>();
            ArrayList<String> perloanRepaymentTypes = new ArrayList<>();
            ArrayList<String>monthlyPaymentCalc = new ArrayList();
            Double lowestVariablePayment;
            Integer repaymentMonth = 0 ;
            Integer repaymentMonthLimit = 300;  //add functionality to change this for different repayment types
            double startingStandardRepayment=Calculations.Standard10QuickCheck();
            double monthlyInterestRate=loan.interestRate/100/12;
            double monthlyAccruedInterest;
            double netChange;
            double startingIncome=targetBorrower.startingPrimaryIncome;    //might not need this, prolly could just do running income
            double runningIncome=startingIncome;
            double discretionaryIncome=1;
            double idrPayment;
            double statAccruedUnpaidInterst = 0;
            double loanRunningIndividualBalance=loan.currentBalance;
            String pfhStatus;


            while (loanRunningIndividualBalance > 0 && repaymentMonth < repaymentMonthLimit)
            {
                //stages each month: check which IDR best eligible for, calculate IDR payment, calculate interest, update income, process payment
                discretionaryIncome=DiscIncomeCalc(repaymentMonth, runningIncome);
                pfhStatus = financialHardshipCalc(discretionaryIncome, targetBorrower);
                ArrayList<String> results = variableRepaymentCalc(targetBorrower, loan, repaymentMonth, pfhStatus, discretionaryIncome);

                idrPayment = Double.valueOf(results.get(1));

                perloanRepaymentTypes.add( results.get(0) );
                perLoanRepayments.add( Double.valueOf(results.get(1)));

                monthlyAccruedInterest = monthlyInterestRate * loanRunningIndividualBalance;
                //put check here if subsidized, if so and < 36 months interest = 0

                netChange = idrPayment - monthlyAccruedInterest;

                if (netChange > 0)
                {
                    if (loanRunningIndividualBalance - netChange <= 0)
                    {
                        //condition for if payment exceeds balance of loan, IE for calculating the final payment
                        idrPayment = idrPayment + (loanRunningIndividualBalance - netChange);   // <-- plus a negative should reduce idrPayment to whatever required to zero out balance
                        //I think this is correct, delte this when I've verified
                        loanRunningIndividualBalance=0;
                    }
                    else
                    {
                        //condition for typical payment
                        loanRunningIndividualBalance -= (long) netChange;
                    }
                }
                else
                {
                    //condition where generated interest is greater than payment
                    netChange= netChange*-1;    //converting to positive value so I can add this balance
                    loanRunningIndividualBalance += (long) netChange;
                    statAccruedUnpaidInterst += (long) netChange;     //calculating how much unpaid interest accrues
                }
                statTotalSpent += idrPayment;
                loan.currentBalance=loanRunningIndividualBalance;

                if (repaymentMonth > totalPayments.size()-1)    //double check that this logic works the way you think it does
                {
                    totalPayments.add(idrPayment);
                }
                else
                {
                    totalPayments.set(repaymentMonth, totalPayments.get(repaymentMonth)+idrPayment);
                }
                repaymentMonth++;

                runningIncome=targetBorrower.primaryIncomeOverTime.get(repaymentMonth);
                targetBorrower.primaryCurrentIncome=runningIncome;  //might not actually NEED to set this but eh for now
            }
            individualPayments.add(perLoanRepayments);

            //final loan forgiveness sorting out
            if (loanRunningIndividualBalance > 0)
            {
                forgiveness += loanRunningIndividualBalance;
            }

            loan.forgiveness=forgiveness;
            loan.currentBalance=(long) 0;
        }

        targetBorrower.debtAndRepaymentObject.addRepaymentNoSwitching("Variable", totalPayments, statTotalSpent, forgiveness, 0);

    }



    // Weighted average actually not used here but perhaps keep this code around for if I need to use that, say for loan consolidation for instance where I know this is used
//        Double totalLoanWeight = 0.0;
//        Double weightedAverageInterestRate = 0.0;
//
//        for (Object_Loan loan : targetBorrower.debtAndRepaymentObject.loanPortfolio)
//        {
//            totalLoanWeight += loan.interestRate * loan.startingBalance;
//        }
//
//        weightedAverageInterestRate = ( (totalCurrentDebt / totalLoanWeight) / 10000 ) /12;
//        //should add a round up to the nearest 1/8th just to be totally correct





//    public ArrayList<Double> standardIBRCalc() {
//
//        ArrayList<Double> payments = new ArrayList<>();
//        double povertyLevelsingle48states2017 = 12060;
//        double annualRaise = 1.05;         //assuming 1.5% per year income, average is 2.5 with most unlucky getting 1%, so a conservative estimate.
//        double annualPovertyInflation = 1.02;      //assuming 2% inflation per year, historical average is higher but in recent years it's been about 1.5% so this should be safe.
//        int repaymentYear = 0;
//        int repaymentYearLimit = 25;
//        double runningIncome = targetBorrower.startingIncome;
//        double runningDebt = totalCurrentDebt;
//        double runningInterest = 0;
//        double totalSpent = 0;
//        double forgiveness = 0;
//        double totalUnpaidInterest = 0;
//        double totalAccruedInterest = 0;
//        double financialHardshipLine = povertyLevelsingle48states2017 * 1.5;
//        double discretionaryIncome = runningIncome - financialHardshipLine;
//        double ibrPayment;
//        double netChange=0;
//        boolean financialHardship= true;
//
//        double standardPayment = simpleStandardRepaymentCalc(runningDebt);
//
//        double monthlyAccruedInterest;
//
//        double forgivenessTaxDue;
//
//
//        while (runningDebt > 0 && repaymentYear < repaymentYearLimit)     //built in edge case detection if IBR gets more expensive than standard
//        {
//            discretionaryIncome = runningIncome - financialHardshipLine;
//
//
//            for (int m = 0; m < 12 && runningDebt > 0; m++)
//            {
//                ibrPayment = (discretionaryIncome * 0.15) / 12;
//
//                if (ibrPayment > standardPayment && financialHardship==true)
//                {
//                    ibrPayment=standardPayment;
//                    financialHardship=true;
//                    //not sure if losing PFH just puts you at the standard repayment level, so doesn't increase the time you're repaying.
//
//                }
//
//                if (ibrPayment < 0)
//                {
//                    ibrPayment = 0;
//                }
//
//                if (financialHardship==false)
//                {
//                    ibrPayment = standardPayment;
//                }
//
//                monthlyAccruedInterest = defaultMonthlyInterestRate * runningDebt;
//                netChange = ibrPayment - monthlyAccruedInterest;
//
//                //monthly interest section
//
//                //negative amortization payment for subsidized loans. For now just assume these loans are subsidized, later implement a check.
//                if (financialHardship==true && repaymentYear < 3 && netChange < 0)
//                {
//                    netChange=0;
//                }
//
//                if (netChange < 0)
//                {
//                    totalUnpaidInterest += (netChange*-1);
//                }
//
//                if (totalUnpaidInterest < 0)
//                {
//                    totalUnpaidInterest=0;
//                }
//
//                runningDebt -= netChange;
//
//                if (runningDebt <= 0)
//                {
//                    //so the last payment should be just what is required to zero out balance
//                    ibrPayment=ibrPayment+runningDebt;
//                    runningDebt=0;
//                }
//
//                //not currently used for anything just a statistic measurement
//                totalAccruedInterest += monthlyAccruedInterest - ibrPayment;
//                if (totalAccruedInterest < 0)
//                {
//                    totalAccruedInterest = 0;
//                }
//
//                totalSpent += ibrPayment;
//                payments.add(ibrPayment);
//            }
//
//            povertyLevelsingle48states2017=povertyLevelsingle48states2017*annualPovertyInflation;
//            runningIncome = runningIncome * annualRaise;
//
//            repaymentYear++;
//        }
//
//        forgiveness = runningDebt;
//        MainActivity.simpleForgiveness.add(forgiveness);
//
//        //adding extra blank value as hacky way to avoid crashing on the other plans that don't have forgiveness for now
////        forgivenessTaxDue=taxCalc(runningIncome, forgiveness)
//
//
//        MainActivity.payments = payments;
//
//        MainActivity.noPaymentDataYet =false;
//        return payments;
//    }











//    public ArrayList<Double> opportunisticIBRCalc() {
//
//        ArrayList<Double> payments = new ArrayList<>();
//        double povertyLevelsingle48states2017 = 12060;
//        double annualRaise = 1.05;         //assuming 1.5% per year income, average is 2.5 with most unlucky getting 1%, so a conservative estimate.
//        double annualPovertyInflation = 1.02;      //assuming 2% inflation per year, historical average is higher but in recent years it's been about 1.5% so this should be safe.
//        int repaymentYear = 0;
//        int repaymentYearLimit = 25;
//        double runningIncome = targetBorrower.startingIncome;
//        double runningDebt = totalCurrentDebt;
//        double runningInterest = 0;
//        double totalSpent = 0;
//        double forgiveness = 0;
//        double totalUnpaidInterest = 0;
//        double totalAccruedInterest = 0;
//        double financialHardshipLine = povertyLevelsingle48states2017 * 1.5;
//        double discretionaryIncome = runningIncome - financialHardshipLine;
//        double ibrPayment;
//        double runningStandardPayment;
//        boolean financialHardship= true;
//        boolean oportunisticSwitch=false;
//        double storedOportunisticValue=0;
//        double originalStandardPayment = simpleStandardRepaymentCalc(runningDebt);
//        double monthlyAccruedInterest;
//        double netChange=0;
//
//
//        while (runningDebt > 0 && repaymentYear < repaymentYearLimit)     //built in edge case detection if IBR gets more expensive than standard
//        {
//            discretionaryIncome = runningIncome - financialHardshipLine;
//
//
//            for (int m = 0; m < 12 && runningDebt > 0; m++)
//            {
//                ibrPayment = (discretionaryIncome * 0.15) / 12;
//
//                if (ibrPayment > originalStandardPayment && financialHardship==true)  //hardship must be true so this can only be triggered once
//                {
//                    ibrPayment=originalStandardPayment;
//                    financialHardship=false;
//                    //the opportunistic switch means repayment isn't really limited, its just 120 more payments to whenever it was triggered
//                    repaymentYearLimit =9999;
//                }
//
//
//
//
//                //opportunistic part, as soon as IBR equals or exceeds standard calculated at any time, not just begining, switch to standard
//                runningStandardPayment=simpleStandardRepaymentCalc(runningDebt);
//                if (ibrPayment >= runningStandardPayment && oportunisticSwitch==false)  //&& PFH is false? this depends on if PFH kicks you out or not of IBR
//                {
//                    storedOportunisticValue=runningStandardPayment;
//                    ibrPayment=runningStandardPayment;
//                    oportunisticSwitch=true;
//                }
//
//                if (oportunisticSwitch==true)
//                {
//                    ibrPayment=storedOportunisticValue;
//                }
//
//
//
//
//                if (ibrPayment < 0)
//                {
//                    ibrPayment = 0;
//                }
//
//                monthlyAccruedInterest = defaultMonthlyInterestRate * runningDebt;
//                netChange = ibrPayment - monthlyAccruedInterest;
//
//                //monthly interest section
//
//                //negative amortization payment for subsidized loans. For now just assume these loans are subsidized, later implement a check.
//                if (financialHardship==true && repaymentYear < 3 && netChange < 0)
//                {
//                    netChange=0;
//                }
//
//                if (netChange < 0)
//                {
//                    totalUnpaidInterest += (netChange*-1);
//                }
//
//                if (totalUnpaidInterest < 0)
//                {
//                    totalUnpaidInterest=0;
//                }
//
//                runningDebt -= netChange;
//
//                if (runningDebt <= 0)
//                {
//                    //so the last payment should be just what is required to zero out balance
//                    ibrPayment=ibrPayment+runningDebt;
//                    runningDebt=0;
//                }
//
//                //not currently used for anything just a statistic measurement
//                totalAccruedInterest += monthlyAccruedInterest - ibrPayment;
//                if (totalAccruedInterest < 0)
//                {
//                    totalAccruedInterest = 0;
//                }
//
//                totalSpent += ibrPayment;
//                payments.add(ibrPayment);
//            }
//
//            povertyLevelsingle48states2017=povertyLevelsingle48states2017*annualPovertyInflation;
//            runningIncome = runningIncome * annualRaise;
//
//            repaymentYear++;
//        }
//
//        forgiveness=runningDebt;
//
//        if (oportunisticSwitch==true)
//        {
//            MainActivity.simpleForgiveness.add(0.0);
//        }
//        else
//        {
//            MainActivity.simpleForgiveness.add(forgiveness);
//        }
//
//        MainActivity.payments = payments;
//        return payments;
//    }


}
