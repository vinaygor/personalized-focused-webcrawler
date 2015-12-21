package mainpackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;

import org.apache.commons.codec.binary.Base64;

public class BingSearch {
	StringBuffer tosend=new StringBuffer();
	
	 public String getBingResults(String searchstring) {
         // TODO Auto-generated method stub
         //--------------------------------------Bing search------------------------------
         String searchText = searchstring;
         searchText = searchText.replaceAll(" ", "%20");
         String accountKey="4OWRPC6hQOLiRWr64tqqGZILoq3GhG4/TUAKa4vCySc";
       
         byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
         String accountKeyEnc = new String(accountKeyBytes);
         URL url;
         try {
             url = new URL(
                     "https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Web?Query=%27" + searchText + "%27&$top=50&$format=Atom");
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("GET");
         conn.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
         System.out.println("Done..");
         //conn.setRequestProperty("Accept", "application/json");
         BufferedReader br = new BufferedReader(new InputStreamReader(
                 (conn.getInputStream())));
         StringBuffer sb = new StringBuffer("");
         int counter=0;
         String output;
         System.out.println("Output from Server .... \n");
         char[] buffer = new char[4096];
         while ((output = br.readLine()) != null) {
             sb.append(output);
         //        text.append(link + "\n\n\n");//Will print the google search links
             //}    
         }
        
        int start=0;
        int end=sb.length();
         conn.disconnect();
         //System.out.println(sb);
         StringBuilder ss=new StringBuilder();
         		ss.append(sb.substring(start));
         for(int i=0;i<10;i++)
         {
        
         	ss.delete(0, ss.length());
         	ss.append(sb.substring(start));
         	//System.out.println(ss);
         	int find = ss.indexOf("</d:Url>");
         	find=find-1;
         	int find1=+find;
	            StringBuffer url1=new StringBuffer();
	            //url1.delete(0, url1.length());
	            while(ss.charAt(find)!='>')
	            {
	            	url1.append(ss.charAt(find));
	            	find--;
	            }
	            //System.out.println("find==="+find);
	            url1.reverse();
	            System.out.println(url1);
	            tosend.append("<h2><a href='"+url1.toString()+"'>"+url1.toString()+"</h2>\n");
	            //System.out.println("url1.length()===="+url1.length());
	            start=start+find1+9;
	            //System.out.println("start=="+start);
	            
         }
        
         } catch (MalformedURLException e1) {
             // TODO Auto-generated catch block
             e1.printStackTrace();
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
         return tosend.toString();
	 }
        
        
}
