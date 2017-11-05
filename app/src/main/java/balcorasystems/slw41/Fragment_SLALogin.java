package balcorasystems.slw41;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class Fragment_SLALogin extends Fragment
{
//    public String results = "";
    public JSONObject loanResults = new JSONObject();
    public Map<String, String> loanDataMap = new HashMap<>();
    Boolean fsaSiteDown=false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootview = inflater.inflate(R.layout.sla_login, container, false);

        final EditText username = (EditText) rootview.findViewById(R.id.unamefield);
        final EditText password = (EditText) rootview.findViewById(R.id.pwfield);
        Button loginButton = (Button) rootview.findViewById(R.id.loginButton);
        final TextView result = (TextView) rootview.findViewById(R.id.postResult);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Thread PostSender = new Thread(new Coms_SLA_runnableZ(username.getText().toString(), password.getText().toString()));
                PostSender.start();
            }
        });




        return rootview;
    }



    public void updateResults(JSONObject runnableResult)
    {
        loanResults = runnableResult;
        TextView result = (TextView) getView().findViewById(R.id.postResult);

        if (fsaSiteDown)
        {
            result.setText("FSA Website Down for Maintenance");
        }
        else
        {
            result.setText(runnableResult.toString());
        }

    }

    public void updateProgress(Integer numOfLoans, Integer progress)
    {
        ProgressBar downloadBar = (ProgressBar) getView().findViewById(R.id.downloadProgress);
        downloadBar.setMax(numOfLoans);
        downloadBar.setProgress(progress);
    }





    public class Coms_SLA_runnableZ implements Runnable
    {

        String username;
        String password;
        JSONObject jsonResult;

        Coms_SLA_runnableZ (String uname, String pwd)
        {
            username=uname;
            password=pwd;
        }


        JSONObject Go (String username, String password)
        {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

            String urlTarget1 = "https://studentloans.gov/myDirectLoan/mobile/repayment/computeRepaymentPlans.action";
            String urlTarget2 = "https://posttestserver.com/post.php";
            String urlTarget3 = "https://studentloans.gov/myDirectLoan/mobile/repayment/loadLoans.action";
            String urlTarget4 = "https://studentloans.gov/myDirectLoan/checkValidLogin.action";

            OkHttpClient client = new OkHttpClient();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("https://studentloans.gov/myDirectLoan/mobile/repayment/repaymentEstimator.action")
                    .build();

            Map<String, List<String>> headersMap = new LinkedHashMap<>();
            okhttp3.Response getresponse;

            try {
                getresponse = client.newCall(request).execute();
                headersMap = getresponse.headers().toMultimap();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String authXSRFtoken = "test=data;fakedata";
            String sessionIDtoken = "test;data";

            if (headersMap.containsKey("Set-Cooke"))
            {
                authXSRFtoken = headersMap.get("Set-Cookie").get(1);
                sessionIDtoken = headersMap.get("Set-Cookie").get(0);
            }
            else
            {
                fsaSiteDown=true;
            }

            String splitToken[] = authXSRFtoken.split(";");
            String secondSplitToken[] = splitToken[0].split("=");
            authXSRFtoken=secondSplitToken[1];

            String splitIdToken[] = sessionIDtoken.split(";");
            sessionIDtoken=splitIdToken[0];

            String rawResponse = "";
            JSONObject jsonResponse = new JSONObject();

//      login format UTF-8 web encoding: "userId=asdasdf%40aaa.comdd&password=Password34589asd&from=&id=";

            try {
                username = URLEncoder.encode(username, "UTF-8");
                password = URLEncoder.encode(password, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String POSTtextLogin = "userId=" + username + "&password=" + password + "&" + "from=" + "&" + "id=";
            RequestBody body = RequestBody.create(MediaType.parse(POSTtextLogin), POSTtextLogin);

            okhttp3.Request postRequest = new okhttp3.Request.Builder()
                    .url(urlTarget3)
                    .post(body)
                    .addHeader("Host", "studentloans.gov")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0")
                    .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                    .addHeader("Accept-Language", "en-US,en;q=0.5")
//                .addHeader("Accept-Encoding", "gzip, deflate, br")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .addHeader("X-XSRF-TOKEN", authXSRFtoken)
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .addHeader("Referer", "https://studentloans.gov/myDirectLoan/mobile/repayment/repaymentEstimator.action")
                    .addHeader("Cookie", sessionIDtoken)
                    .build();

            String testdata ="{\"simpleLoans\":[{\"balance\":4521,\"interestRate\":3.4,\"loanType\":\"D1\",\"date\":{\"dateString\":\"January 2012\"},\"firstDisbursementDate\":{\"dateString\":\"January 2012\"},\"servicer\":\"DEPT OF ED\\/FEDLOAN SERVICING(PHEAA)\",\"servicerPhone\":\"800-699-2908\"},{\"balance\":3517,\"interestRate\":6.8,\"loanType\":\"D2\",\"date\":{\"dateString\":\"January 2012\"},\"firstDisbursementDate\":{\"dateString\":\"January 2012\"},\"servicer\":\"DEPT OF ED\\/FEDLOAN SERVICING(PHEAA)\",\"servicerPhone\":\"800-699-2908\"},{\"balance\":5619,\"interestRate\":3.4,\"loanType\":\"D1\",\"date\":{\"dateString\":\"September 2012\"},\"firstDisbursementDate\":{\"dateString\":\"September 2012\"},\"servicer\":\"DEPT OF ED\\/FEDLOAN SERVICING(PHEAA)\",\"servicerPhone\":\"800-699-2908\"},{\"balance\":9379,\"interestRate\":6.8,\"loanType\":\"D2\",\"date\":{\"dateString\":\"September 2012\"},\"firstDisbursementDate\":{\"dateString\":\"September 2012\"},\"servicer\":\"DEPT OF ED\\/FEDLOAN SERVICING(PHEAA)\",\"servicerPhone\":\"800-699-2908\"},{\"balance\":5635,\"interestRate\":3.86,\"loanType\":\"D1\",\"date\":{\"dateString\":\"September 2013\"},\"firstDisbursementDate\":{\"dateString\":\"September 2013\"},\"servicer\":\"DEPT OF ED\\/FEDLOAN SERVICING(PHEAA)\",\"servicerPhone\":\"800-699-2908\"},{\"balance\":8045,\"interestRate\":3.86,\"loanType\":\"D2\",\"date\":{\"dateString\":\"September 2013\"},\"firstDisbursementDate\":{\"dateString\":\"September 2013\"},\"servicer\":\"DEPT OF ED\\/FEDLOAN SERVICING(PHEAA)\",\"servicerPhone\":\"800-699-2908\"},{\"balance\":1844,\"interestRate\":4.66,\"loanType\":\"D1\",\"date\":{\"dateString\":\"September 2014\"},\"firstDisbursementDate\":{\"dateString\":\"September 2014\"},\"servicer\":\"DEPT OF ED\\/FEDLOAN SERVICING(PHEAA)\",\"servicerPhone\":\"800-699-2908\"},{\"balance\":2672,\"interestRate\":4.66,\"loanType\":\"D2\",\"date\":{\"dateString\":\"September 2014\"},\"firstDisbursementDate\":{\"dateString\":\"September 2014\"},\"servicer\":\"DEPT OF ED\\/FEDLOAN SERVICING(PHEAA)\",\"servicerPhone\":\"800-699-2908\"}],\"nsldsCode\":\"O\",\"error\":false}";
            ArrayList extractedJsonData = new ArrayList();


            okhttp3.Response response = null;
            try {
                response = client.newCall(postRequest).execute();
                rawResponse = response.body().string();
                jsonResponse = new JSONObject(testdata);        // TODO change this back to raw response
                Integer loanNumber =0;
                loanNumber = jsonResponse.getJSONArray("simpleLoans").length();

                updateProgressBar(loanNumber, 0);

                for (int i = 0; i < loanNumber; i++)
                {
                    JSONObject reSerializedsimpleLoansContent = new JSONObject(String.valueOf(jsonResponse.getJSONArray("simpleLoans").get(i)));
                    Integer balance = Integer.valueOf(String.valueOf(reSerializedsimpleLoansContent.get("balance")));
                    Double interest = Double.valueOf(String.valueOf(reSerializedsimpleLoansContent.get("interestRate")));
                    String servicer = String.valueOf(reSerializedsimpleLoansContent.get("servicer"));
                    String servicerPhone = String.valueOf(reSerializedsimpleLoansContent.get("servicerPhone"));
                    String loanType = String.valueOf(reSerializedsimpleLoansContent.get("loanType"));
                    String date = String.valueOf(reSerializedsimpleLoansContent.get("date"));

//                    JSONObject reSerializedDateContent = new JSONObject(String.valueOf(reSerializedsimpleLoansContent.getJSONArray("firstDisbursementDate").get(0)));
//                    String date = String.valueOf(reSerializedsimpleLoansContent.getJSONArray("firstDisbursementDate").get(0));

                    extractedJsonData.add(balance);
                    extractedJsonData.add(interest);
                    extractedJsonData.add(servicer);
                    extractedJsonData.add(servicerPhone);
                    extractedJsonData.add(loanType);
                    extractedJsonData.add(date);

                    Object_Debt updatedDebt = new Object_Debt();
                    updatedDebt.addFullLoan(balance, balance, interest, loanType, servicer, date);

                    MainActivity.downloadedLoans = updatedDebt;

                    updateProgressBar(loanNumber, i+1);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonResponse;
        }


        @Override
        public void run()
        {
            jsonResult = Go(username, password);

            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run() {
                    updateResults(jsonResult);
                }

            });

        }

        public void updateProgressBar(final Integer loanNum, final Integer progress)
        {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run()
                {
                    updateProgress(loanNum, progress);
                }
            });
        }
    }



}
