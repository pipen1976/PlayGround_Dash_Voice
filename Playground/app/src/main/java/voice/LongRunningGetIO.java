package voice;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;

public class LongRunningGetIO extends AsyncTask<String, Void, String>
{

    public interface OnResultCallback
    {
        void onSuccess(String text);
        void onError(String text);
    }

    String originalText;
    OnResultCallback callback;

    public LongRunningGetIO(String originalText, OnResultCallback callback)
    {
        this.originalText = originalText;
        this.callback = callback;
    }

    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException
    {
        InputStream in = entity.getContent();

        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n > 0)
        {
            byte[] b = new byte[4096];
            n = in.read(b);
            if (n > 0)
                out.append(new String(b, 0, n));
        }

        return out.toString();
    }


    @Override
    protected String doInBackground(String... params)
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();

        String text = "";
        try
        {
            HttpResponse response = null;
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost("https://api.dialogflow.com/v1/query?v=20150910");
            final String CODEPAGE = "UTF-8";


            request.setEntity(new StringEntity("{\n" +
                    "\"contexts\": [\"bot\"],\"lang\": \"es\",\n" +
                    "\"query\": \"" + originalText + "\"" +
                    ",\"sessionId\": \"12345\",\"timezone\": \"America/New_York\"\n" +
                    "}"));


            request.setHeader(new BasicHeader("Content-Type", "application/json"));
            request.setHeader(new BasicHeader("Authorization", "Bearer 43c322c2d52a44e99586f383fd31ad4f"));

            response = client.execute(request);

            HttpEntity entity = response.getEntity();


            text = getASCIIContentFromEntity(entity);


        }
        catch (Exception e)
        {
            return e.getLocalizedMessage();
        }

        return text;
    }


    protected void onPostExecute(String results)
    {
        play_i.playground.responses.AIResponse response = new com.google.gson.Gson().fromJson(results, play_i.playground.responses.AIResponse.class);
        if (results != null && response.getResult() != null && response.getResult().getFulfillment() != null)
        {
            if (callback != null)
                callback.onSuccess(response.getResult().getFulfillment().getSpeech());
        }
        else
        {
            if (callback != null)
                callback.onError("no entiendo");
        }
    }
}
