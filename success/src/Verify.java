import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
public class Verify extends HttpServlet {
public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException
{
	res.setContentType("text/html");
String username = req.getParameter("username");
String password = req.getParameter("password");
String designation = req.getParameter("designation");
PrintWriter out = res.getWriter();
/*RequestDispatcher rd = req.getRequestDispatcher("square");
rd.forward(req, res);*/
try
{
	Class.forName("oracle.jdbc.driver.OracleDriver");
	Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe","system","sql");
	//out.println("connected");
	Statement smt = con.createStatement();
	//out.println(username);
	//out.println(password);
	/*choosing table in database according to designation*/
	ResultSet rs = smt.executeQuery("Select * from "+designation+"passwords where username = '"+username+"' and password = '"+ password+"'");
	if(rs.next())
	{
		HttpSession session = req.getSession();
		session.setAttribute("username", username);
		session.setAttribute("designation", designation);/*for leader and manager so that delete and accept servlets will know it
		storingletterindatabase servlet knows whether it is leader or employyee*/
		if(designation.equals("employee"))
		res.sendRedirect("employeehomepage.html");
		else if(designation.equals("leader"))
		{
			res.sendRedirect("leaderhomepage.html");
		}
		else if(designation.equals("manager"))
		{
			rs = smt.executeQuery("Select * from managerletters");
			int flag = 0;
			while(rs.next())
			{
	        flag = 1;
         out.println("username : "+rs.getString(1)+" ");
         out.print("subject : "+rs.getString(2)+" ");
         out.print("body : "+rs.getString(3)+" ");
         out.print("<form action = 'accept'><input type = 'hidden' name = 'username' value = '"+rs.getString(1)+"'><input type = 'hidden' name = 'subject' value = '"+rs.getString(2)+"'><input type = 'submit' value = 'accept'></form> ");
         out.print("<form action = 'delete'><input type = 'hidden' name = 'username' value = '"+rs.getString(1)+"'><input type = 'hidden' name = 'subject' value = '"+rs.getString(2)+"'><input type = 'submit' value = 'reject'></form> ");
         out.println("------------------------------");
         out.print("<br/>");
			}
			if(flag==0)
				out.println("no messages to display");
		}
		
		
	}
	else
	{
		out.println("invalid details...please login again");
		RequestDispatcher rd=req.getRequestDispatcher("login.html");
		rd.include(req, res);
	}
	con.close();
}catch(Exception e)
{
	out.println("Our application is failed due to:" + e);
}
}
}
