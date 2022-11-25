package edu.miu.propertymanagement.service;

import javax.servlet.http.HttpServletResponse;

public interface ReceiptService {

    void getReceipt(long propertyId, HttpServletResponse response);
}
