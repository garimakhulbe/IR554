	

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
 * Servlet implementation class RerankServlet
 */
@WebServlet("/RerankServlet")
public class RerankServlet extends HttpServlet {
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
    public RerankServlet() {
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
		
	    /* Populating the Probability table */
	    PopulateLatLongInProbabilityTable();
		
	    /* Printing the Probability table */
	    for(i=0;i<rows;i++)
	    {
	    	if(i==0)
	    	{
	    		out.println("Printing locations of 10 pizza places from route1(randomly generated latitude, longitude)");
	    	}
	    	if(i==10)
	    	{
	    		out.println("Printing locations of 10 pizza places from route2(randomly generated latitude, longitude)");
	    	}
	    	if(i==20)
	    	{
	    		out.println("Printing locations of 10 pizza places from route3(randomly generated latitude, longitude)");
	    	}
	    	
	    	toprint="<p>";
	    	
	    	for(j=0;j<cols;j++)
	    	{
	    		toprint += probabilitytable[i][j] + " ";
	    	}
	    	
	    	toprint += "</p>\n";
	    	out.println(toprint);
	    }
	    
	    /* Reranking the Probability table */
	    /* float to string : Float.toString(probabilityofroute1)
	     * string to float : Float.parseFloat("tbroute1");
	     * */
	    float probabilityofroute1 = Float.parseFloat(request.getParameter("tbroute1")); 
	    float probabilityofroute2 = Float.parseFloat(request.getParameter("tbroute2"));
	    float probabilityofroute3 = Float.parseFloat(request.getParameter("tbroute3"));
	    
	    float sumofprobabilities = probabilityofroute1 + probabilityofroute2 + probabilityofroute3;
	    
	    int fraction1 = (int)(probabilityofroute1*10/sumofprobabilities);
	    int fraction2 = (int)(probabilityofroute2*10/sumofprobabilities);
	    int fraction3 = (int)(probabilityofroute3*10/sumofprobabilities);
	    
	    /* "smoothing" */
	    if( (fraction1 +fraction2 + fraction3) != 10)
	    {
	    	int diff = 10 - (fraction1 +fraction2 + fraction3);
	    	fraction1 += diff; 
	    }
	    
	    /* printing final locations for route1 */
	    out.println("Printing" + Float.toString(fraction1) + " locations for route1\n");
	    for(i=0;i<fraction1;i++)
	    {
	    	out.println("{");
	    	out.println(probabilitytable[0+i][0]);
	    	out.println(probabilitytable[0+i][1]);
	    	out.println(probabilitytable[0+i][2]);
	    	out.println("}");
	    	out.println(" ");
	    }
	    
	    /* printing final locations for route2 */
	    out.println("<br>");
	    out.println("Printing" + Float.toString(fraction2) + " locations for route2\n");
	    for(i=0;i<fraction2;i++)
	    {
	    	out.println("{");
	    	out.println(probabilitytable[10+i][0]);
	    	out.println(probabilitytable[10+i][1]);
	    	out.println(probabilitytable[10+i][2]);
	    	out.println("}");
	    	out.println(" ");
	    }
	    out.println("\n");
	    
	    /* printing final locations for route3 */
	    out.println("<br>");
	    out.println("Printing" + Float.toString(fraction3) + " locations for route3\n");
	    for(i=0;i<fraction3;i++)
	    {
	    	out.println("{");
	    	out.println(probabilitytable[20+i][0]);
	    	out.println(probabilitytable[20+i][1]);
	    	out.println(probabilitytable[20+i][2]);
	    	out.println("}");
	    	out.println(" ");
	    }
	    out.println("\n");
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
