package Controller;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12 on 21.03.2017.
 */
@WebServlet(name = "AreaCheckServlet", urlPatterns = "/checking")
public class AreaCheckServlet extends HttpServlet {
    private ServletConfig config;
    private List<Point> list=null;
    @Override
    public void init (ServletConfig config) throws ServletException
    {
        this.config = config;
    }
    @Override
    public void destroy() {}
    @Override
    public ServletConfig getServletConfig()
    {
        return config;
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(list==null){
            list=new ArrayList<Point>();
            //config.getServletContext().setAttribute("list",list);
        }
        list.add(new Point(Integer.parseInt(request.getParameter("x_coord")),Integer.parseInt(request.getParameter("y_coord"))));
        config.getServletContext().setAttribute("list",list);
        String pageTitle="Servlet example";
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + "Point checking" + "</title>");
        out.println("</head>");
        out.println("<body bgcolor=white>");
        out.println("<table border=\"1\" bordercolor=\"red\" >");
        out.println("<tr>");
            out.println("<td>");
                out.println("X coordinate");
            out.println("</td>");
            out.println("<td>");
                 out.println("Y coordinate");
            out.println("</td>");
            out.println("<td>");
                out.println("Radius");
            out.println("</td>");
            out.println("<td>");
                out.println("Entrance");
            out.println("</td>");
        out.println("</tr>");
        for(int i=0;i<list.size();i++){
            out.println("<tr>");
                out.println("<td>");
                    out.println(list.get(i).x);
                out.println("</td>");
                out.println("<td>");
                    out.println(list.get(i).y);
                out.println("</td>");
            out.println("<td>");
            out.println(request.getParameter("chBox"));
            out.println("</td>");
                out.println("<td>");
                    if(checkArea(list.get(i).x,list.get(i).y,Integer.parseInt(request.getParameter("chBox")))){
                        out.println("Yes");
                    }
                    else{
                        out.println("No");
                    }

                out.println("</td>");
            out.println("</tr>");

        }
        out.println("</table>");
        out.println("<a href=\"/GlassFishApplication_war_exploded\">Do you want add another point?</a>");

        out.println("</body>");
        out.println("</html>");
        //request.getRequestDispatcher("/WEB-INF/checking.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    class Point {
        private int x;
        private int y;
        Point(int x,int y){
            this.x=x;
            this.y=y;
        }
    }
    public static boolean checkArea(int x,int y,int R){
        if(x<=0&&y<=0&&x*x+y*y<=R*R){
            return true;
        }
        if(x<=0&&y>=0&&y<=x+1.0*R/2){
            return true;
        }
        if(x>=0&&y<=0&&x<=R&&y>=-R){
            return true;
        }
        return false;
    }
}
