package com.example.margingmanagement.controller;

import com.example.margingmanagement.entity.FindRequest;
import com.example.margingmanagement.entity.MarginOrder;
import com.example.margingmanagement.services.MarginOrderService;
import com.example.margingmanagement.utility.IFileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/margins")
public class MarginOrderController {

    @Autowired
    MarginOrderService marginOrderService;

    @Autowired
    IFileHandler fileHandler;

    @GetMapping(produces = "application/json")
    public ResponseEntity<String> getAllMargins() {
        List<MarginOrder> marginOrders = marginOrderService.getAllMarginOrders();
        return ResponseEntity.ok(marginOrders.toString());
    }

    @PostMapping
    public ResponseEntity<String> addMarginOrder(@RequestBody MarginOrder marginOrder) {
        MarginOrder saved = marginOrderService.save(marginOrder);
        return ResponseEntity.ok(saved.toString());
    }

    @PostMapping("/find")
    public ResponseEntity<String> find(@RequestBody FindRequest findRequest) {
        List<MarginOrder> searchResults = marginOrderService.search(findRequest);
        return ResponseEntity.ok(searchResults.toString());
    }

    @PostMapping("/uploadfile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.getContentType() != null && !file.getContentType().equals(fileHandler.getContentType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "Please upload a " + fileHandler.getContentType() + " file");
        }
        //Validation
        try {
            List<MarginOrder> marginOrders = fileHandler.convertToMarginOrders(file);
            List<MarginOrder> saved = marginOrderService.clearAndSaveAll(marginOrders);
            return ResponseEntity.ok("Added " + saved.size() + " records");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping(value = "/download", produces = "text/csv")
    public ResponseEntity downloadAll() {
        List<MarginOrder> marginOrders = marginOrderService.getAllMarginOrders();
        ByteArrayInputStream inputStream = fileHandler.prepareCSVFile(marginOrders);
        InputStreamResource fileInputStream = new InputStreamResource(inputStream);
        String csvFileName = "results.csv";

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);

        return new ResponseEntity(
                fileInputStream,
                headers,
                HttpStatus.OK
        );
    }

}
