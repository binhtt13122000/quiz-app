/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binhtt.controllers;

import binhtt.constants.Pages;
import binhtt.constants.Roles;
import binhtt.daos.QuizDAO;
import binhtt.dtos.QuizDTO;
import binhtt.dtos.SubjectDTO;
import binhtt.dtos.UserDTO;

import java.io.IOException;
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
@WebServlet(name = "LoadHistoryController", urlPatterns = {"/LoadHistoryController"})
public class LoadHistoryController extends HttpServlet {
        static final Logger LOGGER = Logger.getLogger(LoadHistoryController.class);

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
            if(userDTO.getRole() == Roles.ADMIN){
                request.setAttribute("ERROR", "Admin Cannot Load History");
            } else {
                String id = request.getParameter("id");
                List<SubjectDTO> subjectDTOS = (List<SubjectDTO>) getServletContext().getAttribute("SUBJECTS");
                SubjectDTO selectedSubject = null;
                if(id == null || id.trim().isEmpty()) {
                    selectedSubject = subjectDTOS.get(0);
                    request.setAttribute("SELECTED_SUBJECT", selectedSubject);
                    QuizDAO quizDAO = new QuizDAO();
                    List<QuizDTO> quizDTOList = quizDAO.getQuiz(selectedSubject.getId(), userDTO.getEmail());
                    request.setAttribute("HISTORY", quizDTOList);
                    url = Pages.HOME_USER;
                } else {
                    boolean existed = false;
                    for (SubjectDTO subjectDTO: subjectDTOS) {
                        if(subjectDTO.getId().equals(id)){
                            selectedSubject = subjectDTO;
                            request.setAttribute("SELECTED_SUBJECT", selectedSubject);
                            existed = true;
                            break;
                        }
                    }
                    if(!existed){
                        request.setAttribute("ERROR", "Id is not available!");
                    } else {
                        QuizDAO quizDAO = new QuizDAO();
                        List<QuizDTO> quizDTOList = quizDAO.getQuiz(selectedSubject.getId(), userDTO.getEmail());
                        request.setAttribute("HISTORY", quizDTOList);
                        url = Pages.HOME_USER;
                    }
                }
            }
        } catch (Exception e){
            request.setAttribute("ERROR", e.getMessage());
            LOGGER.info("Exception at LoadHistoryController: " + e.getMessage());
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
