package balcorasystems.slw41;


import java.util.ArrayList;

public class Logic_Calculations {
    static Object_Borrower targetBorrower;

    Logic_Calculations(Object_Borrower passedBorrower) {
        targetBorrower = passedBorrower;

    }


    public static void StandardRepayCalc() {
        ArrayList<Double> stdPayments = new ArrayList<>();
        Double totalPayed = 0.0;
        Double individualPayment = 0.0;
        Double totalMonthlyPayment = 0.0;

        for (Object_Loan loan : Object_Debt.loanPortfolio) {
            ArrayList<Double> payments = new ArrayList<>();
            double monthlyInterestRate = loan.interestRate / 100 / 12;
            double paymentCalcNumerator = monthlyInterestRate * loan.currentBalance;    //change this to current balance soon and start using that value in other things
            double paymentCalcDenominator = 1 - Math.pow(1 + (monthlyInterestRate), -120);
            individualPayment = paymentCalcNumerator / paymentCalcDenominator;

            totalPayed += individualPayment * 120;
            totalMonthlyPayment += individualPayment;
        }

        for (int i = 0; i < 120; i++) {
            stdPayments.add(totalMonthlyPayment);
        }

        //passing 0.0 for forgiveness and 0.0 for taxes
        Object_Debt.addStdRepayment("Standard", stdPayments, totalPayed, 0.0, 0.0, targetBorrower.repaymentDate);

        //updating the master borrower object with this data so I don't have to pass one back.
        MainActivity.masterBorrower = targetBorrower;
    }


    public static Double DiscIncomeCalc(Integer repaymentMonth, Double currentIncome, String filingStatus) {
        if (filingStatus.equals("jointly something")) {
            //use the spousal poverty guidelines
        }
        double povertyLevelsingle48states2017 = 12060;  //will adjust and make this an array with all the propper values later
        double financialHardshipLine = povertyLevelsingle48states2017 * 1.5;
        double discretionaryIncome = currentIncome - financialHardshipLine;

        if (discretionaryIncome < 0)
        {
            discretionaryIncome=0D;
        }

        return discretionaryIncome;
    }


    public static String financialHardshipCalc(double discretionaryIncome) {
        //perhaps later throw in a check to make sure std calc has actually been run first

        Integer coordinatesOfStdRepayment = 0;
        String hardshipResult;

        for (Object_Repayment x : Object_Debt.repaymentPortfolio) {
            if (x.type.equals("Standard")) {
                coordinatesOfStdRepayment = x.coordinateInArray;
            }
        }

        Double stdPayment = Object_Debt.repaymentPortfolio.get(coordinatesOfStdRepayment).monthlyPayments.get(0);
        targetBorrower.debtAndRepaymentObject.repaymentPortfolio.get(0).calculateMeanMonthlyPayment();
        Double discretionaryIncomeMonthly = discretionaryIncome / 12;

        if (stdPayment > discretionaryIncomeMonthly * 0.15) {
            //15% PFH true
            hardshipResult = "15% PFH";
        } else if (stdPayment > discretionaryIncomeMonthly * 0.10) {
            //10% PFH true
            hardshipResult = "10% PFH";
        } else {
            //No PFH
            hardshipResult = "No PFH";
        }

        return hardshipResult;

    }


    public static Double TaxCalc (Double passedIncome)

    {

        Double incomeBracketDiff;
        String filingStatus=targetBorrower.taxStatus;
        Integer taxcoord1=0;
        Double owedTotal=0D;

        //TODO: update these values for 2017 and add another dimension for famyily sizes
        double[] taxBracketsPercent2016 = {.10, .15, .25, .28, .33, .35, .396};
        double[] taxBracketsGrossSingle2016 = {0, 9275, 37650, 91150, 190150, 413350, 415050};
        double[] taxBracketsGrossJoint2016 = {0, 18550, 75300, 151900, 231450, 413350, 466950};
        double[] taxBracketsGrossHead2016 = {0, 13250, 50400, 130150, 210800, 413350, 441000};
        double[][] taxContainer = {taxBracketsGrossSingle2016, taxBracketsGrossJoint2016, taxBracketsGrossHead2016};

        if (filingStatus.equals("Single filing") || filingStatus.equals("Married but filing separately") )
        {
            taxcoord1=0;
        }
        else if (filingStatus.equals("Joint filing"))
        {
            taxcoord1=1;
        }
        else if (filingStatus.equals("Head of Household"))
        {
            taxcoord1=2;
        }

        for (int i = 6; passedIncome > 0; i--)
        {
            if (passedIncome > taxContainer[taxcoord1][i])                                              //for smaller example, 40k forgiven... 40k<415k, loop, 40k<413k, 40k<190k 40k<91l, 40k<37k = yes, start at i=2
            {                                                                                                // example first time around loop income(loan forgiveness)= 500k
                double taxRate = taxBracketsPercent2016[i];                                             //.396,
                incomeBracketDiff = passedIncome - (taxContainer[taxcoord1][i]);               //lets do single filing, rt=500k - (415050) , so iBD =84950
                passedIncome = passedIncome - incomeBracketDiff;                               //                       rt now = 500k- 84950, rt = 415050
                owedTotal += taxRate * incomeBracketDiff;                                           // wt = 0.396*84950 = 33640.2
            }                                                                                      //second time around loop
                                                                                                   //.35 rate,
        }                                                                                          // Ibd = 415050-413350 = 1700
                                                                                                   // rt = 415050-1700 = 413350
                                                                                                    // wt = 0.35 * 1700 = 33640.2 + 595 = 34235.2
    return owedTotal;
    }


    public static void payeCalc()
    {
        Object_Borrower copyofBorrower = MainActivity.masterBorrower;
        Double loanTotalBalance= 0D;
        Double loan10percentCapAmmount= 0D;
        Integer repaymentMonth=0;

        Integer repaymentMonthLimit=0;
        Double runningIncome= Object_Borrower.startingPrimaryIncome;
        String taxfilingStatus = targetBorrower.taxStatus;
        Double forgiveness=0D;
        String pfhStatus = "";
        Double owedTaxesOnForgiveness = 0D;
        ArrayList<Double> payments = new ArrayList();
        Double totalPayed = 0D;


        Double standardPayment = copyofBorrower.debtAndRepaymentObject.repaymentPortfolio.get(0).meanMonthlyRepayment;

        //populate initial values
        for (Object_Loan loan: copyofBorrower.debtAndRepaymentObject.loanPortfolio)
        {
            loanTotalBalance += loan.currentBalance;
            loan10percentCapAmmount += loan.startingBalance*0.10;
        }




        while (loanTotalBalance > 0)
        {
            Double discretionaryIncome=0D;
            Double idrPayment=0D;



            //calculate payment
            //TODO add in something that calculates tax savings filing seperately vs jointly
            if(copyofBorrower.taxStatus.equals("joint filing option here"))
            {
                runningIncome += Object_Borrower.spouseIncomeOverTime.get(repaymentMonth) + Object_Borrower.primaryIncomeOverTime.get(repaymentMonth);
            }
            else
            {
                runningIncome = Object_Borrower.primaryIncomeOverTime.get(repaymentMonth);
            }
            discretionaryIncome= Logic_Calculations.DiscIncomeCalc(repaymentMonth, runningIncome, taxfilingStatus);
            pfhStatus = financialHardshipCalc(discretionaryIncome);
            idrPayment = discretionaryIncome/12*0.10;

            if (idrPayment > 0 && idrPayment < 5)
            {
                idrPayment=5D;
            }


            if (pfhStatus.equals("No PFH") || copyofBorrower.inDefault)
            {
                idrPayment = standardPayment;
            }
            payments.add(idrPayment);


            //interest and payments
            for (Object_Loan loan: copyofBorrower.debtAndRepaymentObject.loanPortfolio)
            {

                //payment allocation - ratio based
                Double paymentPercentAllocation = loan.currentBalance/loanTotalBalance;
                Double apportionedPayment = idrPayment*paymentPercentAllocation;

                //generate interest
                Double loanRunningIndividualBalance = loan.currentBalance;
                Double loanInterestRate = loan.interestRate/100/12;
                Double perLoanPerMonthInterest = loanRunningIndividualBalance*loanInterestRate;

                if (loan.isSubsidized && repaymentMonth < 36)
                {
                    perLoanPerMonthInterest =0D;
                }



                if (perLoanPerMonthInterest-apportionedPayment > 0)
                {
                    Double negativeAmoritizationLeftover = (perLoanPerMonthInterest-apportionedPayment);

                    //applying PAYE interest cap benefit, if cap reached no more uncapitalized interest accrues, but I THINK PAYMENT IS STILL REDUCED BY INTEREST
                    if (!loan.PAYEinterestCapReached)
                    {
                        loan.uncapitalizedInterest += negativeAmoritizationLeftover;
                        if (loan.uncapitalizedInterest >= loan.PAYEinterestCapValue)
                        {
                            loan.uncapitalizedInterest=loan.PAYEinterestCapValue;
                            loan.PAYEinterestCapReached=true;
                        }

                    }
                }
                else
                {
                    //if payment is greater than interest then there is no interest to add to uncapitalized pile
                    Double netPaymentPostInterestDeduction = apportionedPayment-perLoanPerMonthInterest;
                    loan.currentBalance -= netPaymentPostInterestDeduction;
                }


                //interest capitalization
                //of course there is 3rd possibility that triggers capitalization, leaving the plan, I'll handle switching later.
                if (pfhStatus.equals("No PFH") || pfhStatus.equals("15% PFH") || copyofBorrower.inDefault)
                {
                    loan.currentBalance += loan.uncapitalizedInterest;
                    loan.uncapitalizedInterest=0;
                }


            }


            loanTotalBalance=0D;
            for (Object_Loan loan: copyofBorrower.debtAndRepaymentObject.loanPortfolio)
            {
                loanTotalBalance += loan.currentBalance;
            }

//            //payment allocation - highest interest first
//            for (Object_Loan loan: targetBorrower.debtAndRepaymentObject.loanPortfolio)
//            {
//
//            }

            //check for loan timeout
            for (Object_Loan loan: copyofBorrower.debtAndRepaymentObject.loanPortfolio)
            {
                if (loan.isGraduateLoan)
                {
                    if (repaymentMonth == 299)
                    {
                        loan.uncapitalizedInterest += loan.currentBalance;
                        loan.currentBalance=0;
                    }
                }
                else
                {
                    if (repaymentMonth == 239)
                    {
                        loan.uncapitalizedInterest += loan.currentBalance;
                        loan.currentBalance=0;
                    }
                }
            }

            loanTotalBalance=0D;
            for (Object_Loan loan: copyofBorrower.debtAndRepaymentObject.loanPortfolio)
            {
                loanTotalBalance += loan.currentBalance;
            }

            repaymentMonth++;
        }

        //dump last uncapitalized interest into forgiveness
        for (Object_Loan loan: copyofBorrower.debtAndRepaymentObject.loanPortfolio)
        {
            forgiveness += loan.uncapitalizedInterest;
            loan.uncapitalizedInterest=0;
            forgiveness += loan.currentBalance;
            loan.currentBalance=0;
        }

        for (Double x:payments)
        {
                totalPayed += x;
        }

        owedTaxesOnForgiveness = TaxCalc(forgiveness + runningIncome) - TaxCalc(runningIncome);

        Object_Debt.addRepaymentNoSwitching("PAYE", payments, totalPayed, forgiveness, owedTaxesOnForgiveness, targetBorrower.repaymentDate);
        Logic_RepaymentActions repaymentActions = new Logic_RepaymentActions();
        repaymentActions.populate(copyofBorrower, "PAYE");

        Integer index=0;
        Integer arrayCoord=0;
        for (Object_Repayment repayment:copyofBorrower.debtAndRepaymentObject.repaymentPortfolio)
        {
            if (repayment.type.equals("PAYE"));
            {
                arrayCoord=index;
            }
            index++;
        }

        //restoring the starting balances so next calculation can work.
        for (Object_Loan loan:copyofBorrower.debtAndRepaymentObject.loanPortfolio)
        {
            loan.currentBalance=loan.startingBalance;
        }

        Object_Debt.repaymentPortfolio.get(arrayCoord).addRepaymentActions(repaymentActions);
        MainActivity.masterBorrower=copyofBorrower;
    }



    public static void repayeCalc()
    {
        Object_Borrower copyofBorrower = MainActivity.masterBorrower;
        Double loanTotalBalance= 0D;
        Integer repaymentMonth=0;

        Integer repaymentMonthLimit=0;
        Double runningIncome= Object_Borrower.startingPrimaryIncome;
        String taxfilingStatus = targetBorrower.taxStatus;
        Double forgiveness=0D;
        Double owedTaxesOnForgiveness = 0D;
        ArrayList<Double> payments = new ArrayList();
        Double totalPayed = 0D;

        //populate initial values
        for (Object_Loan loan: copyofBorrower.debtAndRepaymentObject.loanPortfolio)
        {
            loanTotalBalance += loan.currentBalance;
        }

        while (loanTotalBalance > 0)
        {
            Double discretionaryIncome=0D;
            Double idrPayment=0D;


            //calculate payment
            if(copyofBorrower.isMarried)
            {
                //for REPAYE your spouses income is counted regardless of how you file
                runningIncome += Object_Borrower.spouseIncomeOverTime.get(repaymentMonth) + Object_Borrower.primaryIncomeOverTime.get(repaymentMonth);
            }
            else
            {
                runningIncome = Object_Borrower.primaryIncomeOverTime.get(repaymentMonth);
            }
            discretionaryIncome= Logic_Calculations.DiscIncomeCalc(repaymentMonth, runningIncome, taxfilingStatus);
            idrPayment = discretionaryIncome/12*0.10;

            if (idrPayment > 0 && idrPayment < 5)
            {
                idrPayment=5D;
            }

            payments.add(idrPayment);



            for (Object_Loan loan: copyofBorrower.debtAndRepaymentObject.loanPortfolio)
            {
                //payment allocation - ratio based
                Double paymentPercentAllocation = loan.currentBalance/loanTotalBalance;
                Double apportionedPayment = idrPayment*paymentPercentAllocation;

                if (loan.currentBalance - apportionedPayment < 0)
                {
                    apportionedPayment = loan.currentBalance;
                }

//                loan.currentBalance -= apportionedPayment;    NOT YET



                //generate and apply interest from each loan
                Double loanRunningIndividualBalance = loan.currentBalance;
                Double loanInterestRate = loan.interestRate/100/12;
                Double perLoanPerMonthInterest = loanRunningIndividualBalance*loanInterestRate;

                //TODO add feature to repayment model that doesn't accrue interest during forebearance (while in school. or grace?) and tells model to turn forebearance off when payment date reached
                if (loan.isSubsidized && repaymentMonth < 36)
                {
                    perLoanPerMonthInterest =0D;
                }

                //applying RPEAYE 50% subsidy on remaining balance of interest that exceeds apportioned payment
                if (perLoanPerMonthInterest-apportionedPayment > 0)
                {
                    Double negativeAmoritizationLeftover = (perLoanPerMonthInterest-apportionedPayment);
                    negativeAmoritizationLeftover /= 2;
                    loan.uncapitalizedInterest += negativeAmoritizationLeftover;
                }
                else
                {
                    //if payment is greater than interest then there is no interest to add to uncapitalized pile
                    Double netPaymentPostInterest = apportionedPayment-perLoanPerMonthInterest;
                    loan.currentBalance -= netPaymentPostInterest;
                }



            }

            //interest capitalization
            //interest only capitalizes when you leave the program for PAYE which is handled in timeout and payoff below

            loanTotalBalance=0D;
            for (Object_Loan loan: copyofBorrower.debtAndRepaymentObject.loanPortfolio)
            {
                loanTotalBalance += loan.currentBalance;
            }




            //TODO finish highest allocation first, payes interest due on highest interest first, then distributes the rest by ratio?
//            //payment allocation - highest interest first
//            for (Object_Loan loan: targetBorrower.debtAndRepaymentObject.loanPortfolio)
//            {
//
//            }

            //check for loan timeout
            for (Object_Loan loan: copyofBorrower.debtAndRepaymentObject.loanPortfolio)
            {
                if (loan.isGraduateLoan)
                {
                    if (repaymentMonth == 299)
                    {
                        loan.uncapitalizedInterest += loan.currentBalance;
                        loan.currentBalance=0;
                    }
                }
                else
                {
                    if (repaymentMonth == 239)
                    {
                        loan.uncapitalizedInterest += loan.currentBalance;
                        loan.currentBalance=0;
                    }
                }
            }

            loanTotalBalance=0D;
            for (Object_Loan loan: copyofBorrower.debtAndRepaymentObject.loanPortfolio)
            {
                loanTotalBalance += loan.currentBalance;
            }

            repaymentMonth++;
        }

        //dump last uncapitalized interest into forgiveness
        for (Object_Loan loan: copyofBorrower.debtAndRepaymentObject.loanPortfolio)
        {
            forgiveness += loan.uncapitalizedInterest;
            loan.uncapitalizedInterest=0;
            forgiveness += loan.currentBalance;
            loan.currentBalance=0;
        }

        for (Double x:payments)
        {
            totalPayed += x;
        }

        owedTaxesOnForgiveness = TaxCalc(forgiveness + runningIncome) - TaxCalc(runningIncome);

        Object_Debt.addRepaymentNoSwitching("REPAYE", payments, totalPayed, forgiveness, owedTaxesOnForgiveness, targetBorrower.repaymentDate);
        Logic_RepaymentActions repaymentActions = new Logic_RepaymentActions();
        repaymentActions.populate(copyofBorrower, "REPAYE");

        Integer index=0;
        Integer arrayCoord=0;
        for (Object_Repayment repayment:copyofBorrower.debtAndRepaymentObject.repaymentPortfolio)
        {
            if (repayment.type.equals("REPAYE"));
            {
                arrayCoord=index;
            }
            index++;
        }

        //restoring the starting balances so next calculation can work.
        for (Object_Loan loan:copyofBorrower.debtAndRepaymentObject.loanPortfolio)
        {
            loan.currentBalance=loan.startingBalance;
        }

        Object_Debt.repaymentPortfolio.get(arrayCoord).addRepaymentActions(repaymentActions);
        MainActivity.masterBorrower=copyofBorrower;
    }





//    public static void repayeCalc(Object_Loan loan)
//    {
//        Double loanRunningIndividualBalance = loan.startingBalance;
//        Double loanInterestRate = loan.interestRate/100/12;
//        Double loan10percentCapAmmount = loan.startingBalance*0.10;
//        Double payeTotalInterest=0D;
//        Double repayeTotalInterest=0D;
//        Integer repaymentMonth=0;
//        Integer repaymentMonthLimit=0;
//        String pfhStatus;
//
//        if (loan.isGraduateLoan)
//        {
//            repaymentMonthLimit=300;
//        }
//        else
//        {
//            repaymentMonthLimit=240;
//        }
//
//        //PAYE has 10% principal interest cap, REPAYE has 50% interest subsidy
//
//        while (loanRunningIndividualBalance > 0 && repaymentMonth < repaymentMonthLimit)
//        {
//            Double thisMonthsInterest = loanRunningIndividualBalance*loanInterestRate;
//            Double repayeMonthlyInterest=0D;
//            Double payeMonthlyInterest=0D;
//
//            if (loan.isSubsidized && repaymentMonth < 36)
//            {
//                repayeMonthlyInterest=0D;
//                payeMonthlyInterest=0D;
//            }
//            else
//            {
//                repayeMonthlyInterest = thisMonthsInterest/2; //REPAYE subsidy
//                payeMonthlyInterest= thisMonthsInterest;
//            }
//
//            if (payeTotalInterest+payeMonthlyInterest > loan10percentCapAmmount)
//            {
//                payeMonthlyInterest=0D;
//            }
//
//            payeTotalInterest += payeMonthlyInterest;
//            repayeTotalInterest += repayeMonthlyInterest;
//
//            repaymentMonth++;
//        }
//
//
//    }




















}
