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
import binhtt.dtos.AnswerOfQuestionDTO;
import binhtt.dtos.QuestionDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        String url = Pages.ERROR_PAGE;
        try {
            HttpSession session = request.getSession();
            UserDTO userDTO = (UserDTO) session.getAttribute("USER");
            if(userDTO.getRole() == Roles.USER){
                request.setAttribute("ERROR", "User cannot update");
            } else {
                String id = request.getParameter("id");
                String question = request.getParameter("updateQuestion");
                String correctAnswer = request.getParameter("updateCorrectAnswer");
                String subject = request.getParameter("updateSubject");
                int count = Integer.parseInt(request.getParameter("count"));
                List<AnswerOfQuestionDTO> answers = new ArrayList<>();
                answers.add(new AnswerOfQuestionDTO(id + "_" + 0, "", false, id));
                for(int i = 0; i < count; i++){
                    answers.add(new AnswerOfQuestionDTO(id + "_" + (i + 1), request.getParameter("updateAnswer" + (i + 1)), (i + 1) == Integer.parseInt(correctAnswer), id));
                }
                QuestionDTO questionDTO = new QuestionDTO(id, question, true, subject, answers);
                QuestionDAO questionDAO = new QuestionDAO();
                boolean check = questionDAO.update(questionDTO);
                if (!check) {
                    request.setAttribute("ERROR", "Error");
                } else {
                    url = Controllers.LOAD_QUESTION_CONTROLLER + "?page=1";
                }
            }
        } catch (Exception e) {
            request.setAttribute("ERROR", e.getMessage());
            LOGGER.info("Exception at UpdateQuestionController: " + e.getMessage());
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
