/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binhtt.controllers;

import binhtt.constants.Controllers;
import binhtt.constants.Pages;
import binhtt.constants.Roles;
import binhtt.daos.QuestionDAO;
import binhtt.dtos.QuestionDTO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import binhtt.dtos.UserDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author binht
 */
@WebServlet(name = "CreateQuestionController", urlPatterns = {"/CreateQuestionController"})
public class CreateQuestionController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CreateQuestionController.class);
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
        String url = Pages.ERROR_PAGE;
        try {
            HttpSession session = request.getSession();
            UserDTO userDTO = (UserDTO) session.getAttribute("USER");
            if(userDTO.getRole() == Roles.USER){
                request.setAttribute("ERROR", "Cannot insert!");
            } else {
                String question = request.getParameter("question");
                String answerA = request.getParameter("answerA");
                String answerB = request.getParameter("answerB");
                String answerC = request.getParameter("answerC");
                String answerD = request.getParameter("answerD");
                String correctAnswer = request.getParameter("correctAnswer");
                String subject = request.getParameter("subject");
                QuestionDAO questionDAO = new QuestionDAO();
                QuestionDTO questionDTO = new QuestionDTO(questionDAO.generateId(subject), question, answerA, answerB, answerC, answerD, Integer.parseInt(correctAnswer), true, subject);
                boolean check = questionDAO.create(questionDTO);
                if (!check) {
                    request.setAttribute("ERROR", "Error");
                } else {
                    url = Controllers.LOAD_QUESTION_CONTROLLER + "?page=1";
                }
            }
        } catch (Exception e) {
            request.setAttribute("ERROR", e.getMessage());
            LOGGER.info("Exception at CreateQuizController: " + e.getMessage());
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
