package db;

import android.content.Context;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * Created by loke09 on 10/01/17.
 */
public class WebserviceConnect {
    public static String getdatafromserver(Context context, String url1) {
        // TODO Auto-generated method stub
        String response = null;
        try {
            HttpClient client = getClient();
            Gson gson = new Gson();
            String url = url1;

            HttpPost httpPost = new HttpPost();
            httpPost.setURI(new URI(url));

            httpPost.setHeader("Content-Type", "application/json");
//            httpPost.setHeader("Username", "" + LMSConstant.userName);
//            httpPost.setHeader("Token", LMSConstant.agent_token);

            HttpResponse httpRespons = client.execute(httpPost);
            HttpEntity httpEntity = httpRespons.getEntity();
            response = readStream(httpEntity.getContent());
            // response =
        } catch (IllegalStateException e) {
            try {
            } catch (Exception w) {
            }
            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return response;
    }
    public static String readStream(InputStream in) throws IOException {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        String line = "";
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
        return sb.toString();
    }

    public static HttpClient getClient() throws KeyStoreException, IOException,
            NoSuchAlgorithmException, CertificateException,
            KeyManagementException, UnrecoverableKeyException {

//        //allowAllSSL();
//        HttpParams httpParameters = new BasicHttpParams();
//        // Set the timeout in milliseconds until a connection is established.
//        // The default value is zero, that means the timeout is not used.
//        int timeoutConnection = 30000;
//        //HttpConnectionParams.setConnectionTimeout(httpParameters,
//        //	timeoutConnection);
//
//        // Set the default socket timeout (SO_TIMEOUT)
//        // in milliseconds which is the timeout for waiting for data.
//        int timeoutSocket = 30000;
//        //HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
//
//        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//        trustStore.load(null, null);
//
//        SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
//        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//
//        //HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
//        //HttpProtocolParams.setContentCharset(httpParameters, HTTP.UTF_8);
//
//        SchemeRegistry registry = new SchemeRegistry();
//        registry.register(new Scheme("http", PlainSocketFactory
//                .getSocketFactory(), 80));
//        registry.register(new Scheme("https", sf, 443));
//
//        ClientConnectionManager ccm = new ThreadSafeClientConnManager(
//                httpParameters, registry);

        HttpClient client = new DefaultHttpClient();

        return client;
    }

}
