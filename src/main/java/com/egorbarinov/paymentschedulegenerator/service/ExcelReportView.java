package com.egorbarinov.paymentschedulegenerator.service;

import com.egorbarinov.paymentschedulegenerator.common.ScheduleGeneratorUtil;
import com.egorbarinov.paymentschedulegenerator.entity.Loan;
import com.egorbarinov.paymentschedulegenerator.entity.MonthlyLoanServicing;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ExcelReportView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) {

        response.setHeader("Content-Disposition", "attachment;filename=\"report.xls\"");
        Loan loan = (Loan) model.get("reportLoan");
        CellStyle style = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle dateCell = workbook.createCellStyle();
        dateCell.setDataFormat(createHelper.createDataFormat().getFormat("dd.MM.yyyy"));
        Sheet sheet = workbook.createSheet("Payment schedule");

        int width = (int) (14 * 1.14388) * 256; // 1757;
        sheet.autoSizeColumn(0);
        sheet.setColumnWidth(1, width);
        sheet.setColumnWidth(2, width);
        sheet.setColumnWidth(3, width);
        sheet.setColumnWidth(4, width);
        sheet.setColumnWidth(5, width);

        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        dateCell.setBorderTop(BorderStyle.THIN);
        dateCell.setBorderRight(BorderStyle.THIN);
        dateCell.setBorderBottom(BorderStyle.THIN);
        dateCell.setBorderLeft(BorderStyle.THIN);
// Определение цвета граничных значений стиля

        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        dateCell.setTopBorderColor(IndexedColors.BLACK.getIndex());
        dateCell.setRightBorderColor(IndexedColors.BLACK.getIndex());
        dateCell.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        dateCell.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        dateCell.setWrapText(true);
        dateCell.setAlignment(HorizontalAlignment.CENTER);
        dateCell.setVerticalAlignment(VerticalAlignment.CENTER);

        String header;
        Row row = sheet.createRow((short) 0);
        Cell cell = row.createCell(0);

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Calibri");
        font.setColor(IndexedColors.BLACK.getIndex());
//        font.setBold(true);//Make font bold
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        header = "Название кредита";
        cell.setCellValue(header);
        cell = row.createCell(3);
        cell.setCellValue("Ипотечный кредит");
        row = sheet.createRow((short) 1);
        cell = row.createCell(0);
        cell.setCellValue("Дата выдачи кредита");
        cell = row.createCell(3);
        cell.setCellValue(ScheduleGeneratorUtil.formatLocalDate(loan.getDateOfIssueOfLoan()));
        row = sheet.createRow((short) 2);
        cell = row.createCell(0);
        cell.setCellValue("Сумма кредита");
        cell = row.createCell(3);
        cell.setCellValue(ScheduleGeneratorUtil.formatBigDecimalToRuLocale(loan.getAmount()));

        row = sheet.createRow((short) 3);
        cell = row.createCell(0);
        cell.setCellValue("Валюта кредита");
        cell = row.createCell(3);
        cell.setCellValue("RUR");

        row = sheet.createRow((short) 4);
        cell = row.createCell(0);
        cell.setCellValue("Срок кредита");
        cell = row.createCell(3);
        cell.setCellValue(loan.getCreditPeriod() + " мес.");

        row = sheet.createRow((short) 5);
        cell = row.createCell(0);
        cell.setCellValue("Процентная ставка по кредиту");
        cell = row.createCell(3);
        cell.setCellValue(loan.getPercentRate() + " %");

// Определение стиля ячейки

        row = sheet.createRow((short) 7);
        row.setHeightInPoints(30.0f);
        cell = row.createCell(0);
        header = "№";
        cell.setCellValue(header);
        cell.setCellStyle(style);

        cell = row.createCell(1);
        header = "Дата платежа";
        cell.setCellValue(header);
        cell.setCellStyle(style);

        cell = row.createCell(2);
        header = "Сумма";
        cell.setCellValue(header);
        cell.setCellStyle(style);

        cell = row.createCell(3);
        header = "Погашение основного долга";
        cell.setCellValue(header); //Write header
        cell.setCellStyle(style);

        cell = row.createCell(4);
        header = "Выплата процентов";
        cell.setCellValue(header);
        cell.setCellStyle(style);

        cell = row.createCell(5);
        header = "Остаток";
        cell.setCellValue(header);
        cell.setCellStyle(style);

        int rowNum = 8;
        for(MonthlyLoanServicing payment : loan.getMonthlyServiceList()){
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue(payment.getCountOfPay());
            cell = row.createCell(1);
//            cell.setCellStyle(style);
//            cell.setCellValue(ScheduleGeneratorUtil.formatLocalDate(payment.getDateOfPayment())); //11/04/2021
            cell.setCellStyle(dateCell);
            cell.setCellValue(payment.getDateOfPayment());
            cell = row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue(ScheduleGeneratorUtil.formatBigDecimalToRuLocale(payment.getMonthlyPayment()));
            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue(ScheduleGeneratorUtil.formatBigDecimalToRuLocale(payment.getRepaymentOfPrincipalDebtPerMonth()));
            cell = row.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue(ScheduleGeneratorUtil.formatBigDecimalToRuLocale(payment.getPercentagesPerMonth()));
            cell = row.createCell(5);
            cell.setCellStyle(style);
            cell.setCellValue(ScheduleGeneratorUtil.formatBigDecimalToRuLocale(payment.getBalanceOfDebt()));
        }
    }
}
