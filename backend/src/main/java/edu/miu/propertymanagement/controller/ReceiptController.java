package edu.miu.propertymanagement.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import edu.miu.propertymanagement.service.ReceiptService;
import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/receipts")
@CrossOrigin(origins = "http://localhost:3000")
public class ReceiptController {

    private final ReceiptService receiptService;

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> produceReceipt(@RequestParam(value = "property_id") long propertyId, HttpServletResponse response) {
        response.setContentType("application/pdf");

        receiptService.getReceipt(propertyId, response);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(null);
    }

}
