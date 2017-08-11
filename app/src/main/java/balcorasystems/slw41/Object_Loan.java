package balcorasystems.slw41;

import java.util.ArrayList;

/**
 * Created by L on 7/30/2017.
 */

public class Object_Loan
{
    public boolean inDefault;
    public boolean inDelinquincy;
    public boolean deceased;
    public boolean hasConsolidated;
    public boolean loanRehab;
    public boolean parentLoans;
    public boolean ffelLoans;
    public boolean perkinsLoans;
    public boolean directLoans;
    public Integer employmentNumber;
    public String employmentType;
    public String taxStatus;
    public Integer loanDate;
    public String loanDateString;
    public Double debt;
    public Integer taxSize;
    public Double income;
    public Double currentPayment;
    public Integer dependants;
    public ArrayList<Double> loanBalances;
    public ArrayList<Double> payments;
    public String servicer;

}
