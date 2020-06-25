
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class StoringLetterInDataBase extends HttpServlet {
public void service(HttpServletRequest req,HttpServletResponse res) throws IOException
{
	String subject = req.getParameter("subject"); //string can't take 1000 size i guess
	/*char[] body = new char[1000];
	body = req.getParameter("body").toCharArray();*/
	String body = req.getParameter("body");
	PrintWriter out = res.getWriter();
	HttpSession session = req.getSession();
	String username = (String) session.getAttribute("username");
	/*out.println("your username is"+username);
	out.println("your body is"+req.getParameter("body"));
	out.println("your subject is"+req.getParameter("subject"));*/
	try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe","system","sql");
		out.println("connected");
		Statement stmt = con.createStatement();
		stmt.executeUpdate("Insert into employeeletters (username,subject,body,status) values('"+username+"','"+subject+"','"+body+"','"+"sent to leader"+"')");
		stmt.executeUpdate("Insert into leaderletters values('"+username+"','"+subject+"','"+body+"')");
		out.println("successfully sent");
		con.close();//if you are not closing the connection then it is printing two tables i think it is spliting table for two users
	}catch(Exception e)
	{
		out.print(e);
	}
}
}
