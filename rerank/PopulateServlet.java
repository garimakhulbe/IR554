	

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Random;

/**
 * Servlet implementation class PopulateServlet
 */
@WebServlet("/PopulateServlet")
public class PopulateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String[][] probabilitytable = new String[30][3];
	float minrand = 50.0f;
	float maxrand = 100.0f;
	int i = 0;
	int j = 0;
	int rows = 30;
	int cols = 3;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public PopulateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String toprint="";
		
		/* setting 0th column to probabilities */
		for(i=0;i<10;i++)
		{
			probabilitytable[i][0]=request.getParameter("tbroute1");
		}
		for(i=10;i<20;i++)
		{
			probabilitytable[i][0]=request.getParameter("tbroute2");
		}
		for(i=20;i<30;i++)
		{
			probabilitytable[i][0]=request.getParameter("tbroute3");
		}
		
		// Set response content type
	    response.setContentType("text/html");
	
	    out.println("<h1>" + "Message from Rerank Servlet" + "</h1>");
		
	    PopulateLatLongInProbabilityTable();
		
	    for(i=0;i<rows;i++)
	    {
	    	if(i==0)
	    	{
	    		out.println("Printing 10 probabilities for route1(randomly generated latitude, longitude)");
	    	}
	    	if(i==10)
	    	{
	    		out.println("Printing 10 probabilities for route2(randomly generated latitude, longitude)");
	    	}
	    	if(i==20)
	    	{
	    		out.println("Printing 10 probabilities for route3(randomly generated latitude, longitude)");
	    	}
	    	
	    	toprint="<p>";
	    	
	    	for(j=0;j<cols;j++)
	    	{
	    		toprint += probabilitytable[i][j] + " ";
	    	}
	    	
	    	toprint += "</p>\n";
	    	out.println(toprint);
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	public void PopulateLatLongInProbabilityTable()
	{
		Random rand = new Random();
				
		/* populating probabilities, lat, long for the 30 routes 
		 * 
		 * 0.4, lat1, long1
		 * ..
		 * ..
		 * 0.4, lat1, long9
		 * 
		 * 
		 * 
		 * 0.3, lat10, long10
		 * ..
		 * ..
		 * 0.3, lat20, long20
		 * 
		 * 
		 *
		 * 0.2, lat30, long30
		 * ..
		 * ..
		 * 0.2, lat30, long30
		 * */
		/* setting 1st and 2nd columns to latitudes and longitudes */
		for(i=0;i<rows;i++)
		{
			for(j=1;j<cols;j++)
			{
				float randomfloat = rand.nextFloat() * (maxrand - minrand) + minrand;
				probabilitytable[i][j] = Float.toString(randomfloat); 
			}
		}	    
	}

}
