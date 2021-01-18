package cl.cardif.PDFGenerator.app.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import cl.cardif.PDFGenerator.app.repository.generateFromWordRepository;
import cl.cardif.schema.ebm.pdfgenerator.generatefromword.v1.GenerateFromWordPDFGeneratorREQType;
import cl.cardif.schema.ebm.pdfgenerator.generatefromword.v1.GenerateFromWordPDFGeneratorRSPType;

public class generateFromWordEndpoint {
	
	
	private static final String NAMESPACE_URI = "http://cardif.cl/Schema/EBM/PDFGenerator/generateFromWord/v1.0";
	
	private generateFromWordRepository gfwRepository;

	@Autowired
	public generateFromWordEndpoint(generateFromWordRepository gfwRepository) {
		this.gfwRepository = gfwRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "generateFromWord_PDFGenerator_REQ")
	@ResponsePayload
	public GenerateFromWordPDFGeneratorRSPType generatePDFfromWord(@RequestBody GenerateFromWordPDFGeneratorREQType request) {
		return gfwRepository.executeService(request);
	}
}