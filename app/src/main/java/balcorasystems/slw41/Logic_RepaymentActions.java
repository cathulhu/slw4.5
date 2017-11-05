package balcorasystems.slw41;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by L on 11/4/2017.
 */

public class Logic_RepaymentActions
{
    Integer numberofMonths =0;
    Date startRepayment = new Date();
    Date publicLoanForgivenessExecution = new Date();
    Date publicLoanForgivenessRegistration = new Date();
    Date standardLoanForgivenessDate = new Date();
    Boolean plsfStartToggle = false;
    ArrayList<Date> repaymentDates = new ArrayList<>();
    ArrayList<Date> recertifyDates = new ArrayList<>();

//    HashMap<Date, String> masterlist = new HashMap<>();
    ArrayList<ArrayList> masterValues = new ArrayList<>();

    ArrayList<ArrayList> masterDates = new ArrayList<>();


    Logic_RepaymentActions()
    {

    }

    public void populate (Object_Borrower passedBorrower)
    {
        numberofMonths = passedBorrower.debtAndRepaymentObject.repaymentPortfolio.get(1).monthlyPayments.size();
        startRepayment = passedBorrower.debtAndRepaymentObject.repaymentPortfolio.get(1).repaymentStartDate;
        //TODO update a more intelligent selection method for getting these

        Calendar date = new GregorianCalendar();
        date.setTime(startRepayment);


        Integer yearCounter = 0;
        for (int i = 0; i < numberofMonths; i++)
        {
            ArrayList<String> subvalues = new ArrayList<>();
            ArrayList<Date> subDates = new ArrayList<>();

            //add payment
            repaymentDates.add(date.getTime());
//            masterlist.put(date.getTime(), "Payment of $"+ String.valueOf(passedBorrower.debtAndRepaymentObject.repaymentPortfolio.get(1).monthlyPayments.get(i)));
            subDates.add(date.getTime());
            subvalues.add("Payment of $"+ String.valueOf(passedBorrower.debtAndRepaymentObject.repaymentPortfolio.get(1).monthlyPayments.get(i)));

            //if PSLF eligible
            if (!plsfStartToggle)
            {
                if (passedBorrower.PSLFeligib)
                {
                    publicLoanForgivenessRegistration=date.getTime();
//                    masterlist.put(date.getTime(), "Signup for Public Service Loan Forgivness");
                    subvalues.add("Signup for Public Service Loan Forgivness");
                    subDates.add(date.getTime());
                    plsfStartToggle =true;
                }
            }

            //trigger IDR rectification every year
            yearCounter++;
            if (yearCounter==11)
            {
                yearCounter=0;
                recertifyDates.add(date.getTime());
                subvalues.add("Rectify/Reapply for Income Driven Repayment");
                subDates.add(date.getTime());
//                masterlist.put(date.getTime(), "Rectify/Reapply for Income Driven Repayment");
            }

            //if signed up for PSLF and still employed in PSLF capacity then count that as 1 PSLF payment
            if (passedBorrower.PSLFeligib && plsfStartToggle)
            {
                //TODO add a proper condition for actually being enrolled in PSLF
                passedBorrower.PSLFpayments++;
            }

            //if still PLSF employed and 120 PLSF payments have been made trigger forgiveness
            if (passedBorrower.PSLFeligib && passedBorrower.PSLFpayments==120)
            {
                publicLoanForgivenessExecution=date.getTime();
//                masterlist.put(date.getTime(), "Submit Final Loan Forgiveness Request");
                subvalues.add("Submit Final Loan Forgiveness Request");
                subDates.add(date.getTime());
            }

            if (i == numberofMonths-1)
            {
                standardLoanForgivenessDate=date.getTime();
//                masterlist.put(date.getTime(), "Finish Repayment");
//                masterlist.put(date.getTime(), "Remaining Balance and Interest Forgiven");

                subvalues.add("Remaining Balance and Interest Forgiven");
                subDates.add(date.getTime());
                subvalues.add("Finish Repayment");
                subDates.add(date.getTime());
            }

            masterValues.add(subvalues);
            masterDates.add(subDates);

            Calendar c = Calendar.getInstance();
            c.setTime(date.getTime());
            c.add(Calendar.MONTH, +1);
            date.setTime(c.getTime());

        }
    }




}
