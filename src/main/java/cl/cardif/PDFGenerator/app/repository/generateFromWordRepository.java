package cl.cardif.PDFGenerator.app.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Component;

import cl.cardif.schema.ebm.pdfgenerator.generatefromword.v1.*;

@Component
public class generateFromWordRepository {

	public GenerateFromWordPDFGeneratorRSPType executeService(GenerateFromWordPDFGeneratorREQType request) {
		GenerateFromWordPDFGeneratorRSPType response = new GenerateFromWordPDFGeneratorRSPType();
		GenerateFromWordPDFGeneratorRSPType.Body body = new GenerateFromWordPDFGeneratorRSPType.Body();
		GenerateResponseType generateResponse = new GenerateResponseType();
		
		final String ruta;
		ruta = "D:/Programacion/Almacenamiento archivo/PDFGenerator/doc_prueba.docx";
		String rutaSalida = "D:/Programacion/Almacenamiento archivo/PDFGenerator/doc_prueba_extraida.docx";
		try {
			FileInputStream document = new FileInputStream(ruta);
			XWPFDocument doc = new XWPFDocument(document);
			for (DataSetType d : request.getBody().getGenerateFromWordPDFGenerator().getDataSetList().getDataSet()) {
				for (TextParameterType texto : d.getTextParameterList().getTextParameter()) {
					doc = replaceTextFor(doc, "{TEXTO ; " + texto.getTextParameterKey() + "}",
							texto.getTextParameterValue());
				}
			}
			generateResponse.setDocumentIndex(1);
			generateResponse.setGeneratedPDFBase64("OK");
			saveWord(rutaSalida, doc);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setBody(body);
		return response;
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
