package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.DocumentDTO;
import com.tein.overcatchbackend.domain.model.Document;
import com.tein.overcatchbackend.mapper.DocumentMapper;
import com.tein.overcatchbackend.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
@Slf4j
public class DocumentResource {


    private final DocumentService documentService;
    private final DocumentMapper documentMapper;

    @RequestMapping(value = "/document", method = RequestMethod.POST)
    public DocumentDTO saveDocument(@RequestBody DocumentDTO documentDTO) {
        Document document = documentMapper.toEntity(documentDTO);
        return documentService.saveDocument(document);
    }

    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public List<Document> getDocuments(@RequestParam("clientId") String clientId) {
        return documentService.getDocuments(Long.parseLong(clientId));
    }
    @RequestMapping(value = "/hata", method = RequestMethod.GET)
    public ResponseEntity<String> getHata() throws Exception {
        if("".isEmpty())
        throw new Exception("Hata Mesajı");

        return ResponseEntity.ok(new String("ok"));

    }

    @RequestMapping(value = "/getdocumentbyid", method = RequestMethod.GET)
    public DocumentDTO getDocumentByID(@RequestParam("documentId") String documentId) {
        return documentService.getDocumentByID(Long.valueOf(documentId));
    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<?> deleteFileById(@RequestParam("documentId") String documentId) {
        try {
            documentService.deleteFileById(Long.valueOf(documentId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }
}
