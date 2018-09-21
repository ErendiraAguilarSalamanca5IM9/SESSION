package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ValidaSesionesServlet extends HttpServlet {
   
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      HttpSession sesion = request.getSession();
      String mensa = null;

      //Pedimos el atributo, y verificamos si existe
      String claveSesion = (String) sesion.getAttribute("claveSesion");


            Connection con=null; 
            Statement sta=null;
            ResultSet res = null;
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                con = DriverManager.getConnection("jdbc:mysql://localhost/Lab3","root","n0m3l0");
                sta= con.createStatement();
            }
            catch( SQLException error) {
                out.print(error.toString() );
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ValidaSesionesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
            String usuario=null, password=null;
            try{
                res = sta.executeQuery("select * from Usuario");
                if(res.next()){
                    usuario = res.getString("usuario");
                    password = res.getString("password");
                }
                else
                    out.println("<script>alert('No existe la persona.')</script>");        
            }
            catch( SQLException error) {
                out.print(error.toString() );
            }
      if(claveSesion.equals(usuario+password)){
       mensa = "llave correcta continua la sesion";
      }
      else
      {
       mensa = "llave incorrecta inicie nuevamente sesion";
      }   


      //Mostramos los  valores en el cliente
      out.println("¿Continua la Sesion y es valida?: " + mensa);
      out.println("<br>");
      out.println("ID de la sesi&oacute;n JSESSIONID: " + sesion.getId());
  
    }

}
