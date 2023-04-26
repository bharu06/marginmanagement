package com.example.margingmanagement.utility;

import com.example.margingmanagement.entity.MarginOrder;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public interface IFileHandler {

    List<MarginOrder> convertToMarginOrders(MultipartFile file);
    String getContentType();
    ByteArrayInputStream prepareCSVFile(List<MarginOrder> marginOrders);

}
