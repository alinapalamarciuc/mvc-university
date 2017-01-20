package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import model.AbonamentStatus;
import model.Action;
import model.Discipline;
import model.LibraryAbonament;
import model.Mark;
import model.NewStudent;
import model.Phone;
import model.PhoneType;
import model.Profesor;
import model.Student;
import model.StudentSearchDTO;
import utils.StudentRepository;
/**
 * Servlet Filter implementation class StudentFilter
 */
public class StudentFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public StudentFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		Action action;
		action = Action.LIST;
		if (request.getAttribute("action") != null) {
			action = Action.valueOf(request.getAttribute("action").toString());
		}

		NewStudent newStudent = new NewStudent();
		HttpServletRequest req = (HttpServletRequest) request;
		if (!ServletFileUpload.isMultipartContent((HttpServletRequest) request)) {
			action = Action.valueOf((String) request.getParameter("action"));
			if (action.equals(equals(Action.SAVE))) {
				newStudent = AddNewStudent(request);
			}
		} else {
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> formItems = upload.parseRequest((HttpServletRequest) request);
				Map<String, String> phoneList = new HashMap<String, String>();
				Integer phoneCount = formItems.size();
				phoneCount = (phoneCount - 11) / 2; // 9 cimpuri + id + action
				for (FileItem item : formItems) {
					if (item.isFormField()) {
						String fieldName = item.getFieldName();
						switch (fieldName) {
						case "action":
							action = Action.valueOf((String) item.getString());
							break;
						case "id":
							newStudent.setId(Integer.parseInt(item.getString()));
							break;
						case "firstName":
							newStudent.setFirstName(item.getString());
							break;
						case "lastName":
							newStudent.setLastName(item.getString());
							break;
						case "group":
							newStudent.setGroup(Integer.parseInt(item.getString()));
							break;
						case "dob":
							newStudent.setDob(item.getString());
							break;
						case "gender":
							newStudent.setGender(item.getString());
							break;
						case "country":
							newStudent.setCountry(item.getString());
							break;
						case "city":
							newStudent.setCity(item.getString());
							break;
						case "address":
							newStudent.setAddress(item.getString());
							break;
						default:
							phoneList.put(fieldName, item.getString());
							break;
						}
					} else {
						byte[] data = new byte[1024];
						if (item.get().length == 0) {

							@SuppressWarnings("unchecked")
							List<Student> listSt = (List<Student>) session.getAttribute("students");

							for (Student student : listSt) {
								if (student.getId() == newStudent.getId()) {
									data = student.getPicture();

								}
							}

						} else {
							data = item.get();
						}
						newStudent.setPicture(data);
					}
				}

				List<Phone> phones = new ArrayList<>();

				for (int i = 1; i <= phoneCount; i++) {
					Phone phone = new Phone();
					PhoneType type = new PhoneType();
					for (Map.Entry<String, String> entry : phoneList.entrySet()) {
						if (entry.getKey().endsWith(i + "].phoneType") & (!entry.getValue().equals("0"))) {
							type.setId(new Integer(entry.getValue()));
							phone.setType(type);
						} else if (entry.getKey().endsWith(i + "].phoneType") & (entry.getValue().equals("0"))) {
							type.setId(0);
							phone.setType(type);
						}
						if (entry.getKey().endsWith(i + "].number")) {
							phone.setNumber(entry.getValue());
						}
					}
					phones.add(phone);
				}
				newStudent.setPhones(phones);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		}

		switch (action)

		{

		case SAVE: {
			if (req.getMethod().equals("POST")) {
				request.setAttribute("AddStudent", newStudent);
			}
			break;
		}
		case SAVE_MARK:
			if (req.getMethod().equals("POST")) {
				request.setAttribute("AddMark", AddMark(request, response));
			}
			break;
		case SAVE_LIBRARY:
			if (req.getMethod().equals("POST")) {
				request.setAttribute("editLibrary", editAbonament(request));
			}
			break;
		case LIST:
			if (req.getMethod().equals("POST")) {
				request.setAttribute("searchStudent", searchStudent(request));
			}
			break;

		case DELETE: {
			List<Integer> listId = new ArrayList<Integer>();

			StudentRepository studentRepository = new StudentRepository(request.getParameterValues("idStudent"));
			for(Iterator iter = studentRepository.getIterator(); iter.hasNext();){
				listId.add(new Integer(iter));
			}
			request.setAttribute("listId", listId);
		}
		default:
			break;

		}
		request.setAttribute("action", action);
		// pass the request along the filter chain
		chain.doFilter(request, response);

	}

	private StudentSearchDTO searchStudent(ServletRequest request) {
		StudentSearchDTO st = new StudentSearchDTO();
		st.setDataOfBirthStart(request.getParameter("dobStart"));
		st.setDataOfBirthEnd(request.getParameter("dobEnd"));
		st.setName(request.getParameter("partialName").replaceAll("\\s+", "%"));
		st.setAddress(request.getParameter("partialAdres").replaceAll("\\s+", "%"));
		st.setGroup(new Integer(request.getParameter("group")));
		st.setGender(request.getParameter("gender"));
		st.setDiscipline(new Integer(request.getParameter("discipline")));
		st.setAverageDiscipline(request.getParameter("disciplineAverage").replaceAll("\\s+", "").replace(",", "."));
		st.setTotalAverage(request.getParameter("totalAverage").replaceAll("\\s+", "").replace(",", "."));
		return st;
	}

	private LibraryAbonament editAbonament(ServletRequest request) {
		LibraryAbonament abonament = new LibraryAbonament();
		abonament.setId(new Integer(request.getParameter("id")));
		abonament.setAbonamentStatus(AbonamentStatus.valueOf(request.getParameter("status")));
		String startdate = request.getParameter("start_date");
		String enddate = request.getParameter("end_date");
		if (abonament.getAbonamentStatus() == (AbonamentStatus.ACTIVE)) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date date;
			try {
				date = formatter.parse(startdate);
				abonament.setStartDate(date);
				date = formatter.parse(enddate);
				abonament.setEndDate(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			abonament.setStartDate(null);
			abonament.setEndDate(null);
		}
		return abonament;
	}

	private Mark AddMark(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		Mark mark = new Mark();
		String markValid = request.getParameter("mark").trim();
		Profesor professor = new Profesor();
		professor.setId(new Integer(request.getParameter("profesorId")));
		mark.setProfesor(professor);

		Discipline disciplina = new Discipline();
		disciplina.setTitle(request.getParameter("title"));
		mark.setDiscipline(disciplina);

		Student student = new Student();
		student.setId(new Integer(request.getParameter("id")));
		mark.setStudent(student);

		try {
			mark.setMark(new Double(markValid));
		} catch (Exception e) {
			request.getRequestDispatcher("mark.jsp").forward(request, response);
		}
		request.setAttribute("mark", mark);
		return mark;
	}

	private NewStudent AddNewStudent(ServletRequest request) {
		NewStudent newSt = new NewStudent();
		newSt.setId(new Integer(request.getParameter("id"))); // din URL
		newSt.setFirstName(request.getParameter("firstName"));
		newSt.setLastName(request.getParameter("lastName"));
		newSt.setGroup(new Integer(request.getParameter("group")));
		newSt.setDob(request.getParameter("dob"));
		newSt.setGender(request.getParameter("gender"));
		newSt.setCity(request.getParameter("city"));
		newSt.setCountry(request.getParameter("country"));
		newSt.setAddress(request.getParameter("address"));
		List<Phone> phones = new ArrayList<Phone>();
		String phoneType[] = request.getParameterValues("phoneType");
		String phoneNumber[] = request.getParameterValues("phoneNumber");
		for (int i = 0; i < phoneNumber.length; i++) {
			phones.add(new Phone(new PhoneType(new Integer(phoneType[i])), phoneNumber[i]));
		}
		newSt.setPhones(phones);
		return newSt;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
