/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binhtt.controllers;

import binhtt.constants.Controllers;
import binhtt.constants.Pages;
import binhtt.constants.Roles;
import binhtt.daos.UserDAO;
import binhtt.dtos.UserDTO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author binht
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {
 static final Logger LOGGER = Logger.getLogger(LoginController.class);
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
        String url = Pages.LOGIN;
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            UserDAO userDAO = new UserDAO();
            UserDTO userDTO = userDAO.login(email, password);
            if(userDTO == null){
                request.setAttribute("ERROR", "Username or password is incorrect!");
            } else {
                if(!userDTO.isActive()){
                    request.setAttribute("ERROR", "This account was blocked!");
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", userDTO);
                    if(userDTO.getRole() == Roles.ADMIN){
                        url = Controllers.LOAD_QUESTION_CONTROLLER + "?page=1";
                    } else {
                        url = Controllers.LOAD_HISTORY_CONTROLLER;
                    }
                }
            }
        } catch (Exception e){
            request.setAttribute("ERROR", e.getMessage());
            LOGGER.info("Exception at LoginController: " +  e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
