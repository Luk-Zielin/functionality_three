package com.example.functionality_three.services;

import com.example.functionality_three.entities.FileMetadata;
import com.example.functionality_three.repositories.FoldersJpaRepository;
import com.example.functionality_three.repositories.MetadataJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class ReportService implements IReportService{
    private final MetadataJpaRepository metadataRepository;
    private final FoldersJpaRepository foldersRepository;
    @Autowired
    public ReportService(MetadataJpaRepository metadataJpaRepository, FoldersJpaRepository foldersJpaRepository){
        metadataRepository = metadataJpaRepository;
        foldersRepository = foldersJpaRepository;
    }

    @Override
    public ResponseEntity<Map<String, String>> createReport(String reportType) {
        HashMap<String,String > report = new HashMap<>();
        List<FileMetadata> files = metadataRepository.findAll();
        switch(reportType){
            case "files","Files":
                long sum = files.stream().mapToLong(FileMetadata::getSize).sum();
                report.put("Sum of all files: ",String.valueOf(sum));
                OptionalDouble average = files.stream().mapToLong(FileMetadata::getSize).average();

                Optional<FileMetadata> file = files.stream().max(Comparator.comparingLong(FileMetadata::getSize));
                file.ifPresent(fileMetadata -> report.put("Biggest file: ", fileMetadata.getFilename()));

                break;
            case "folders","Folders":

                break;
            case "length","Length":

                break;
        }
        return ResponseEntity.ok();
        return ResponseEntity.badRequest().build();
    }
}
