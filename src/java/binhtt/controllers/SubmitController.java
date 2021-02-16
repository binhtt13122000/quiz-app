/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binhtt.controllers;

import binhtt.constants.Controllers;
import binhtt.constants.Pages;
import binhtt.daos.QuizDAO;
import binhtt.dtos.QuestionDTO;
import binhtt.dtos.SubjectDTO;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.apache.log4j.Logger;

/**
 *
 * @author binht
 */
@WebServlet(name = "SubmitController", urlPatterns = {"/SubmitController"})
public class SubmitController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(SubmitController.class);

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
            String quizId = (String) session.getAttribute("QUIZ_ID");
            SubjectDTO subjectDTO = (SubjectDTO) session.getAttribute("SELECTED_SUBJECT");
            List<QuestionDTO> questionDTOS = (List<QuestionDTO>) session.getAttribute("LIST_QUESTION");
            String answer = request.getParameter("answer");
            String[] answers = answer.split("");
            QuizDAO quizDAO = new QuizDAO();
            boolean check = quizDAO.submit(quizId, answers, questionDTOS);
            if (check) {
                session.removeAttribute("QUIZ_ID");
                session.removeAttribute("LIST_QUESTION");
                session.removeAttribute("SELECTED_SUBJECT");
                url = Controllers.LOAD_HISTORY_CONTROLLER + "?id=" + subjectDTO.getId();
            } else {
                request.setAttribute("ERROR", "failed!");
            }
        } catch (Exception e) {
            LOGGER.info("Exception at SubmitController: " + e.getMessage());
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
