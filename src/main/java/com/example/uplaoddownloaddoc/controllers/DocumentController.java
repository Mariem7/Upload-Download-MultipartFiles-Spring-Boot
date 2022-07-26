package com.example.uplaoddownloaddoc.controllers;

import com.example.uplaoddownloaddoc.entities.Document;
import com.example.uplaoddownloaddoc.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class DocumentController {

    private DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/")
    public String get(Model model){
        List<Document> documentList = documentService.getFiles();
        model.addAttribute("documentList",documentList);
        return "home";
    }

    @PostMapping("/uploadFiles")
    public String uploadMultipartFiles(@RequestParam("files")MultipartFile[] files){
        for (MultipartFile file: files){
            documentService.saveFile(file);
        }
        return "redirect:/";
    }

    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer id){
        Document document = documentService.getFile(id).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\""+document.getDocName()+"\"")
                .body(new ByteArrayResource(document.getFile()));

    }

}
