package coservlets;

public class SeedPages {
	String educationseeds[] = {"http://www.indiastudychannel.com","http://www.somaiya.edu/vidyavihar/","http://www.caclubindia.com"};
	String technologyseeds[] = {"http://news.cnet.com","http://gadgets.ndtv.com","http://tech2.in.com"};
	String sportsseeds[] = {"http://www.espncricinfo.com","http://www.goal.com","http://www.ibnlive.in.com/sports/","http://www.in.news.yahoo.com/sports/"};
	public String[] selectSeeds(String type)
	{
		if(type.equals("technology"))
		{
			return technologyseeds;
		}
		else if(type.equals("education"))
		{
			return educationseeds;
		}
		else if(type.equals("sports"))
		{
			return sportsseeds;
		}
		return null;
	}
}
