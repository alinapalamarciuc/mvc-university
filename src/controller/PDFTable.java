package controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DisciplineAverage;
import model.Phone;
import model.Student;

public class PDFTable {

	public void createTable(List<Student> students, HttpServletResponse response) {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=studentList.pdf");
		try {
			Document document = new Document(PageSize.LETTER);
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			PdfPTable table = new PdfPTable(8);
			Font fontTitle = new Font(FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLUE);
			Font font = new Font(FontFamily.TIMES_ROMAN, 9, Font.BOLD, BaseColor.BLACK);
			Font fontCell = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
			Paragraph title = new Paragraph("List students" + "\n", fontTitle);
			document.add(createTitle(title));
			PdfPCell cell1 = new PdfPCell(new Paragraph("Picture", font));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Name", font));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Birth Day", font));
			PdfPCell cell4 = new PdfPCell(new Paragraph("Gender", font));
			PdfPCell cell5 = new PdfPCell(new Paragraph("Address", font));
			PdfPCell cell6 = new PdfPCell(new Paragraph("Phone", font));
			PdfPCell cell7 = new PdfPCell(new Paragraph("Library Abonament", font));
			PdfPCell cell8 = new PdfPCell(new Paragraph("Marks", font));
			table.addCell(headerCellStyle(cell1));
			table.addCell(headerCellStyle(cell2));
			table.addCell(headerCellStyle(cell3));
			table.addCell(headerCellStyle(cell4));
			table.addCell(headerCellStyle(cell5));
			table.addCell(headerCellStyle(cell6));
			table.addCell(headerCellStyle(cell7));
			table.addCell(headerCellStyle(cell8));
			for (Student s : students) {
				Image img;
				if (s.getPicture() != null) {
					img = Image.getInstance(s.getPicture());
				} else {
					img = Image.getInstance("image/f.png");
				}
				PdfPCell picture = new PdfPCell(img, true);
				PdfPCell name = new PdfPCell(new Paragraph(s.getFirstName() + " " + s.getLastName(), fontCell));
				PdfPCell dob = new PdfPCell(new Paragraph(s.getDob().toString(), fontCell));
				PdfPCell gender = new PdfPCell(new Paragraph(s.getGender() + "", fontCell));
				PdfPCell address = new PdfPCell(new Paragraph(
						s.getAddres().getCountry() + " " + s.getAddres().getCity() + " " + s.getAddres().getAddress(),
						fontCell));
				String phones = null;
				for (Phone p : s.getPhone()) {
					phones = p.getType().getPhoneNameType() + " " + p.getNumber() + "\n";
				}
				PdfPCell phone = new PdfPCell(new Paragraph(phones, fontCell));
				PdfPCell abonament = new PdfPCell(
						new Paragraph(s.getAbonament().getAbonamentStatus().toString(), fontCell));
				String marks = null;
				for (DisciplineAverage discAvg : s.getAverage()) {
					marks = discAvg.getDiscipline().getTitle() + ": " + discAvg.getAverageMark() + "\n";
				}
				PdfPCell mark = new PdfPCell(new Paragraph(marks, fontCell));
				table.addCell(cellStyle(picture));
				table.addCell(cellStyle(name));
				table.addCell(cellStyle(dob));
				table.addCell(cellStyle(gender));
				table.addCell(cellStyle(address));
				table.addCell(cellStyle(phone));
				table.addCell(cellStyle(abonament));
				table.addCell(cellStyle(mark));
			}
			document.add(table);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PdfPCell headerCellStyle(PdfPCell cell) {

		// alignment
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		// padding
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		return cell;
	}

	private static Paragraph createTitle(Paragraph paragraph) {

		paragraph.setSpacingAfter(50);
		paragraph.setSpacingBefore(30);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		return paragraph;

	}

	public static PdfPCell cellStyle(PdfPCell cell) {
		// alignment
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		// padding
		cell.setPaddingLeft(3f);
		cell.setPaddingTop(0f);
		return cell;
	}
}
