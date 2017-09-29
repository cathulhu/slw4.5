package balcorasystems.slw41;


public class Object_Loan
{
    String type;
    Boolean wasRehabed;
    Boolean isSubsidized =false;
    Boolean isDirectLoan =false;
    Boolean isGraduateLoan =false;
    Double interestRate;
    double currentBalance;
    double startingBalance;
    double forgiveness;
    Long generatedMonthlyInterest;
    Long accumulatedNonCapitalizedInterest;

    Object_Loan ()
    {

    }

    public void updatetypes()
    {
        startingBalance=currentBalance;

        if ( this.type.equals("Direct Subsidized Loan") || this.type.equals("Subsidized Federal Stafford Loan") || this.type.equals("Direct Subsidized Consolidation Loan") )
        {
            isSubsidized =true;
        }

        if ( this.type.equals("Direct Subsidized Loan") || this.type.equals("Direct Subsidized Consolidation Loan") || this.type.equals("Direct Unsubsidized Loan")
                || this.type.equals("Direct Unsubsidized Consolidation Loan") || this.type.equals("Direct PLUS Loan for Graduate/Professional Students")
                || this.type.equals("Direct Unsubsidized Loan") || this.type.equals("Direct PLUS Consolidation Loan")
                )
        {
            isDirectLoan =true;
        }

        if (this.type.equals("Direct PLUS Loan for Graduate/Professional Students") || this.type.equals("FFEL PLUS Loan for Graduate/Professional Students"))
        {
            isGraduateLoan =true;
        }
    }
}
