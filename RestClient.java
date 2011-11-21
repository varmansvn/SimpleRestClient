/**
 * User: varman
 * Date: 11/21/11
 * Time: 10:58 AM
 * Description:
 * This class is just used to show the simplest way to send request of REST to server.
 * It does not contain any solid architecture design at all.
 * History:
 * 11/21/11  varman Creation
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class RestClient {
    private static final int MAX_LOOP = 1000;
    public static void main(String[] args){
        HttpURLConnection httpConnection = null;
        BufferedReader readingBuffer  = null;
        StringBuilder stringBuilder = null;
        String line = null;
        URL resourceUri = null;
        int cycle = 0;

        try {
            // the uri of the resource on the server
            resourceUri = new URL("https://graph.facebook.com/19292868552");

            //Set up the properties of httpConnection before connecting.
            httpConnection = (HttpURLConnection)resourceUri.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoOutput(true);
            httpConnection.setReadTimeout(10000);

            // connecting to server
            httpConnection.connect();

            //read the result from the server
            readingBuffer  = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            stringBuilder = new StringBuilder();

            // read the response from server.
            while ((line = readingBuffer.readLine()) != null)
            {
               cycle = cycle + 1;
               if(MAX_LOOP <= cycle){
                  throw new IOException("Response buffer from server is too big to handle.");
               }
               stringBuilder.append(line + '\n');
            }

            System.out.println(stringBuilder.toString());

        } catch (MalformedURLException e) {
                 e.printStackTrace();
        } catch (ProtocolException e) {
                 e.printStackTrace();
        } catch (IOException e) {
                 e.printStackTrace();
        }
        finally
        {
            //close the httpConnection, set all objects to null
            httpConnection.disconnect();
            readingBuffer = null;
            httpConnection = null;
            stringBuilder = null;
            resourceUri = null;
        }
    }
}
