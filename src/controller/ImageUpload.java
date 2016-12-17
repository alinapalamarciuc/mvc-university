package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Student;

/**
 * Servlet implementation class ImageUpload
 */
public class ImageUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {

	}

	public ImageUpload() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer studId = new Integer(request.getParameter("id"));
		@SuppressWarnings("unchecked")
		List<Student> listSt = (List<Student>) session.getAttribute("students");
		for (Student student : listSt) {
			if (student.getId() == studId) {
				byte[] buffer = student.getPicture();
				response.getOutputStream().write(buffer);
			}
		}
	}

}
