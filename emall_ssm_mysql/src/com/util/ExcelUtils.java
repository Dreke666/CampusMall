package com.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;

/**
 * ����POI��javaee����Excel������
 *https://blog.csdn.net/u010003835/article/details/50894549?locationNum=7&fps=1
 * @author 
 */
public class ExcelUtils {
    /**
     *
     * @param response
     *   ����
     * @param fileName
     *   �ļ��� �磺"ѧ����"
     * @param excelHeader
     *   excel��ͷ���飬���"����#name"��ʽ�ַ�����"����"Ϊexcel�����У� "name"Ϊ�����ֶ���
     * @param dataList
     *   ���ݼ��ϣ������ͷ�����е��ֶ���һ�£����ҷ���javabean�淶
     * @return ����һ��HSSFWorkbook
     * @throws Exception
     */
    public static <T> HSSFWorkbook export(HttpServletResponse response, String fileName, String[] excelHeader,
                                          Collection<T> dataList) throws Exception {
        // ��������
        response.setContentType("application/application/vnd.ms-excel");
        response.setHeader("Content-disposition",
                "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "UTF-8"));
        // ����һ��Workbook����Ӧһ��Excel�ļ�
        HSSFWorkbook wb = new HSSFWorkbook();
        // ���ñ�����ʽ
        HSSFCellStyle titleStyle = wb.createCellStyle();
        // ���õ�Ԫ��߿���ʽ
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿� ϸ����
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// �±߿� ϸ����
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿� ϸ����
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿� ϸ����
        // ���õ�Ԫ����뷽ʽ
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ˮƽ����
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // ��ֱ����
        // ����������ʽ
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 15); // ����߶�
        titleFont.setFontName("����"); // ������ʽ
        titleStyle.setFont(titleFont);
        // ��Workbook�����һ��sheet,��ӦExcel�ļ��е�sheet
        HSSFSheet sheet = wb.createSheet(fileName);
        // ��������
        String[] titleArray = new String[excelHeader.length];
        // �ֶ�������
        String[] fieldArray = new String[excelHeader.length];
        for (int i = 0; i < excelHeader.length; i++) {
            String[] tempArray = excelHeader[i].split("#");// ��ʱ���� �ָ�#
            titleArray[i] = tempArray[0];
            fieldArray[i] = tempArray[1];
        }
        // ��sheet����ӱ�����
        HSSFRow row = sheet.createRow((int) 0);// ������0��ʼ
        HSSFCell sequenceCell = row.createCell(0);// cell�� ��0��ʼ ��һ��������
        sequenceCell.setCellValue("���");
        sequenceCell.setCellStyle(titleStyle);
        sheet.autoSizeColumn(0);// �Զ����ÿ��
        // Ϊ�����и�ֵ
        for (int i = 0; i < titleArray.length; i++) {
            HSSFCell titleCell = row.createCell(i + 1);// 0��λ�����ռ�ã�������+1
            titleCell.setCellValue(titleArray[i]);
            titleCell.setCellStyle(titleStyle);
            sheet.autoSizeColumn(i + 1);// 0��λ�����ռ�ã�������+1
        }
        // ������ʽ ��Ϊ�����������ʽ��ͬ ��Ҫ�ֿ����� ��Ȼ�Ḳ��
        HSSFCellStyle dataStyle = wb.createCellStyle();
        // �������ݱ߿�
        dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // ���þ�����ʽ
        dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ˮƽ����
        dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // ��ֱ����
        // ������������
        Font dataFont = wb.createFont();
        dataFont.setFontHeightInPoints((short) 12); // ����߶�
        dataFont.setFontName("����"); // ����
        dataStyle.setFont(dataFont);
        // �����������ݣ�����������
        Iterator<T> it = dataList.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;// 0��λ��ռ�� ����+1
            row = sheet.createRow(index);
            // Ϊ��Ÿ�ֵ
            HSSFCell sequenceCellValue = row.createCell(0);// ���ֵ��Զ�ǵ�0��
            sequenceCellValue.setCellValue(index);
            sequenceCellValue.setCellStyle(dataStyle);
            sheet.autoSizeColumn(0);
            T t = (T) it.next();
            // ���÷��䣬���ݴ��������ֶ������飬��̬���ö�Ӧ��getXxx()�����õ�����ֵ
            for (int i = 0; i < fieldArray.length; i++) {
                HSSFCell dataCell = row.createCell(i + 1);
                dataCell.setCellStyle(dataStyle);
                sheet.autoSizeColumn(i + 1);
                String fieldName = fieldArray[i];
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);// ȡ�ö�ӦgetXxx()����
                Class<? extends Object> tCls = t.getClass();// ����ΪObject�Լ�����Object������
                Method getMethod = tCls.getMethod(getMethodName, new Class[] {});// ͨ���������õ���Ӧ�ķ���
                Object value = getMethod.invoke(t, new Object[] {});// ��̬���÷�,�õ�����ֵ
                if (value != null) {
                    dataCell.setCellValue(value.toString());// Ϊ��ǰ�и�ֵ
                }
            }
        }

        OutputStream outputStream = response.getOutputStream();// ����
        wb.write(outputStream);// HSSFWorkbookд����
        wb.close();// HSSFWorkbook�ر�
        outputStream.flush();// ˢ����
        outputStream.close();// �ر���
        return wb;
    }
    // XSSFCellStyle.ALIGN_CENTER ���ж���
    // XSSFCellStyle.ALIGN_LEFT �����
    // XSSFCellStyle.ALIGN_RIGHT �Ҷ���
    // XSSFCellStyle.VERTICAL_TOP �϶���
    // XSSFCellStyle.VERTICAL_CENTER �ж���
    // XSSFCellStyle.VERTICAL_BOTTOM �¶���

    // CellStyle.BORDER_DOUBLE ˫����
    // CellStyle.BORDER_THIN ϸ����
    // CellStyle.BORDER_MEDIUM �еȱ���
    // CellStyle.BORDER_DASHED ���߱���
    // CellStyle.BORDER_HAIR СԲ�����߱���
    // CellStyle.BORDER_THICK �ֱ���

}