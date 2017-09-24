package balcorasystems.slw41;

import java.util.ArrayList;



public class Object_Borrower
{
    public String currentlyEditing;

    public boolean inDefault;
    public boolean inDelinquincy;
    public boolean deceased;
//    public boolean hasConsolidated;
    public boolean loanRehab;
//    public boolean parentLoans;
//    public boolean ffelLoans;
//    public boolean perkinsLoans;
//    public boolean directLoans;

    public boolean timeBefore98;
    public boolean timeBetween98to07;
    public boolean timeBetween07to11;
    public boolean timeBetween11to14;
    public boolean timeAfter14;

    public String employmentType;
    public String taxStatus;
    public String servicer;
//    public String loanDateString;
//    public Integer loanDate;

    // public Double debt;
    public Integer taxSize;

    public static Double annualraisePercent=(1.05);
    //could have a separate raise percent for spouse, add this later
    public static Double startingPrimaryIncome;
    public static Double startingSpouseIncome;
    public static ArrayList<Double> primaryIncomeOverTime = new ArrayList<>();
    public static ArrayList<Double> spouseIncomeOverTime = new ArrayList<>();
    public static Double primaryCurrentIncome=startingPrimaryIncome;
    public static Double spouseCurrentIncome=startingSpouseIncome;

    public Boolean wagesGranished;
    public Double firstNextRepaymentDate;
    public Boolean inGrace;
    public Boolean inDeferment;
    public Boolean inForebearance;

    public static Object_Debt debtAndRepaymentObject = new Object_Debt();

    // public ArrayList<Double> payments;
    // make sure the update everything that still uses this and switch it over to the proper Object_Loan stuff.

    public static void PopulatePrimaryIncome ()
    {
        primaryCurrentIncome=startingPrimaryIncome;

        while (primaryIncomeOverTime.size() < 500)
        {
            for (int i = 0; i < 12; i++)
            {
                primaryIncomeOverTime.add(primaryCurrentIncome);
            }

            primaryCurrentIncome = primaryCurrentIncome * annualraisePercent;
        }

    }

    public static void PopulateSpouseIncome ()
    {
        spouseCurrentIncome=startingSpouseIncome;

        while (spouseIncomeOverTime.size() < 500)
        {
            for (int i = 0; i < 12; i++)
            {
                spouseIncomeOverTime.add(spouseCurrentIncome);
            }

            spouseCurrentIncome = spouseCurrentIncome * annualraisePercent;
        }

    }


}
