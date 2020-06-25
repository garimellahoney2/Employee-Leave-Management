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

public class accept extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException
	{PrintWriter out = res.getWriter();
	String username = (String)req.getParameter("username");
	out.println("accepted"+username);
	String subject = (String)req.getParameter("subject");
	out.println("accepted"+subject);
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
		out.println("insert into managerletters select * from leaderletters where username = '"+username+"' and subject = '"+subject+"'");
	    smt.executeUpdate("insert into managerletters select * from leaderletters where username = '"+username+"' and subject = '"+subject+"'");
	    out.println("Update employeeletters set status = 'accepted from leader' where username = '"+username+"',"+"subject = '"+subject+"'");
	    smt.executeUpdate("Update employeeletters set status = 'accepted from leader' where username = '"+username+"' and "+"subject = '"+subject+"'");
	    smt.executeUpdate("delete leaderletters where username = '"+username+"' and subject = '"+subject+"'");
	   // out.println("delete leaderletters where username = '"+username+"' and subject = '"+subject+"'");
		}
		else if(designation.equals("manager"))
		{ResultSet value = smt.executeQuery("Select unique_sequence.nextval from dual");
		value.next();
		int val = value.getInt(1);
		out.print("val"+val);
			smt.executeUpdate("Update employeeletters set status = 'accepted by manager',id = "+val+" where username = '"+username+"' and "+"subject = '"+subject+"'");
			smt.executeUpdate("delete managerletters where username = '"+username+"' and subject = '"+subject+"'");
		}
		con.close();
	}catch(Exception e)
	{
		out.println("Our application is failed due to:" + e);
	}
	}
}
