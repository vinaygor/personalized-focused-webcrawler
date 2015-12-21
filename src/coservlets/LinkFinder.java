package coservlets;

import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.sql.*;


public class LinkFinder {
	String link[] = new String[1000];
	int inserted,visited;
	String searchstring = new String();
	static final String USER = "root";
    static final String PASS = "";
    static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
    static final String DB_URL="jdbc:mysql://localhost/crawler";
	public String searchString (String string, String type) throws Exception{
		String orderLinks[]=new String[1000];
		int weight[]=new int[1000];
		int j=0,z=0;
		System.out.println(type);
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
		Connection conn1 = DriverManager.getConnection(DB_URL,USER,PASS);
		searchstring = string;
		Statement stmt1 = conn1.createStatement();
		Statement stmt = conn.createStatement();
		ResultSet rs1=null;
		ResultSet rs=null;
		String dbString="";
		boolean flag=false;
		rs1=stmt1.executeQuery("Select DISTINCT SearchString from crawledlinks ");
		while(rs1.next()&&flag==false)
		{
			dbString=rs1.getString("SearchString");
			if(dbString.equalsIgnoreCase(searchstring))
				flag=true;
		}
		
		
		searchstring=searchstring.toLowerCase();
		SeedPages sp = new SeedPages();
		String templinks[] = sp.selectSeeds(type);
		if (templinks!=null)
		{
			for(int i=0;i<templinks.length;i++)
				link[i]=templinks[i];
			inserted=templinks.length;
		}
		else
			return "<!DOCTYPE html>\n" +
	           "<html>\n" +
	           "<head>\n"
	           	+"<title> Links Crawled </title>\n"
	           	+"<link rel=\"stylesheet\" href=\"script.css\"/>\n"
	           	+"</head>\n"
	           + "<body>\n"
	           	+"<center><iframe src=\"logo.html\" scrolling=\"no\" frameborder=\"0\" width=\"920\" height=\"120\"></iframe></center>\n"
	           	+"<a href=\"index.html\" class=\"right\"><h3>Back</h3></a><br/><br/><br/>\n"
	           	+"<center><div class=\"body\">\n"+"<h2>Please Provide An Input</h2>"+"</div></center>\n"
				+"</body></html>";
			
		//How many links have been inserted
		visited=1;//First link has already been started to crawl
		
		
		if(flag==false)
		crawl(link[0]);
		
		rs=stmt.executeQuery("Select RelatedLinks,LinkWeight from crawledlinks where SearchString='"+string+"' order by LinkWeight desc");
		
		while(rs.next())
		{
			orderLinks[j++]=rs.getString("RelatedLinks");
			weight[z++]=rs.getInt("LinkWeight");
		}

		for(int h=0;h<j;h++)
			for(int g=0;g<j-1;g++)
			{
			if(weight[g]<weight[g+1])
			{
				String temp="";
				temp=orderLinks[g];
				orderLinks[g]=orderLinks[g+1];
				orderLinks[g+1]=temp;
			}
			}
		int i=0;
		//System.out.println("LINKS VISITED IN TOTAL:");
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html>\n" +
		           "<html>\n" +
		           "<head>\n"
		           	+"<title> Links Crawled </title>\n"
		           	+"<link rel=\"stylesheet\" href=\"script.css\"/>\n"
		           	+"</head>\n"
		           + "<body>\n"
		           	+"<center><iframe src=\"logo.html\" scrolling=\"no\" frameborder=\"0\" width=\"920\" height=\"120\"></iframe></center>\n"
		           	+"<a href=\"index.html\" class=\"right\"><h3>Home</h3></a><br/><br/><br/>\n"
		           	+"<center><div class=\"body\">\n"
		           	);
		if(j==0)
			sb.append("<h2>No Results Found</h2>");
		for(i=0;i<j;i++)
			sb.append("<h2><a href='"+orderLinks[i]+"'>"+orderLinks[i]+"</h2>\n");
		sb.append("</div></center>\n"
				+"</body></html>");
		
		
		return sb.toString();
		
		
	}
	public void crawl (String name) throws Exception
	{
		try
		{	
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			Statement stmt = conn.createStatement();
			
			
			System.out.println("CRAWLING PAGE =>"+name);
			URL url = new URL(name);
		    BufferedReader in = new BufferedReader(
		    new InputStreamReader(url.openStream()));
		    String current="";
		    int maxlinks=0;
		    String urlString=new String();
		    StringBuilder sb = new StringBuilder();
		    while((current = in.readLine()) != null)
		    {
		    	urlString+=current.toLowerCase();
		    	sb.append(current+'\n');
		    }
		    /*
		    if(sb.length()>1000)
		    	sb=new StringBuilder(sb.substring(0, 999));
		    stat.executeUpdate("insert into Links values('"+name+"','"+sb.toString()+"')");
		    conn.close();*/
		    int i=0;
		    int c=0;
		    
		    //System.out.println(sb);
		    //System.out.println(sb.toString().length());
		    //System.out.println(searchstring.length());
		    //String src = sb.toString();
		    //System.out.println(searchstring);
		    WordFrequency wf = new WordFrequency();
		    c= wf.findFrequency(urlString, searchstring);
		    /*
		    ContentAnalysis ca = new ContentAnalysis();
		    int a= ca.calculate(sb, searchstring);
		    System.out.println(sb);
		    System.out.println(searchstring);
		    System.out.println("a===="+a);*/
		    //System.out.println(c);
		    String sql="";
		    if(c!=0)
		    {
		    	sql = "insert into crawledlinks values('"+searchstring+"','"+name+"','"+Integer.toString(c)+"')";
		    stmt.executeUpdate(sql);
		    }
		    Scanner scan = new Scanner(sb.toString());
		    
		    while(scan.hasNextLine())
		    {
		    	current = scan.nextLine();
		    	String lowercasecopy=current.toLowerCase();//String for comparison
				if(maxlinks<3)
			    	if(lowercasecopy.contains(searchstring)&&lowercasecopy.contains("href=")&&!lowercasecopy.contains(".css")&&!lowercasecopy.contains(".pdf"))
					{
						//System.out.println("Found in==");
						int n=lowercasecopy.indexOf(searchstring);
						n=n-3;
						String temp = new String();
						for(i=n;i>=0;i--)
						{
							temp=Character.toString(current.charAt(i))+temp;
							if(temp.contains("href="))
								break;
						}
						if(i==-1)
						{
							//System.out.println("i=-1");
							temp="";
							for(i=n;i<current.length();i++)
							{
								temp=Character.toString(current.charAt(i))+temp;
								if(temp.contains("href="))
								break;
							}
						}
						//System.out.println("i=="+i);
						int j=0;
						//System.out.println("i+6==="+current.charAt(i)+"  i+6==="+current.charAt(i+6));
						for(j=i+6;j<current.length();j++)
							if(lowercasecopy.charAt(j)=='\''||lowercasecopy.charAt(j)=='\"')
								break;
						String linktobeinserted = current.substring(i+6,j);
						//System.out.println("link to be inserted="+linktobeinserted);
						boolean check = checkIfInserted(linktobeinserted);
						//System.out.println(check);
						if(!check&&inserted<100)
							if(!linktobeinserted.startsWith("http://"))
							{
								//System.out.println("Not starts with http");
								String tempstr = new String();
								String tempmatch = name+linktobeinserted;
								//System.out.println("temp match string="+tempmatch);
								int doubleslashcount=0;
								int count=0;
								int time=0;
								
								for(count=0;count<tempmatch.length();count++)
								{
									//System.out.println("time=="+time+"  doubleslashcount=="+doubleslashcount);
									if(tempmatch.charAt(count)=='/')
									{
										time++;
										if(time==2)
										{
											doubleslashcount++;
											if(doubleslashcount==2)
												count++;
										}
									}
									else
									{
										time=0;
									}
										
									//System.out.print(count+", ");
									tempstr +=Character.toString(tempmatch.charAt(count));
								}
								//System.out.println();
								//System.out.println("trying to insert===="+tempmatch);
								link[inserted++] = tempstr;
								//System.out.println("double slash count="+doubleslashcount);
								//System.out.println("link inserted=="+tempstr);
							}
							else
							{
								link[inserted++]=linktobeinserted;
								//System.out.println("link inserted=="+linktobeinserted);
							}
						maxlinks++;
					}
		    }
		    if(visited<inserted)
	        		crawl(link[visited++]);
			else
				return;
		}
		catch(Exception e)
		{
			System.out.println("Exception caught"+e);
			if(visited<inserted)
				crawl(link[visited++]);
			else
				return;
		}
	}
	public boolean checkIfInserted(String name)
	{
		int i=0;
		for(i=0;i<inserted;i++)
		{
			if(link[i].contains(name))
			{
				return true;
			}
		}
		return false;
	}
}
