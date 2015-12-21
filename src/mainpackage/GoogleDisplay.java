package mainpackage; 
import mainpackage.*;


import coservlets.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sun.xml.internal.fastinfoset.tools.PrintTable;


@WebServlet("/DisplayGoogleResults")
public class GoogleDisplay extends HttpServlet{
	
	static final String USER = "root";
    static final String PASS = "";
    static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
    static final String DB_URL="jdbc:mysql://localhost/crawler";
    @Override
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html");
		String searchString = request.getParameter("searchstring");
		String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
		String query = searchString;
		String charset = "UTF-8";
		try
		{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
		Statement stmt = conn.createStatement();
		ResultSet rs=null;
		String sql="";
		int max=1000;
		
	    
		
		URL url = new URL(address + URLEncoder.encode(query, charset));
		Reader reader = new InputStreamReader(url.openStream(), charset);
		GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
 
		// Show title and URL of 1st result and the the 4th results
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
		           	+"<H1>GOOGLE RESULTS</H1>"
		           	);
		sb.append("<h2><a href='"+results.getResponseData().getResults().get(0).getUrl()+"'>"+results.getResponseData().getResults().get(0).getUrl()+"</h2>\n");
		sql = "insert into crawledlinks values('"+searchString+"','"+results.getResponseData().getResults().get(0).getUrl()+"','"+Integer.toString(max)+"')";
		stmt.executeUpdate(sql);
		max--;
		sb.append("<h2><a href='"+results.getResponseData().getResults().get(1).getUrl()+"'>"+results.getResponseData().getResults().get(1).getUrl()+"</h2>\n");
		sql = "insert into crawledlinks values('"+searchString+"','"+results.getResponseData().getResults().get(1).getUrl()+"','"+Integer.toString(max)+"')";
		stmt.executeUpdate(sql);
		max--;
		sb.append("<h2><a href='"+results.getResponseData().getResults().get(2).getUrl()+"'>"+results.getResponseData().getResults().get(2).getUrl()+"</h2>\n");
		sql = "insert into crawledlinks values('"+searchString+"','"+results.getResponseData().getResults().get(2).getUrl()+"','"+Integer.toString(max)+"')";
		stmt.executeUpdate(sql);
		max--;
		sb.append("<h2><a href='"+results.getResponseData().getResults().get(3).getUrl()+"'>"+results.getResponseData().getResults().get(3).getUrl()+"</h2>\n");
		sql = "insert into crawledlinks values('"+searchString+"','"+results.getResponseData().getResults().get(3).getUrl()+"','"+Integer.toString(max)+"')";
		stmt.executeUpdate(sql);
		max--;
		
		url = new URL(address + URLEncoder.encode(query, charset)+"start=4");
		reader = new InputStreamReader(url.openStream(), charset);
		results = new Gson().fromJson(reader, GoogleResults.class);
		sb.append("<h2><a href='"+results.getResponseData().getResults().get(0).getUrl()+"'>"+results.getResponseData().getResults().get(0).getUrl()+"</h2>\n");
		sql = "insert into crawledlinks values('"+searchString+"','"+results.getResponseData().getResults().get(0).getUrl()+"','"+Integer.toString(max)+"')";
		stmt.executeUpdate(sql);
		max--;
		sb.append("<h2><a href='"+results.getResponseData().getResults().get(1).getUrl()+"'>"+results.getResponseData().getResults().get(1).getUrl()+"</h2>\n");
		sql = "insert into crawledlinks values('"+searchString+"','"+results.getResponseData().getResults().get(1).getUrl()+"','"+Integer.toString(max)+"')";
		stmt.executeUpdate(sql);
		max--;
		sb.append("<h2><a href='"+results.getResponseData().getResults().get(2).getUrl()+"'>"+results.getResponseData().getResults().get(2).getUrl()+"</h2>\n");
		sql = "insert into crawledlinks values('"+searchString+"','"+results.getResponseData().getResults().get(2).getUrl()+"','"+Integer.toString(max)+"')";
		stmt.executeUpdate(sql);
		max--;
		sb.append("<h2><a href='"+results.getResponseData().getResults().get(3).getUrl()+"'>"+results.getResponseData().getResults().get(3).getUrl()+"</h2>\n");
		sql = "insert into crawledlinks values('"+searchString+"','"+results.getResponseData().getResults().get(3).getUrl()+"','"+Integer.toString(max)+"')";
		stmt.executeUpdate(sql);
		max--;
		
		url = new URL(address + URLEncoder.encode(query, charset)+"start=8");
		reader = new InputStreamReader(url.openStream(), charset);
		results = new Gson().fromJson(reader, GoogleResults.class);
		sb.append("<h2><a href='"+results.getResponseData().getResults().get(0).getUrl()+"'>"+results.getResponseData().getResults().get(0).getUrl()+"</h2>\n");
		sql = "insert into crawledlinks values('"+searchString+"','"+results.getResponseData().getResults().get(0).getUrl()+"','"+Integer.toString(max)+"')";
		stmt.executeUpdate(sql);
		max--;
		sb.append("<h2><a href='"+results.getResponseData().getResults().get(1).getUrl()+"'>"+results.getResponseData().getResults().get(1).getUrl()+"</h2>\n");
		sql = "insert into crawledlinks values('"+searchString+"','"+results.getResponseData().getResults().get(1).getUrl()+"','"+Integer.toString(max)+"')";
		stmt.executeUpdate(sql);
		max--;
		BingSearch bs = new BingSearch();
		sb.append("<H1> Bing Results</H1>");
		sb.append(bs.getBingResults(searchString));
		sb.append("</div></center>\n"
				+"</body></html>");
	//	System.out.println(sb);
		PrintWriter out = response.getWriter();
		out.println(sb.toString());
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
