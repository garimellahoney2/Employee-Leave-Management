import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class delete extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException
	{PrintWriter out = res.getWriter();
	String username = (String)req.getParameter("username");
	String subject = (String)req.getParameter("subject");
	out.println("rejected"+username);
	try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe","system","sql");
		//out.println("connected");
		Statement smt = con.createStatement();
		HttpSession session = req.getSession();
		String designation = (String)session.getAttribute("designation");
		if(designation.equals("leader"))
		{
		int status = smt.executeUpdate("delete leaderletters where username = '"+username+"'"+" AND subject = '"+subject+"'");
	    smt.executeUpdate("Update employeeletters set status = 'rejected from leader' where username = '"+username+"' and "+"subject = '"+subject+"'");
		out.print(status);
		}
		else if(designation.equals("manager"))
		{
			smt.executeUpdate("delete managerletters where username = '"+username+"'"+" AND subject = '"+subject+"'");
			smt.executeUpdate("Update employeeletters set status = 'rejected by manager' where username = '"+username+"' and "+"subject = '"+subject+"'");
			
		}
		con.close();
	}catch(Exception e)
	{
		out.println("Our application is failed due to:" + e);
	}
	}
}
