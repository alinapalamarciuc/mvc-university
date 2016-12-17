package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import controller.PDFTable;
import dao.AddressDao;
import dao.DisciplineAverageDao;
import dao.DisciplineDao;
import dao.GroupDao;
import dao.LibraryDao;
import dao.MarkDao;
import dao.PhoneDao;
import dao.ProfesorDao;
import dao.StudentDao;
import model.Address;
import model.Discipline;
import model.Group;
import model.LibraryAbonament;
import model.Mark;
import model.NewStudent;
import model.Phone;
import model.PhoneType;
import model.Profesor;
import model.Student;
import model.StudentSearchDTO;

public class StudentService {
	private Connection con;

	private StudentDao studentDao;
	private AddressDao addressDao;
	private PhoneDao phoneDao;
	private DisciplineDao disciplineDao;
	private GroupDao groupDao;
	private ProfesorDao profesorDao;
	private MarkDao markDao;
	private LibraryDao libraryDao;
	private DisciplineAverageDao disciplineAverageDao;
	private PDFTable pdfTable;

	public StudentService(Connection con) {
		this.con = con;
		studentDao = new StudentDao(con);
		phoneDao = new PhoneDao(con);
		addressDao = new AddressDao(con);
		disciplineDao = new DisciplineDao(con);
		groupDao = new GroupDao(con);
		profesorDao = new ProfesorDao(con);
		markDao = new MarkDao(con);
		libraryDao = new LibraryDao(con);
		disciplineAverageDao = new DisciplineAverageDao(con);
		pdfTable = new PDFTable();
	}

	public List<Student> loadAllStudents() {

		List<Student> students = null;

		try {
			con.setAutoCommit(false);
			students = studentDao.getAllStudents();
			for (Student s : students) {
				s.setPhone(phoneDao.getAllPhones(s.getId()));
				s.setAverage(disciplineAverageDao.getDisciplineAverage(s.getId()));
			}
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException ex) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return students;

	}

	public void saveStudent(NewStudent s) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date parsed = null;
		try {
			parsed = format.parse(s.getDob());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Student student = new Student(s.getId(), new Group(s.getGroup()), s.getFirstName(), s.getLastName(), parsed,
				s.getGender().charAt(0), s.getPicture());
		Address address = new Address(s.getCountry(), s.getCity(), s.getAddress());
		try {
			con.setAutoCommit(false);
			if (student.getId() != 0) {
				studentDao.updatePerson(student);
				studentDao.updateStudent(student);
				phoneDao.deleteAllPhonesStudentById(s.getId());
			} else {
				studentDao.insertPerson(student);
				int idStud = studentDao.selectLastIdStudent();
				studentDao.insertStudent(student, idStud);
				libraryDao.insertAbonamentStudent(idStud);
			}

			// save addres
			int getAddresId = addressDao.getAddress(address);
			if (getAddresId == 0) {
				addressDao.insertAddress(address);
				int addresId = addressDao.getLastIdAddres();
				if (s.getId() != 0) {
					addressDao.updateAddressToStudent(s.getId(), addresId);
				} else {
					addressDao.insertAddressToStudent(studentDao.selectLastIdStudent(), addresId);
				}
			} else {
				if (s.getId() != 0) {
					addressDao.updateAddressToStudent(s.getId(), getAddresId);
				} else {
					addressDao.insertAddressToStudent(studentDao.selectLastIdStudent(), getAddresId);
				}
			}

			// save phone
			for (Phone phone : s.getPhones()) {
				int getPhoneId = phoneDao.getPhone(phone);
				if (getPhoneId == 0) {
					phoneDao.insertPhone(phone);
					int phoneId = phoneDao.getLastIdPhone();
					if (s.getId() != 0) {
						phoneDao.insertPhoneToStudent(s.getId(), phoneId);
					} else {
						phoneDao.insertPhoneToStudent(studentDao.selectLastIdStudent(), phoneId);
					}
				} else {
					if (s.getId() != 0) {
						phoneDao.insertPhoneToStudent(s.getId(), getPhoneId);
					} else {
						phoneDao.insertPhoneToStudent(studentDao.selectLastIdStudent(), getPhoneId);
					}
				}
			}
			addressDao.rewiewAddress();
			phoneDao.rewiewPhones();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException ex) {
			if (con != null) {
				try {
					con.rollback();
					System.out.println("Transaction was unsuccessfully");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public NewStudent getStudentById(Integer id) {
		NewStudent st = new NewStudent();
		try {
			con.setAutoCommit(false);
			st = studentDao.getStudentById(id);
			st.setPhones(phoneDao.getAllPhones(st.getId()));
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException ex) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return st;
	}

	/**
	 * 
	 * @param sf
	 * @return
	 */
	public List<Student> searchStudents(StudentSearchDTO sf) {
		List<Student> students = null;
		try {
			con.setAutoCommit(false);
			students = studentDao.searchStudent(sf);
			for (Student s : students) {
				s.setPhone(phoneDao.getAllPhones(s.getId()));
				s.setAverage(disciplineAverageDao.getDisciplineAverage(s.getId()));
			}
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException ex) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return students;
	}

	public void deleteStudents(List<Integer> ids) {
		try {
			con.setAutoCommit(false);
			for (Integer i : ids) {
				if (studentDao.deleteStudent(i)) {
					addressDao.rewiewAddress();
					phoneDao.rewiewPhones();
				}
			}
			con.commit();
			con.setAutoCommit(true);
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException es) {
					es.printStackTrace();
				}
			}
			e.printStackTrace();
		}
	}

	public Map<String, List<Profesor>> getAllDisciplin(Integer id) {
		Set<Discipline> disciplines = new HashSet<Discipline>();
		Map<String, List<Profesor>> m = new HashMap<String, List<Profesor>>();
		try {
			con.setAutoCommit(false);
			disciplines = disciplineDao.getAllDisciplineOfStudent(id);
			for (Discipline d : disciplines) {
				List<Profesor> listProf = new ArrayList<Profesor>();
				for (Profesor p : profesorDao.getProfesorForDisciplines(d.getId()))
					listProf.add(p);
				m.put(d.getTitle(), listProf);
			}
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException ex) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return m;
	}

	public List<Discipline> loadAllDisciplines() {
		List<Discipline> disciplines = null;
		try {
			con.setAutoCommit(false);
			disciplines = disciplineDao.getDistinctDisciplines();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException ex) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return disciplines;
	}

	public List<Group> loadAllGroups() {
		List<Group> groups = null;
		try {
			con.setAutoCommit(false);
			groups = groupDao.getAllGroups();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException ex) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return groups;
	}

	public List<PhoneType> loadAllPhoneTypes() {
		List<PhoneType> types = null;
		try {
			con.setAutoCommit(false);
			types = phoneDao.getAllTypes();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException ex) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return types;
	}

	public void addMark(Mark mark, int idStud) {
		try {
			con.setAutoCommit(false);
			markDao.addMark(mark);
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException ex) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Student loadLibrary(Integer id) {
		Student studentLibrary = new Student();
		try {
			con.setAutoCommit(false);
			studentLibrary = libraryDao.getLibrary(id);
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException ex) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return studentLibrary;
	}

	public void editLibrary(LibraryAbonament abonament) {
		try {
			con.setAutoCommit(false);
			libraryDao.modifyLibrary(abonament);
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException ex) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}

	}

	public void DownloadPDF(List<Student> students, HttpServletResponse response) {
		pdfTable.createTable(students, response);

	}
}