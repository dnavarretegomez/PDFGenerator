package cl.cardif.PDFGenerator.app.archivo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class ExtraerFormato {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws URISyntaxException, IOException {
		final String ruta;
		ruta = "D:/Programacion/Almacenamiento archivo/PDFGenerator/doc_prueba.docx";
		String rutaSalida = "D:/Programacion/Almacenamiento archivo/PDFGenerator/doc_prueba_extraida.docx";

		try {

			FileInputStream document = new FileInputStream(ruta);
			// Path templatePath =
			// Paths.get(ExtraerFormato.class.getClassLoader().getResource(resourcePath).toURI());
			XWPFDocument doc = new XWPFDocument(document);
			doc = replaceTextFor(doc, "{TEXTO ; PolizaColectiva}", "0123456");
			saveWord(rutaSalida, doc);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static XWPFDocument replaceTextFor(XWPFDocument doc, String findText, String replaceText) {
		String value = "";

		// parrafos solos
		doc.getParagraphs().forEach(p -> {
			p.getRuns().forEach(run -> {
				String text = run.text();
				if (text.contains(findText)) {
					run.setText(text.replace(findText, replaceText), 0);
				}
			});
		});

		// Tablas
		for (int t = 0; t < doc.getTables().size(); t++) {
			XWPFTable tb = doc.getTables().get(t);
			for (int x = 0; x < tb.getRows().size(); x++) {
				XWPFTableRow tr = tb.getRow(x);
				for (int y = 0; y < tr.getTableCells().size(); y++) {
					XWPFTableCell cell = tr.getCell(y);
					value = cell.getText();
					while (value.contains(findText)) {
						if (value.contains(findText)) {
							cell.removeParagraph(0);
							//XWPFParagraph parrafo = cell.addParagraph();
							value = replaceText;
							cell.setText(value);	
						}
					}
				}
			}
		}

		return doc;
	}

	private static void saveWord(String filePath, XWPFDocument doc) throws FileNotFoundException, IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			doc.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
