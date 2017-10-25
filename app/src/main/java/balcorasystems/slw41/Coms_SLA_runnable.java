//package balcorasystems.slw41;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.telecom.Connection;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.franmontiel.persistentcookiejar.PersistentCookieJar;
//import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
//import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.Reader;
//import java.io.UnsupportedEncodingException;
//import java.net.CookieHandler;
//import java.net.CookieManager;
//import java.net.CookiePolicy;
//import java.net.CookieStore;
//import java.net.HttpCookie;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.net.URLEncoder;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.zip.GZIPInputStream;
//
//import javax.net.ssl.HttpsURLConnection;
//
//import okhttp3.CipherSuite;
//import okhttp3.ConnectionSpec;
//import okhttp3.Cookie;
//import okhttp3.CookieJar;
//import okhttp3.FormBody;
//import okhttp3.Headers;
//import okhttp3.HttpUrl;
//import okhttp3.Interceptor;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import okhttp3.TlsVersion;
//import okhttp3.internal.http2.Header;
//
//
//public class Coms_SLA_runnable implements Runnable
//{
//    String username;
//    String password;
//
//    Coms_SLA_runnable (String uname, String pwd)
//    {
//        username=uname;
//        password=pwd;
//    }
//
//
//    String Go (String username, String password)
//    {
//        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
//
//        String urlTarget1 = "https://studentloans.gov/myDirectLoan/mobile/repayment/computeRepaymentPlans.action";
//        String urlTarget2 = "https://posttestserver.com/post.php";
//        String urlTarget3 = "https://studentloans.gov/myDirectLoan/mobile/repayment/loadLoans.action";
//        String urlTarget4 = "https://studentloans.gov/myDirectLoan/checkValidLogin.action";
//
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("https://studentloans.gov/myDirectLoan/mobile/repayment/repaymentEstimator.action")
//                .build();
//
//        Map<String, List<String>> headersMap = new LinkedHashMap<>();
//        Response getresponse;
//
//        try {
//            getresponse = client.newCall(request).execute();
//            headersMap = getresponse.headers().toMultimap();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String authXSRFtoken = headersMap.get("Set-Cookie").get(1);
//        String splitToken[] = authXSRFtoken.split(";");
//        String secondSplitToken[] = splitToken[0].split("=");
//        authXSRFtoken=secondSplitToken[1];
//
//        String sessionIDtoken = headersMap.get("Set-Cookie").get(0);
//        String splitIdToken[] = sessionIDtoken.split(";");
//        sessionIDtoken=splitIdToken[0];
//
//        String rawResponce = "";
//        JSONObject jsonResponce = new JSONObject();
//
////      login format UTF-8 web encoding: "userId=asdasdf%40aaa.comdd&password=Password34589asd&from=&id=";
//
//        try {
//            username = URLEncoder.encode(username, "UTF-8");
//            password = URLEncoder.encode(password, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        String POSTtextLogin = "userId=" + username + "&password=" + password + "&" + "from=" + "&" + "id=";
//
//        RequestBody body = RequestBody.create(MediaType.parse(POSTtextLogin), POSTtextLogin);
//
//
//        Request postRequest = new Request.Builder()
//                .url(urlTarget3)
//                .post(body)
//                .addHeader("Host", "studentloans.gov")
//                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0")
//                .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
//                .addHeader("Accept-Language", "en-US,en;q=0.5")
////                .addHeader("Accept-Encoding", "gzip, deflate, br")
//                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                .addHeader("X-XSRF-TOKEN", authXSRFtoken)
//                .addHeader("X-Requested-With", "XMLHttpRequest")
//                .addHeader("Referer", "https://studentloans.gov/myDirectLoan/mobile/repayment/repaymentEstimator.action")
//                .addHeader("Cookie", sessionIDtoken)
//                .build();
//
//
//        Response response = null;
//        try {
//            response = client.newCall(postRequest).execute();
//            rawResponce = response.body().string();
//            jsonResponce = new JSONObject(rawResponce);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        return rawResponce;
//    }
//
//
//    @Override
//    public void run()
//    {
//        Fragment_SLALogin.results = Go(username, password);
//    }
//}