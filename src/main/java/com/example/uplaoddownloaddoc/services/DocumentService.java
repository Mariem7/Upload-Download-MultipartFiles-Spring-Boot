package com.example.uplaoddownloaddoc.services;

import com.example.uplaoddownloaddoc.entities.Document;
import com.example.uplaoddownloaddoc.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DocumentService {

    private DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document saveFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try{
            Document document = new Document(fileName, file.getContentType(),file.getBytes());
            return documentRepository.save(document);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Optional<Document> getFile(Integer fileId){
        return documentRepository.findById(fileId);
    }

    public List<Document> getFiles(){
        return documentRepository.findAll();
    }




}
