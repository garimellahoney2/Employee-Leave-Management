import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class letterstatus extends HttpServlet {
public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException
{ PrintWriter out = res.getWriter();
HttpSession session = req.getSession();
   String username = (String) session.getAttribute("username");
	try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe","system","sql");
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery("Select * from employeeletters where username = '"+username+"'");
		while(rs.next())
		{
			//out.print("username "+rs.getString(1)+" ");
	         out.println("subject : "+rs.getString(2)+" ");
	         out.println("------------------------------");
	         out.println("body : "+rs.getString(3)+" ");
	         out.println("status : "+rs.getString(4)+" ");
	         if(rs.getString(4).equals("accepted by manager"))
	        	 out.println("unique id : "+rs.getString(5));
	         out.println("------------------------------");
	        out.println();
		}
		con.close();
	}catch(Exception e)
	{
		out.println("error is"+e);
	}
}
}
