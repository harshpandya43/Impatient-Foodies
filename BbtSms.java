
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.lang.*;

public class BbtSms {
  
  public void sendSMS(String name,String number,int time)
  {
	  QueryString qs = new QueryString("workingkey","1781e82a84062ypt0218");
	    qs.add("sender","CICDMO");
	    qs.add("to",number);
	    qs.add("message",BbtSms.getMessage(name,time));
  String urltext = "http://alerts.prioritysms.com/api/web2sms.php?"+qs;
  
  
    BufferedReader in = null;
    try {
      URL url = new URL(urltext);
      in = new BufferedReader(new InputStreamReader(url.openStream()));

      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        System.out.println(inputLine);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
  
  public static String getMessage(String name,int time)
  {StringBuffer mymsg = new StringBuffer("Hi ");
   String message=name;
   mymsg.append(message+" "+"your table shall be vacated in ");
   mymsg.append(time);
   mymsg.append(" "+"minutes");
   message=mymsg.toString();
   System.out.println(message);
   return message;
  }
  
}
  class QueryString {

	  private String query = "";

	  public QueryString(String name, String value) {
	    encode(name, value);
	  }

	  public void add(String name, String value) {
	    query += "&";
	    encode(name, value);
	  }

	  private void encode(String name, String value) {
	    try {
	      query +=URLEncoder.encode(name, "UTF-8");
	      query += "=";
	      query += URLEncoder.encode(value, "UTF-8");
	    } catch (UnsupportedEncodingException ex) {
	      throw new RuntimeException("Broken VM does not support UTF-8");
	    }
	  }

	  public String getQuery() {
	    return query;
	  }

	  public String toString() {
	    return getQuery();
	  }

}
 