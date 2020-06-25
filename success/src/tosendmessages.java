import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class tosendmessages extends HttpServlet {
public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException
{
	String username = req.getParameter("employee");
	String subject = req.getParameter("subject");
	String body = req.getParameter("body");
	PrintWriter out = res.getWriter();
	 try
	   {Class.forName("oracle.jdbc.driver.OracleDriver");
		   Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe","system","sql");
		   Statement stm = con.createStatement();
		   stm.executeUpdate("Insert into employeemessages values('"+username+"','"+subject+"','"+body+"')");
		   out.println("message sent successfully");
		   con.close();
	   }catch(Exception e)
	   {
		   out.println("Exception is"+e);
	   }
}
}
