package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AbonamentStatus;
import model.Action;
import model.LibraryAbonament;
import model.Mark;
import model.NewStudent;
import model.Phone;
import model.Student;
import model.StudentSearchDTO;
import service.StudentService;
import utils.ConnectionManager;

public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ConnectionManager cm;
	private Connection con;
	private HttpSession session;
	private StudentService studentService;

	@Override
	public void init() throws ServletException {
		try {
			cm = ConnectionManager.getInstance();
			con = cm.getConnection();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		studentService = new StudentService(con);
		super.init();
	}

	public StudentController() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Action action;
		action = Action.LIST;
		session = request.getSession();
		if (request.getAttribute("action") != null) {
			action = Action.valueOf(request.getAttribute("action").toString());
		}
		NewStudent newSt = null;
		Integer id = null;
		request.setAttribute("LIST", Action.LIST.toString());
		request.setAttribute("SAVE", Action.SAVE.toString());
		request.setAttribute("SAVE_MARK", Action.SAVE_MARK.toString());
		request.setAttribute("SAVE_LIBRARY", Action.SAVE_LIBRARY.toString());
		request.setAttribute("DOWNLOAD", Action.DOWNLOAD.toString());
		switch (action) {
		case LIST: {
			studentService = new StudentService(con);
			List<Student> listst = studentService.loadAllStudents();
			request.setAttribute("groups", studentService.loadAllGroups());
			request.setAttribute("disciplines", studentService.loadAllDisciplines());
			request.setAttribute("students", studentService.loadAllStudents());
			session.setAttribute("students", studentService.loadAllStudents());
			request.getRequestDispatcher("list.jsp").forward(request, response);
			break;
		}
		case SAVE: {
			newSt = new NewStudent();
			newSt.setId(new Integer(request.getParameter("id")));
			if (newSt.getId() != 0) {
				newSt = studentService.getStudentById(newSt.getId());
				request.setAttribute("phoneTypes", studentService.loadAllPhoneTypes());
				request.setAttribute("newStudent", newSt);
				request.setAttribute("groups", studentService.loadAllGroups());
				request.getRequestDispatcher("saveStudent.jsp").forward(request, response);
			} else {
				newSt.setId(0);
				request.setAttribute("newStudent", newSt);
				request.setAttribute("phoneTypes", studentService.loadAllPhoneTypes());
				request.setAttribute("groups", studentService.loadAllGroups());
				request.getRequestDispatcher("saveStudent.jsp").forward(request, response);
			}
			break;
		}
		case SAVE_MARK: {
			id = new Integer(request.getParameter("id"));
			request.setAttribute("student", studentService.getStudentById(id));
			request.setAttribute("disciplines", studentService.getAllDisciplin(id));
			request.getRequestDispatcher("addMark.jsp").forward(request, response);
			break;
		}
		case SAVE_LIBRARY:
			id = Integer.parseInt(request.getParameter("id"));
			request.setAttribute("student", studentService.loadLibrary(id));
			request.setAttribute("enum", AbonamentStatus.values());
			request.getRequestDispatcher("addAbonament.jsp").forward(request, response);
			break;
		case DOWNLOAD:
			List<Student> students = studentService.loadAllStudents();
			studentService.DownloadPDF(students, response);
			break;

		default:
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		Action action;
		action = Action.LIST;
		if (request.getAttribute("action") != null) {
			action = Action.valueOf(request.getAttribute("action").toString());
		}
		Integer id;
		NewStudent newSt = null;
		List<Student> students = null;
		Map<String, String> errors = new HashMap<String, String>();
		request.setAttribute("LIST", Action.LIST.toString());
		request.setAttribute("SAVE", Action.SAVE.toString());
		request.setAttribute("SAVE_MARK", Action.SAVE_MARK.toString());
		request.setAttribute("SAVE_LIBRARY", Action.SAVE_LIBRARY.toString());
		request.setAttribute("DELETE", Action.DELETE.toString());
		switch (action) {
		case SAVE: {
			newSt = (NewStudent) request.getAttribute("AddStudent");
			errors = valitationSave(newSt);
			if (!errors.isEmpty()) {
				request.setAttribute("errors", errors);
				request.setAttribute("groups", studentService.loadAllGroups());
				request.setAttribute("phoneTypes", studentService.loadAllPhoneTypes());
				request.setAttribute("errorSt", newSt);
				request.getRequestDispatcher("errorSave.jsp").forward(request, response);
			} else {
				studentService.saveStudent(newSt);
				response.sendRedirect("student?action=LIST");
			}
			break;
		}
		case LIST:
			errors = valitationSearch((StudentSearchDTO) request.getAttribute("searchStudent"));
			if (!errors.isEmpty()) {
				request.setAttribute("errors", errors);
				request.setAttribute("groups", studentService.loadAllGroups());
				request.setAttribute("disciplines", studentService.loadAllDisciplines());
				request.setAttribute("search", request.getAttribute("searchStudent"));
				request.getRequestDispatcher("list.jsp").forward(request, response);
			} else {
				students = studentService.searchStudents((StudentSearchDTO) request.getAttribute("searchStudent"));
				session.setAttribute("students", studentService.loadAllStudents());
				request.setAttribute("groups", studentService.loadAllGroups());
				request.setAttribute("disciplines", studentService.loadAllDisciplines());
				request.setAttribute("students", students);
				request.setAttribute("search", request.getAttribute("searchStudent"));
				request.getRequestDispatcher("list.jsp").forward(request, response);
			}
			break;
		case SAVE_MARK: {
			id = Integer.parseInt(request.getParameter("id"));
			studentService.addMark((Mark) request.getAttribute("AddMark"), id);
			response.sendRedirect("student?action=LIST");
			break;
		}
		case SAVE_LIBRARY:
			studentService.editLibrary((LibraryAbonament) request.getAttribute("editLibrary"));
			response.sendRedirect("student?action=LIST");
			break;

		case DELETE:
			studentService.deleteStudents((List<Integer>) request.getAttribute("listId"));
			response.sendRedirect("student?action=LIST");

		}
	}

	private Map<String, String> valitationSave(NewStudent newSt) {
		Map<String, String> errors = new HashMap<String, String>();
		newSt.getFirstName().trim();
		newSt.getLastName().trim();
		newSt.getCountry().trim();
		newSt.getCity().trim();
		newSt.getAddress().trim();

		if (!newSt.getFirstName().matches("^[A-Za-z]{3,10}(\\s[A-Za-z]{3,9})?$")) {
			errors.put("firstName", "First name has been introduced incorrect");
		}
		if (!newSt.getLastName().matches("^[A-Za-z]{3,20}$")) {
			errors.put("lastName", "Last name has been introduced incorrect");
		}
		if (newSt.getGender() == null) {
			errors.put("gender", "Gender is not selected");
		}
		if (newSt.getGroup() == 0) {
			errors.put("group", "Group is not selected");
		}
		if (newSt.getDob() != null) {
			if (!newSt.getDob().matches("^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-(1([0-9]){3}|2([0-9]){3})$")) {
				errors.put("dob", "Date has been introduced incorrect. Date format dd-mm-yyyy");
			}
		}
		if (!newSt.getCountry().matches("^[A-Za-z]{3,20}$")) {
			errors.put("country", "Country has been introduced incorrect");
		}
		if (!newSt.getCity().matches("^[A-Za-z]{3,20}$")) {
			errors.put("city", "City has been introduced incorrect");
		}
		if ((newSt.getAddress().equals("")) || (newSt.getAddress().length() < 3)
				|| (newSt.getAddress().length() > 20)) {
			errors.put("address", "Address has been introduced incorrect");
		}
		List<Phone> phones = newSt.getPhones();
		int i = 1;
		for (Phone ph : phones) {
			if (ph.getType().getId() == 0) {
				errors.put("phone[" + i + "].phoneType", "Phone type " + i + " is not selected");
			}
			if (!ph.getNumber().matches("^[0-9]{3,15}$")) {
				errors.put("phone[" + i + "].number" + i, "Phone " + i + " has been introduced incorrect");
			}
			i++;
		}
		return errors;
	}

	private Map<String, String> valitationSearch(StudentSearchDTO stSearch) {
		Map<String, String> errors = new HashMap<String, String>();
		double markAverage = 0;
		if (!stSearch.getDataOfBirthStart().equals("")) {
			if (!stSearch.getDataOfBirthStart()
					.matches("^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-(1([0-9]){3}|2([0-9]){3})$")) {
				errors.put("dobStart", "Start date has been introduced incorrect. Date format dd-mm-yyyy");
			}
		}
		if (!stSearch.getDataOfBirthEnd().equals("")) {
			if (!stSearch.getDataOfBirthEnd()
					.matches("^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-(1([0-9]){3}|2([0-9]){3})$")) {
				errors.put("dobEnd", "End date has been introduced incorrect. Date format dd-mm-yyyy");
			}
		}
		if (!stSearch.getTotalAverage().isEmpty()) {
			if ((stSearch.getTotalAverage().matches("^[0-9]{1,2}(.[0-9]{1,2})?$"))) {
				markAverage = new Double(stSearch.getTotalAverage());
			}
			if ((markAverage < 1) || markAverage > 10) {
				errors.put("totalAverage", "Total average has been introduced incorrect");
			}
		}

		if (!stSearch.getAverageDiscipline().isEmpty()) {
			if ((stSearch.getAverageDiscipline().matches("^[0-9]{1,2}(.[0-9]{1,2})?$"))) {
				markAverage = new Double(stSearch.getAverageDiscipline());
			}
			if ((markAverage < 1) || markAverage > 10) {
				errors.put("disciplineAverage", "Discipline average has been introduced incorrect");
			}
		}
		return errors;
	}

}