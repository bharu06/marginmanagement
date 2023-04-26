package com.example.margingmanagement.utility;

import com.example.margingmanagement.entity.MarginOrder;
import com.example.margingmanagement.entity.Operator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvFileHandler implements IFileHandler {

    public String FILE_TYPE = "text/csv";
    public static final String FIXED_HEADER = "Instruction,Base Ccy,Term Ccy,Trader Tier,From Amount,To Amount,Amt Ccy,Bid Operator,Bid Modifier,Ask Operator,Ask Modifier,Remarks";
    public static final String FIXED_HEADER_2 = "A,B,C";

    @Override
    public List<MarginOrder> convertToMarginOrders(MultipartFile file) {
        MarginOrderValidator marginOrderValidator = new MarginOrderValidator();
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            List<MarginOrder> marginOrders = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                MarginOrder marginOrder = new MarginOrder();
                //Instruction	Base Ccy	Term Ccy	Trader Tier	From Amount	To Amount	Amt Ccy	Bid Operator	Bid Modifier	Ask Operator	Ask Modifier	Remarks
                marginOrder.setInstruction(csvRecord.get("Instruction"));
                marginOrder.setRemarks(csvRecord.get("Remarks"));

                //CurrencyFields
                marginOrder.setBaseCCY(csvRecord.get("Base Ccy"));
                marginOrder.setTermCCY(csvRecord.get("Term Ccy"));
                marginOrder.setAmountCCY(csvRecord.get("Amt Ccy"));

                //Number fields
                marginOrder.setTraderTier(Integer.parseInt(csvRecord.get("Trader Tier")));
                marginOrder.setFromAmount(Integer.parseInt(csvRecord.get("From Amount")));
                marginOrder.setToAmount(Integer.parseInt(csvRecord.get("To Amount")));
                marginOrder.setBidModifier(Double.parseDouble(csvRecord.get("Bid Modifier")));
                marginOrder.setAskModifier(Double.parseDouble(csvRecord.get("Ask Modifier")));

                marginOrder.setBidOperator(Operator.fromString(csvRecord.get("Bid Operator")));
                marginOrder.setAskOperator(Operator.fromString(csvRecord.get("Ask Operator")));

                marginOrderValidator.validate(marginOrder);

                marginOrders.add(marginOrder);
            }

            return marginOrders;

        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public String getContentType() {
        return FILE_TYPE;
    }

    public ByteArrayInputStream prepareCSVFile(List<MarginOrder> marginOrders) {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // defining the CSV printer
            CSVPrinter csvPrinter = new CSVPrinter(
                    new PrintWriter(out),
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim()
            );
            csvPrinter.printRecord(FIXED_HEADER.split(","));
            for (MarginOrder marginOrder : marginOrders) {
                csvPrinter.printRecord(
                        marginOrder.getInstruction(),
                        marginOrder.getBaseCCY(),
                        marginOrder.getTermCCY(),
                        marginOrder.getTraderTier(),
                        marginOrder.getFromAmount(),
                        marginOrder.getToAmount(),
                        marginOrder.getAmountCCY(),
                        marginOrder.getBidOperator(),
                        marginOrder.getBidModifier(),
                        marginOrder.getAskOperator(),
                        marginOrder.getAskModifier(),
                        marginOrder.getRemarks());
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
