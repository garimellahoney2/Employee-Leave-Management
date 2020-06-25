import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendRedirectClass1 extends HttpServlet {
 public	void service(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		int i = Integer.parseInt(req.getParameter("num1"));
		int j = Integer.parseInt(req.getParameter("num2"));
		int k = i*j;
		/*HttpSession session = req.getSession();
		session.setAttribute("k", k);*/
		Cookie cookie = new Cookie("k",k+"");
		res.addCookie(cookie);
		PrintWriter out = res.getWriter();
		out.println("value of k is"+k);
		res.sendRedirect("square1");
	}
}
