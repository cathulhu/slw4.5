package balcorasystems.slw41;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
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

        result.setText(runnableResult.toString());
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

            String authXSRFtoken = headersMap.get("Set-Cookie").get(1);
            String splitToken[] = authXSRFtoken.split(";");
            String secondSplitToken[] = splitToken[0].split("=");
            authXSRFtoken=secondSplitToken[1];

            String sessionIDtoken = headersMap.get("Set-Cookie").get(0);
            String splitIdToken[] = sessionIDtoken.split(";");
            sessionIDtoken=splitIdToken[0];

            String rawResponce = "";
            JSONObject jsonResponce = new JSONObject();

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


            okhttp3.Response response = null;
            try {
                response = client.newCall(postRequest).execute();
                rawResponce = response.body().string();
                jsonResponce = new JSONObject(rawResponce);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return jsonResponce;
        }


        @Override
        public void run()
        {
            jsonResult = Go(username, password);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateResults(jsonResult);
                }
            });
        }
    }



}
