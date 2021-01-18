package cl.cardif.PDFGenerator.app.archivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

public class ArchivoWord {
	public static void main(String[] args){
		final String ruta;
		ruta = "D:/Programacion/Almacenamiento archivo/PDFGenerator/doc_prueba.docx";
		String rutaSalida = "D:/Programacion/Almacenamiento archivo/PDFGenerator/doc_prueba_reemplazada.docx";
		String variable="";
		
		try {
		FileInputStream document = new FileInputStream(ruta);
		XWPFDocument docx = new XWPFDocument(document);
		List<XWPFParagraph> paragraphList = docx.getParagraphs();
		List<XWPFTable> table = docx.getTables();
		XWPFDocument out = new XWPFDocument();
		FileOutputStream output = new FileOutputStream(new File(rutaSalida));
		XWPFParagraph parrafo = out.createParagraph();
		XWPFRun run = parrafo.createRun();
		for(XWPFTable paragraph: table) {
			
			variable = paragraph.getText().replace("{TEXTO ; PolizaColectiva}", "01234");
			
			run.setText(variable);
		}
		docx.close();
		
		out.write(output);
		output.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}