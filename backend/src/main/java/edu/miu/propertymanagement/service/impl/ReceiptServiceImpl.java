package edu.miu.propertymanagement.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.jdi.request.InvalidRequestStateException;

import org.springframework.stereotype.Service;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import edu.miu.propertymanagement.entity.Offer;
import edu.miu.propertymanagement.entity.Property;
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

        Property property = propertyService.findById(propertyId);

        if (offer == null || !isComplete) {
            throw new InvalidRequestStateException();
        }

        if (!userDetail.isAdmin() && userDetail.getId() != offer.getCustomer().getId()) {
            throw new InvalidRequestStateException();
        }

        Document document = new Document(PageSize.A4);

        BigDecimal actualPrice = property.getPrice();
        BigDecimal boughtPrice = offer.getAmount();

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

            table.setWidthPercentage(100f);

            table.setWidths(new int[]{3, 3});

            table.setSpacingBefore(5);

            // Create Table Cells for the table header

            PdfPCell cell = new PdfPCell();

            // Setting the background color and padding of the table cell

            cell.setBackgroundColor(CMYKColor.BLUE);

            cell.setPadding(5);

            // Creating font

            // Setting font style and size

            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);

            font.setColor(CMYKColor.WHITE);

            // Adding headings in the created table cell or  header

            // Adding Cell to table

            cell.setPhrase(new Phrase("Actual Price", font));
            table.addCell(cell);

            cell.setPhrase(new Phrase("Bought Price", font));
            table.addCell(cell);

            table.addCell(actualPrice.toString());
            table.addCell(boughtPrice.toString());

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
