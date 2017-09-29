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

        for (Object_Loan loan : Object_Debt.loanPortfolio)
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
        Object_Debt.addRepaymentNoSwitching("Standard", stdPayments, totalPayed, 0.0, 0.0);

        //updating the master borrower object with this data so I don't have to pass one back.
        MainActivity.masterBorrower=targetBorrower;
    }



    public static Double DiscIncomeCalc(Integer repaymentMonth, Double currentIncome, String filingStatus)
    {
        if (filingStatus.equals("jointly something"))
        {
            //use the spousal poverty guidelines
        }
        double povertyLevelsingle48states2017 = 12060;  //will adjust and make this an array with all the propper values later
        double financialHardshipLine = povertyLevelsingle48states2017 * 1.5;
        double discretionaryIncome = currentIncome - financialHardshipLine;

        return discretionaryIncome;
    }





    public static String financialHardshipCalc(double discretionaryIncome)
    {
        //perhaps later throw in a check to make sure std calc has actually been run first

        Integer coordinatesOfStdRepayment=0;
        String hardshipResult;

        for (Object_Repayment x: Object_Debt.repaymentPortfolio)
        {
            if (x.type.equals("Standard"))
            {
                coordinatesOfStdRepayment=x.coordinateInArray;
            }
        }

        Double stdPayment= Object_Debt.repaymentPortfolio.get(coordinatesOfStdRepayment).monthlyPayments.get(0);
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


//PAYE calc works, now just need to add the other calculations, the create the mega calc that calls on them all and can compare the results

    public static void payeCalc()
    {
        ArrayList<Double> payments = new ArrayList<>();
        Double totalPayed=0D;
        //TODO: think if I want to be returning payments to the mega function that calls the individual calcs or a more sophisitacted object, either way need to return this array

        for (Object_Loan loan: targetBorrower.debtAndRepaymentObject.loanPortfolio)
        {
            loan.updatetypes();

            Double loanRunningIndividualBalance = loan.startingBalance;
            Double loanInterestRate = loan.interestRate/100/12;
            Double loan10percentCapAmmount = loan.startingBalance*0.10;

            Double payeUncappitalizedInterest=0D;
            Integer repaymentMonth=0;
            Integer repaymentMonthLimit=0;
            double runningIncome= Object_Borrower.startingPrimaryIncome;
            String taxfilingStatus = targetBorrower.taxStatus;
            double forgiveness=0D;
            String pfhStatus;


            if (loan.isGraduateLoan)
            {
                repaymentMonthLimit=300;
            }
            else
            {
                repaymentMonthLimit=240;
            }

            //PAYE has 10% principal interest cap

            while (loanRunningIndividualBalance > 0 && repaymentMonth < repaymentMonthLimit)
            {
                Double thisMonthsInterest = loanRunningIndividualBalance*loanInterestRate;  //should this value be divided by 12?
                Double payeMonthlyInterest=0D;
                Double discretionaryIncome=0D;
                double idrPayment = 0D;

                //checking for joint filing which counts spousal income;
                if(targetBorrower.taxStatus.equals("joint filing option here"))
                {
                    runningIncome += Object_Borrower.spouseIncomeOverTime.get(repaymentMonth);
                }
                else
                {
                    runningIncome = Object_Borrower.primaryIncomeOverTime.get(repaymentMonth);
                }

                //calculate payment
                discretionaryIncome=Calculations.DiscIncomeCalc(repaymentMonth, runningIncome, taxfilingStatus);
                pfhStatus = financialHardshipCalc(discretionaryIncome);
                idrPayment = discretionaryIncome/12*0.10;

                //check if payment is greater than ballance, used for final payment
                if (loanRunningIndividualBalance - idrPayment < 0)
                {
                    idrPayment = (loanRunningIndividualBalance - idrPayment) * -1;
                }

                //adding idrPayment to array of payments for this loan
                payments.add(idrPayment);
                totalPayed += idrPayment;

                //calculate interest
                if (loan.isSubsidized && repaymentMonth < 36)
                {
                    payeMonthlyInterest =0D;
                }
                else
                {
                    payeMonthlyInterest = thisMonthsInterest;
                }

                //adding latest interest to the uncapitalized pile
                payeUncappitalizedInterest += payeMonthlyInterest;

                //checking partial financial hardship status
                pfhStatus=financialHardshipCalc(discretionaryIncome);

                if (pfhStatus.equals("No PFH"))
                {
                    //the three events that trigger interest capitalization are default, leaving the plan or loss of PFH

                    if (payeUncappitalizedInterest > loan10percentCapAmmount)
                    {
                        payeUncappitalizedInterest = loan10percentCapAmmount;
                    }

                    loanRunningIndividualBalance += payeUncappitalizedInterest;
                }

                repaymentMonth++;
            }

            //adding the uncapitalized interest to the balance at the end
            if (payeUncappitalizedInterest > loan10percentCapAmmount)
            {
                payeUncappitalizedInterest = loan10percentCapAmmount;
            }
            loanRunningIndividualBalance += payeUncappitalizedInterest;

            forgiveness=loanRunningIndividualBalance;

            //TODO: add calculation for tax on forgived sum

            //passing 0.0 for taxes until I put tax calc back in
            Object_Debt.addRepaymentNoSwitching("PAYE", payments, totalPayed, forgiveness, 0.0);

            //updating the master borrower object with this data so I don't have to pass one back.
            MainActivity.masterBorrower=targetBorrower;
        }






    }


    public static void repayeCalc(Object_Loan loan)
    {
        Double loanRunningIndividualBalance = loan.startingBalance;
        Double loanInterestRate = loan.interestRate/100/12;
        Double loan10percentCapAmmount = loan.startingBalance*0.10;
        Double payeTotalInterest=0D;
        Double repayeTotalInterest=0D;
        Integer repaymentMonth=0;
        Integer repaymentMonthLimit=0;
        String pfhStatus;

        if (loan.isGraduateLoan)
        {
            repaymentMonthLimit=300;
        }
        else
        {
            repaymentMonthLimit=240;
        }

        //PAYE has 10% principal interest cap, REPAYE has 50% interest subsidy

        while (loanRunningIndividualBalance > 0 && repaymentMonth < repaymentMonthLimit)
        {
            Double thisMonthsInterest = loanRunningIndividualBalance*loanInterestRate;
            Double repayeMonthlyInterest=0D;
            Double payeMonthlyInterest=0D;

            if (loan.isSubsidized && repaymentMonth < 36)
            {
                repayeMonthlyInterest=0D;
                payeMonthlyInterest=0D;
            }
            else
            {
                repayeMonthlyInterest = thisMonthsInterest/2; //REPAYE subsidy
                payeMonthlyInterest= thisMonthsInterest;
            }

            if (payeTotalInterest+payeMonthlyInterest > loan10percentCapAmmount)
            {
                payeMonthlyInterest=0D;
            }

            payeTotalInterest += payeMonthlyInterest;
            repayeTotalInterest += repayeMonthlyInterest;

            repaymentMonth++;
        }


    }

















//    public void lowestCalc()
//    {
//        ArrayList<Double> totalPayments = new ArrayList<>();
//        ArrayList<ArrayList> individualPayments = new ArrayList<>();
//        double forgiveness=0;
//        double statTotalSpent = 0;
//
//        for (Object_Loan loan : targetBorrower.debtAndRepaymentObject.loanPortfolio)
//        {
//            ArrayList<Double> perLoanRepayments = new ArrayList<>();
//            ArrayList<String> perloanRepaymentTypes = new ArrayList<>();
//            ArrayList<String>monthlyPaymentCalc = new ArrayList();
//            Double lowestVariablePayment;
//            Integer repaymentMonth = 0 ;
//            Integer repaymentMonthLimit = 300;  //add functionality to change this for different repayment types
//            double startingStandardRepayment=Calculations.Standard10QuickCheck();
//            double monthlyInterestRate=loan.interestRate/100/12;
//            double monthlyAccruedInterest;
//            double netChange;
//            double startingIncome=targetBorrower.startingPrimaryIncome;    //might not need this, prolly could just do running income
//            double runningIncome=startingIncome;
//            double discretionaryIncome=1;
//            double idrPayment;
//            double statAccruedUnpaidInterst = 0;
//            double loanRunningIndividualBalance=loan.currentBalance;
//            String pfhStatus;
//
//
//            while (loanRunningIndividualBalance > 0 && repaymentMonth < repaymentMonthLimit)
//            {
//                //stages each month: check which IDR best eligible for, calculate IDR payment, calculate interest, update income, process payment
//                discretionaryIncome=DiscIncomeCalc(repaymentMonth, runningIncome);
//                pfhStatus = financialHardshipCalc(discretionaryIncome, targetBorrower);
//                ArrayList<String> results = variableRepaymentCalc(targetBorrower, loan, repaymentMonth, pfhStatus, discretionaryIncome);
//
//                idrPayment = Double.valueOf(results.get(1));
//
//                perloanRepaymentTypes.add( results.get(0) );
//                perLoanRepayments.add( Double.valueOf(results.get(1)));
//
//                monthlyAccruedInterest = monthlyInterestRate * loanRunningIndividualBalance;
//                //put check here if isSubsidized, if so and < 36 months interest = 0
//
//                netChange = idrPayment - monthlyAccruedInterest;
//
//                if (netChange > 0)
//                {
//                    if (loanRunningIndividualBalance - netChange <= 0)
//                    {
//                        //condition for if payment exceeds balance of loan, IE for calculating the final payment
//                        idrPayment = idrPayment + (loanRunningIndividualBalance - netChange);   // <-- plus a negative should reduce idrPayment to whatever required to zero out balance
//                        //I think this is correct, delte this when I've verified
//                        loanRunningIndividualBalance=0;
//                    }
//                    else
//                    {
//                        //condition for typical payment
//                        loanRunningIndividualBalance -= (long) netChange;
//                    }
//                }
//                else
//                {
//                    //condition where generated interest is greater than payment
//                    netChange= netChange*-1;    //converting to positive value so I can add this balance
//                    loanRunningIndividualBalance += (long) netChange;
//                    statAccruedUnpaidInterst += (long) netChange;     //calculating how much unpaid interest accrues
//                }
//                statTotalSpent += idrPayment;
//                loan.currentBalance=loanRunningIndividualBalance;
//
//                if (repaymentMonth > totalPayments.size()-1)    //double check that this logic works the way you think it does
//                {
//                    totalPayments.add(idrPayment);
//                }
//                else
//                {
//                    totalPayments.set(repaymentMonth, totalPayments.get(repaymentMonth)+idrPayment);
//                }
//                repaymentMonth++;
//
//                runningIncome=targetBorrower.primaryIncomeOverTime.get(repaymentMonth);
//                targetBorrower.primaryCurrentIncome=runningIncome;  //might not actually NEED to set this but eh for now
//            }
//            individualPayments.add(perLoanRepayments);
//
//            //final loan forgiveness sorting out
//            if (loanRunningIndividualBalance > 0)
//            {
//                forgiveness += loanRunningIndividualBalance;
//            }
//
//            loan.forgiveness=forgiveness;
//            loan.currentBalance=(long) 0;
//        }
//
//        targetBorrower.debtAndRepaymentObject.addRepaymentNoSwitching("Variable", totalPayments, statTotalSpent, forgiveness, 0);
//
//    }



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
//                //negative amortization payment for isSubsidized loans. For now just assume these loans are isSubsidized, later implement a check.
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
//                //negative amortization payment for isSubsidized loans. For now just assume these loans are isSubsidized, later implement a check.
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
