/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binhtt.controllers;

import binhtt.constants.Pages;
import binhtt.dtos.QuestionDTO;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author binht
 */
@WebServlet(name = "LoadAnswerPageController", urlPatterns = {"/LoadAnswerPageController"})
public class LoadAnswerPageController extends HttpServlet {
    static final Logger LOGGER = Logger.getLogger(LoadAnswerPageController.class);
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
            List<QuestionDTO> questionDTOS = (List<QuestionDTO>) session.getAttribute("LIST_QUESTION");
            String quizId = (String) session.getAttribute("QUIZ_ID");
            if(quizId == null){
                request.setAttribute("ERROR", "Take quiz before select question!");
            } else {
                String questionIndex = request.getParameter("question");
                String answerId = request.getParameter("answerId");
                if(questionIndex == null){
                    questionIndex = "1";
                }
                if(answerId != null && !"".equals(answerId.trim())){
                    questionDTOS.get(Integer.parseInt(questionIndex) - 1).setSelectedAnswer(answerId);
                    session.setAttribute("LIST_QUESTION", questionDTOS);
                }
                request.setAttribute("CURRENT_QUESTION", questionDTOS.get(Integer.parseInt(questionIndex) - 1));
                request.setAttribute("INDEX", questionIndex);
                url = Pages.QUIZ_PAGE;
            }
        } catch (Exception e){
            e.printStackTrace();
            LOGGER.info("Exception at LoadAnswerPageController: " + e.getMessage());
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
