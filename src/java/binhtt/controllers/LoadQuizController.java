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
import binhtt.daos.QuizDAO;
import binhtt.dtos.QuestionDTO;
import binhtt.dtos.SubjectDTO;
import binhtt.dtos.UserDTO;

import java.io.IOException;
import java.util.Date;
import java.util.List;
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
@WebServlet(name = "LoadQuizController", urlPatterns = {"/LoadQuizController"})
public class LoadQuizController extends HttpServlet {
        static final Logger LOGGER = Logger.getLogger(LoadQuizController.class);
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
            if(userDTO == null){
                request.setAttribute("ERROR", "Login first!");
            } else {
                if(userDTO.getRole() == Roles.ADMIN){
                    request.setAttribute("ERROR", "Admin cannot take quiz");
                } else {
                    String id = request.getParameter("id");
                    String quesPerQuiz = request.getParameter("quesPerQuiz");
                    String time = request.getParameter("time");
                    QuestionDAO questionDAO = new QuestionDAO();
                    QuizDAO quizDAO = new QuizDAO();
                    List<QuestionDTO> questionDTOS = questionDAO.getQuestionsForTest(Integer.parseInt(quesPerQuiz), id);
                    String quizId = userDTO.getEmail() + "-" + id + "-" + new Date().getTime();
                    boolean check = quizDAO.createQuiz(quizId, userDTO.getEmail(), Integer.parseInt(time), id, questionDTOS);
                    if(check){
                        url = Controllers.LOAD_ANSWER_PAGE_CONTROLLER;
                        session.setAttribute("LIST_QUESTION", questionDTOS);
                        session.setAttribute("QUIZ_ID", quizId);
                        List<SubjectDTO> subjectDTOS = (List<SubjectDTO>) getServletContext().getAttribute("SUBJECTS");
                        for (SubjectDTO subjectDTO: subjectDTOS) {
                            if(subjectDTO.getId().equals(id)){
                                session.setAttribute("SELECTED_SUBJECT", subjectDTO);
                                break;
                            }
                        }
                    } else {
                        request.setAttribute("ERROR", "Take Quiz Failed");
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            request.setAttribute("ERROR", e.getMessage());
            LOGGER.info("Exception at LoadQuizController: " + e.getMessage());
        }  finally {
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
