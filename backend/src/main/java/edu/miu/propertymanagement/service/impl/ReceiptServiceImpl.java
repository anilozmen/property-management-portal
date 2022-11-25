package edu.miu.propertymanagement.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.jdi.request.InvalidRequestStateException;

import org.springframework.stereotype.Service;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import edu.miu.propertymanagement.entity.Offer;
import edu.miu.propertymanagement.service.OfferService;
import edu.miu.propertymanagement.service.PropertyService;
import edu.miu.propertymanagement.service.ReceiptService;
import edu.miu.propertymanagement.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final PropertyService propertyService;
    private final OfferService offerService;
    private final UserService userService;

    @Override
    public void getReceipt(long propertyId, HttpServletResponse response) {

        ApplicationUserDetail userDetail = userService.getLoggedInUser();
        Offer offer = offerService.getCompletedOfferIfExists(propertyId);
        boolean isComplete = propertyService.isPropertyStatusComplete(propertyId);
        
        if (offer == null || !isComplete) {
            throw new InvalidRequestStateException();
        }
        
        if(!userDetail.isAdmin() && userDetail.getId() != offer.getCustomer().getId()) {
            throw new InvalidRequestStateException();
        }

        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, response.getOutputStream());

            DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
            String currentDateTime = dateFormat.format(new Date());
            String headerkey = "Content-Disposition";
            String headervalue = "attachment; filename=student" + currentDateTime + ".pdf";
            response.setHeader(headerkey, headervalue);

            document.open();
            Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);

            fontTiltle.setSize(20);

            // Creating paragraph
            Paragraph paragraph1 = new Paragraph("Receipt", fontTiltle);

            paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph1);

            PdfPTable table = new PdfPTable(2);
            document.add(table);
            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
