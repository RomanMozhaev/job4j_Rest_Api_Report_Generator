package ru.job4j.report.controller;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.job4j.person.domain.Person;
import ru.job4j.report.service.ReportGeneratorService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Value("${person.api}")
    private String api;
    @Value("${person.apiId}")
    private String apiId;

    private final ReportGeneratorService service;

    @Autowired
    public ReportController(ReportGeneratorService service) {
        this.service = service;
    }

    /**
     * generates report in PDF with the table of all persons data.
     *
     * @return - report.pdf
     * @throws IOException
     * @throws DocumentException
     */
    @GetMapping(path = "/generate")
    public ResponseEntity<Resource> createReport() throws IOException, DocumentException {
        RestTemplate rest = new RestTemplate();
        List<Person> persons;
        persons = rest.exchange(
                this.api,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
                }
        ).getBody();
        File report = this.service.generateReport(persons);
        Path path = Paths.get(report.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return ResponseEntity.ok()
                .contentLength(report.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
