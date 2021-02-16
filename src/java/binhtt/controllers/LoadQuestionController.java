/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binhtt.controllers;

import binhtt.constants.Constants;
import binhtt.constants.Pages;
import binhtt.daos.QuestionDAO;
import binhtt.dtos.QuestionDTO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "LoadQuestionController", urlPatterns = {"/LoadQuestionController"})
public class LoadQuestionController extends HttpServlet {
        static final Logger LOGGER = Logger.getLogger(LoadQuestionController.class);

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
            String page = request.getParameter("page");
            String subjectId = request.getParameter("subjectId");
            String questionContent = request.getParameter("questionContent");
            String isDeleted = request.getParameter("isDeleted");
            if(page == null || page.trim().isEmpty()){
                page = "1";
            }
            if(subjectId == null || subjectId.trim().isEmpty()){
                subjectId = "all";
            }
            if(questionContent == null || questionContent.trim().isEmpty()){
                questionContent = "";
            }
            boolean status = false;
            if(isDeleted == null || isDeleted.trim().isEmpty()){
                status = true;
            }
            QuestionDAO questionDAO = new QuestionDAO();
            List<QuestionDTO> questionDTOS = questionDAO.getQuestions(Integer.parseInt(page), questionContent, status, subjectId);
            int total = questionDAO.getTotalOfQuestions(questionContent, status, subjectId);
            request.setAttribute("LIST_QUESTION", questionDTOS);
            int totalPage = total % Constants.PAGE_SIZE == 0 ? total / Constants.PAGE_SIZE : total / Constants.PAGE_SIZE + 1;
            request.setAttribute("TOTAL_PAGE", totalPage);
        } catch (Exception e){
            LOGGER.info("Exception at LoadQuestionController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(Pages.HOME_ADMIN).forward(request, response);
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
