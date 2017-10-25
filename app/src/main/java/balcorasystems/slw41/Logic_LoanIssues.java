package balcorasystems.slw41;

import java.util.ArrayList;

/**
 * Created by L on 10/7/2017.
 */

public class Logic_LoanIssues
{
    public static ArrayList<String> CheckOutstanding (Object_Borrower passedBorrower)
    {
        ArrayList<String> results = new ArrayList<>();

        if (passedBorrower.inDefault)
        {
            results.add("Loan in Default");
        }

        if (passedBorrower.inDelinquincy)
        {
            results.add("Loan Delinquent");
        }

        if (passedBorrower.deceased)
        {
            results.add("Loan Cancellation Available");
        }

        if (!passedBorrower.employmentType.equals("Private for profit company") && !passedBorrower.deceased)
        {
            results.add("Public Service Loan Cancellation Available");
        }

        return results;
    }
}
