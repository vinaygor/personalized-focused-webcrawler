package coservlets;

public class WordFrequency {
	public int findFrequency(String urlString, String searchstring)
	{
		int i=0,c=0;
		for(i=0;i<urlString.length();i++)
        {
	    	String temp1 =new String();
	    	if(i<urlString.length()-searchstring.length())
	    		temp1=urlString.substring(i, i+searchstring.length());
	    	//System.out.println(temp1);
	    	if(temp1.equalsIgnoreCase(searchstring))
	    	{
	    		//System.out.println("kahvfuahvsfd");
	    			c++;
	    	}
        }
		return c;
	}
}
