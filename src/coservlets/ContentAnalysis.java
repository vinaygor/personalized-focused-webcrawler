package coservlets;

public class ContentAnalysis
{
	public int calculate(StringBuilder urlString, String searchString)
	{	
		int length=searchString.length();
		int count=0;
		int i=0;
		    for(i=0;i<urlString.length();i++)
	        {
	        	String temp1="";
	        	if(i<=urlString.length()-length)
	        	temp1=urlString.substring(i,i+length);
	        	if(temp1.equals(searchString))
	        		count++;
	        }
		return count;
	}
}
