/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binhtt.controllers;

import binhtt.constants.Controllers;
import binhtt.daos.QuestionDAO;
import binhtt.dtos.QuestionDTO;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author binht
 */
@WebServlet(name = "UpdateQuestionController", urlPatterns = {"/UpdateQuestionController"})
public class UpdateQuestionController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(UpdateQuestionController.class);

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
        try {
            String id = request.getParameter("id");
            String question = request.getParameter("updateQuestion");
            String answerA = request.getParameter("updateAnswerA");
            String answerB = request.getParameter("updateAnswerB");
            String answerC = request.getParameter("updateAnswerC");
            String answerD = request.getParameter("updateAnswerD");
            String correctAnswer = request.getParameter("updateCorrectAnswer");
            String subject = request.getParameter("updateSubject");
            QuestionDTO questionDTO = new QuestionDTO(id, question, answerA, answerB, answerC, answerD, Integer.parseInt(correctAnswer), true, subject);
            QuestionDAO questionDAO = new QuestionDAO();
            boolean check = questionDAO.update(questionDTO);
            if (!check) {
                request.setAttribute("ERROR", "Error");
            }
        } catch (Exception e) {
            LOGGER.info("Exception at UpdateQuestionController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(Controllers.LOAD_QUESTION_CONTROLLER + "?page=1").forward(request, response);
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
