/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.RegrasGraficos;
import model.Search;

/**
 *
 * @author arthur
 */
public class sparqlSearch extends HttpServlet {
    
    private static final String port="8080";

    public static String getport() {
        return port;
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet querySearch</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet querySearch at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        
        String datasetSearch = request.getParameter("param");
        String querySearch = "SELECT ?predicate ?object ?subject WHERE { ?predicate ?object ?subject }";
//        System.out.println(datasetSearch);

        Search se = new Search();
        ArrayList<ArrayList> consulta = se.search(request, datasetSearch, querySearch);
        
        
//        System.out.println(consulta.get(2).get(0));
//        System.out.println(consulta.get(2).get(1));
//        System.out.println(consulta.get(2).get(2));
        
        RegrasGraficos regra = new RegrasGraficos();
        String codigoDoGrafico = regra.getScriptGraph(consulta, datasetSearch);
        
        request.setAttribute("dados", codigoDoGrafico); //categorylist is an arraylist      contains object of class category  
        request.setAttribute("query", datasetSearch); //categorylist is an arraylist      contains object of class category  
        getServletConfig().getServletContext().getRequestDispatcher("/resultado.jsp").forward(request,response);
        
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
