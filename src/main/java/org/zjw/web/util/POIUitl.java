package org.zjw.web.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class POIUitl {
    /**
     * poi创建excel
     *
     * @param os        输出流
     * @param sheetName 工作薄名称
     * @param brow      页眉
     * @param titleRow  标题行
     * @param bodyRow   主体内容
     */
    public static void createExcel(OutputStream os, String sheetName, String brow, List<String> titleRow, List<List<String>> bodyRow) {
        createExcel(os, sheetName, brow, titleRow, null, bodyRow);
    }

    /**
     * poi创建excel
     *
     * @param os        输出流
     * @param sheetName 工作薄名称
     * @param titleRow  标题行
     * @param bodyRow   主体内容
     */
    public static void createExcel(OutputStream os, String sheetName, List<String> titleRow, List<List<String>> bodyRow) {
        createExcel(os, sheetName, null, titleRow, null, bodyRow);
    }

    /**
     * poi创建excel
     *
     * @param os          输出流
     * @param sheetName   工作薄名称
     * @param brow        页眉
     * @param titleRow    标题行
     * @param titleColumn 标题列
     * @param bodyRow     主体内容
     */
    public static void createExcel(OutputStream os, String sheetName, String brow, List<String> titleRow, List<String> titleColumn,
                                   List<List<String>> bodyRow) {
        try {
            //行下标
            int index = 0;
            Workbook wb = new XSSFWorkbook();//创建工作簿
            Sheet sheet = wb.createSheet(sheetName);//第一个sheet
            sheet.autoSizeColumn(1, true);
            //标题眉
            if (org.apache.commons.lang.StringUtils.isNotEmpty(brow)) {
                //对象的构造方法需要传入合并单元格的首行、最后一行、首列、最后一列。
                CellRangeAddress cra = new CellRangeAddress(index, index, index, titleRow.size());
                //在sheet里增加合并单元格
                sheet.addMergedRegion(cra);
                Row row = sheet.createRow(index);
                Cell cell = row.createCell(index);
                cell.setCellValue(brow);
                //单元格样式
                CellStyle cellStyle = wb.createCellStyle();

                cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
                Font font = wb.createFont();
                font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);
                index++;
            }
            Row rowFirst = sheet.createRow(index);//第一个sheet第一行为标题
            rowFirst.setHeight((short) 500);
            //写标题了
            for (int i = 0; i < titleRow.size(); i++) {
                sheet.setColumnWidth((short) i, (short) 4000);// 设置列宽
                //获取第一行的每一个单元格
                Cell cell = rowFirst.createCell(i);
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);// 设置背景色
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                Font font = wb.createFont();
                font.setColor(HSSFColor.WHITE.index);
                cellStyle.setFont(font);
                //往单元格里面写入值
                cell.setCellValue(titleRow.get(i));
                cell.setCellStyle(cellStyle);
            }
            index++;
            //列标题下标
            int columnIndex = 0;
            if (titleColumn != null) {
                columnIndex = 1;
            }
            //写内容了
            for (int i = 0; i < bodyRow.size(); i++) {
                //获取list里面存在是数据集对象
                List<String> rows = bodyRow.get(i);
                //创建数据行
                Row row = sheet.createRow(index + i);
                //设置对应单元格的值
                row.setHeight((short) 400);   // 设置每行的高度
                //如果有列标题,并且列标题的长度<内容的长度才set列标题
                if (titleColumn != null && i < titleColumn.size()) {
                    row.createCell(0).setCellValue(titleColumn.get(i));
                }
                for (int j = 0; j < rows.size(); j++) {
                    row.createCell(columnIndex + j).setCellValue(rows.get(j));
                }
            }

            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * poi创建excel
     *
     * @param os          输出流
     * @param sheetName   工作薄名称
     * @param brow        页眉
     * @param titleRow    标题行
     * @param titleColumn 标题列
     * @param bodyRow     主体内容
     */
    public static void createExcelTitle(OutputStream os, String sheetName, String brow, List<Map<String, Object>> titleRow, List<String> titleColumn,
                                        List<List<String>> bodyRow) {
        try {
            //行下标
            int index = 0;
            Workbook wb = new XSSFWorkbook();//创建工作簿
            Sheet sheet = wb.createSheet(sheetName);//第一个sheet
            sheet.autoSizeColumn(1, true);
            //标题眉
            if (org.apache.commons.lang.StringUtils.isNotEmpty(brow)) {
                //对象的构造方法需要传入合并单元格的首行、最后一行、首列、最后一列。
                CellRangeAddress cra = new CellRangeAddress(index, index, index, titleRow.size());
                //在sheet里增加合并单元格
                sheet.addMergedRegion(cra);
                Row row = sheet.createRow(index);
                Cell cell = row.createCell(index);
                cell.setCellValue(brow);
                //单元格样式
                CellStyle cellStyle = wb.createCellStyle();

                cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
                Font font = wb.createFont();
                font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);
                index++;
            }
            Row rowFirst = sheet.createRow(index);//第一个sheet第一行为标题
            rowFirst.setHeight((short) 500);
            //写标题了

            Row rowOne = sheet.createRow(index);
            Row rowTwo = sheet.createRow(index + 1);

            for (int i = 0; i < titleRow.size(); i++) {
                sheet.setColumnWidth((short) i, (short) 4000);// 设置列宽
                //获取第一行的每一个单元格

                if (Integer.valueOf(titleRow.get(i).get("fr").toString()) > 0) {
                    rowFirst = rowTwo;//第一个sheet第一行为标题
                } else {
                    rowFirst = rowOne;//第一个sheet第一行为标题
                }
                Cell cell = rowFirst.createCell(Integer.valueOf(titleRow.get(i).get("fc").toString()));
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                cellStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);// 设置背景色
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                Font font = wb.createFont();
                font.setColor(HSSFColor.WHITE.index);
                cellStyle.setFont(font);
                //往单元格里面写入值
                cell.setCellValue(titleRow.get(i).get("name").toString());
                cell.setCellStyle(cellStyle);

                //设置表头单元格合并
                CellRangeAddress callRangeAddress = new CellRangeAddress(Integer.valueOf(titleRow.get(i).get("fr").toString()), Integer.valueOf(titleRow.get(i).get("er").toString()), Integer.valueOf(titleRow.get(i).get("fc").toString()), Integer.valueOf(titleRow.get(i).get("ec").toString()));//起始行,结束行,起始列,结束列
                sheet.addMergedRegion(callRangeAddress);
            }
            index++;
            index++;
            //列标题下标
            int columnIndex = 0;
            if (titleColumn != null) {
                columnIndex = 1;
            }
            //写内容了
            for (int i = 0; i < bodyRow.size(); i++) {
                //获取list里面存在是数据集对象
                List<String> rows = bodyRow.get(i);
                //创建数据行
                Row row = sheet.createRow(index + i);
                //设置对应单元格的值
                row.setHeight((short) 400);   // 设置每行的高度
                //如果有列标题,并且列标题的长度<内容的长度才set列标题
                if (titleColumn != null && i < titleColumn.size()) {
                    row.createCell(0).setCellValue(titleColumn.get(i));
                }
                for (int j = 0; j < rows.size(); j++) {
                    row.createCell(columnIndex + j).setCellValue(rows.get(j));
                }
            }

            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * poi创建excel
     *
     * @param os          输出流
     * @param sheetName   工作薄名称
     * @param brow        页眉
     * @param titleRow    标题行
     * @param titleColumn 标题列
     * @param bodyRow     主体内容
     * @param bottom      底部合并内容
     */
    public static void createExcelBottom(OutputStream os, String sheetName, String brow, List<String> titleRow, List<String> titleColumn,
                                         List<List<String>> bodyRow, List<Map<String, Object>> bootom) {
        try {
            //行下标
            int index = 0;
            Workbook wb = new XSSFWorkbook();//创建工作簿
            Sheet sheet = wb.createSheet(sheetName);//第一个sheet
            sheet.autoSizeColumn(1, true);
            //标题眉
            if (org.apache.commons.lang.StringUtils.isNotEmpty(brow)) {
                //对象的构造方法需要传入合并单元格的首行、最后一行、首列、最后一列。
                CellRangeAddress cra = new CellRangeAddress(index, index, index, titleRow.size());
                //在sheet里增加合并单元格
                sheet.addMergedRegion(cra);
                Row row = sheet.createRow(index);
                Cell cell = row.createCell(index);
                cell.setCellValue(brow);
                //单元格样式
                CellStyle cellStyle = wb.createCellStyle();

                cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
                Font font = wb.createFont();
                font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);
                index++;
            }
            Row rowFirst = sheet.createRow(index);//第一个sheet第一行为标题
            rowFirst.setHeight((short) 500);
            //写标题了


            for (int i = 0; i < titleRow.size(); i++) {
                sheet.setColumnWidth((short) i, (short) 4000);// 设置列宽
                //获取第一行的每一个单元格
                Cell cell = rowFirst.createCell(i);
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                cellStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);// 设置背景色
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                Font font = wb.createFont();
                font.setColor(HSSFColor.WHITE.index);
                cellStyle.setFont(font);
                //往单元格里面写入值
                cell.setCellValue(titleRow.get(i));
                cell.setCellStyle(cellStyle);
            }
            index++;
            //列标题下标
            int columnIndex = 0;
            if (titleColumn != null) {
                columnIndex = 1;
            }
            //写内容了
            for (int i = 0; i < bodyRow.size(); i++) {
                //获取list里面存在是数据集对象
                List<String> rows = bodyRow.get(i);
                //创建数据行
                Row row = sheet.createRow(index);
                //设置对应单元格的值
                row.setHeight((short) 400);   // 设置每行的高度
                //如果有列标题,并且列标题的长度<内容的长度才set列标题
                if (titleColumn != null && i < titleColumn.size()) {
                    row.createCell(0).setCellValue(titleColumn.get(i));
                }
                for (int j = 0; j < rows.size(); j++) {
                    row.createCell(columnIndex + j).setCellValue(rows.get(j));
                }
                index++;
            }

            Row row = sheet.createRow(index);
            //写底部合并列内容
            for (int i = 0; i < bootom.size(); i++) {
                sheet.setColumnWidth((short) i, (short) 4000);// 设置列宽
                //获取第一行的每一个单元格
                Cell cell = row.createCell(Integer.valueOf(bootom.get(i).get("fc").toString()));
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                cellStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);// 设置背景色
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                Font font = wb.createFont();
                font.setColor(HSSFColor.WHITE.index);
                cellStyle.setFont(font);
                //往单元格里面写入值
                cell.setCellValue(bootom.get(i).get("value").toString());
                cell.setCellStyle(cellStyle);

                //设置表头单元格合并
                CellRangeAddress callRangeAddress = new CellRangeAddress(index, index, Integer.valueOf(bootom.get(i).get("fc").toString()), Integer.valueOf(bootom.get(i).get("ec").toString()));//起始行,结束行,起始列,结束列
                sheet.addMergedRegion(callRangeAddress);
            }

            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * poi创建excel
     *
     * @param os          输出流
     * @param sheetName   工作薄名称
     * @param brow        页眉
     * @param titleRow    标题行
     * @param titleColumn 标题列
     * @param bodyRow     主体内容
     */
    public static void createExcelTitleAndBottom(OutputStream os, String sheetName, String brow, List<Map<String, Object>> titleRow, List<String> titleColumn,
                                                 List<List<String>> bodyRow, List<Map<String, Object>> bootom) {
        try {
            //行下标
            int index = 0;
            Workbook wb = new XSSFWorkbook();//创建工作簿
            Sheet sheet = wb.createSheet(sheetName);//第一个sheet
            sheet.autoSizeColumn(1, true);
            //标题眉
            if (org.apache.commons.lang.StringUtils.isNotEmpty(brow)) {
                //对象的构造方法需要传入合并单元格的首行、最后一行、首列、最后一列。
                CellRangeAddress cra = new CellRangeAddress(index, index, index, titleRow.size());
                //在sheet里增加合并单元格
                sheet.addMergedRegion(cra);
                Row row = sheet.createRow(index);
                Cell cell = row.createCell(index);
                cell.setCellValue(brow);
                //单元格样式
                CellStyle cellStyle = wb.createCellStyle();

                cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
                Font font = wb.createFont();
                font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);
                index++;
            }
            Row rowFirst = sheet.createRow(index);//第一个sheet第一行为标题
            rowFirst.setHeight((short) 500);
            //写标题了

            Row rowOne = sheet.createRow(index);
            Row rowTwo = sheet.createRow(index + 1);

            for (int i = 0; i < titleRow.size(); i++) {
                sheet.setColumnWidth((short) i, (short) 4000);// 设置列宽
                //获取第一行的每一个单元格

                if (Integer.valueOf(titleRow.get(i).get("fr").toString()) > 0) {
                    rowFirst = rowTwo;//第一个sheet第一行为标题
                } else {
                    rowFirst = rowOne;//第一个sheet第一行为标题
                }
                Cell cell = rowFirst.createCell(Integer.valueOf(titleRow.get(i).get("fc").toString()));
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                cellStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);// 设置背景色
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                Font font = wb.createFont();
                font.setColor(HSSFColor.WHITE.index);
                cellStyle.setFont(font);
                //往单元格里面写入值
                cell.setCellValue(titleRow.get(i).get("name").toString());
                cell.setCellStyle(cellStyle);

                //设置表头单元格合并
                CellRangeAddress callRangeAddress = new CellRangeAddress(Integer.valueOf(titleRow.get(i).get("fr").toString()), Integer.valueOf(titleRow.get(i).get("er").toString()), Integer.valueOf(titleRow.get(i).get("fc").toString()), Integer.valueOf(titleRow.get(i).get("ec").toString()));//起始行,结束行,起始列,结束列
                sheet.addMergedRegion(callRangeAddress);
            }
            index++;
            index++;
            //列标题下标
            int columnIndex = 0;
            if (titleColumn != null) {
                columnIndex = 1;
            }
            //写内容了
            for (int i = 0; i < bodyRow.size(); i++) {
                //获取list里面存在是数据集对象
                List<String> rows = bodyRow.get(i);
                //创建数据行
                Row row = sheet.createRow(index);
                //设置对应单元格的值
                row.setHeight((short) 400);   // 设置每行的高度
                //如果有列标题,并且列标题的长度<内容的长度才set列标题
                if (titleColumn != null && i < titleColumn.size()) {
                    row.createCell(0).setCellValue(titleColumn.get(i));
                }
                for (int j = 0; j < rows.size(); j++) {
                    row.createCell(columnIndex + j).setCellValue(rows.get(j));
                }
                index++;
            }


            Row row = sheet.createRow(index);
            //写底部合并列内容
            for (int i = 0; i < bootom.size(); i++) {
                sheet.setColumnWidth((short) i, (short) 4000);// 设置列宽
                //获取第一行的每一个单元格
                Cell cell = row.createCell(Integer.valueOf(bootom.get(i).get("fc").toString()));
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                cellStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);// 设置背景色
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                Font font = wb.createFont();
                font.setColor(HSSFColor.WHITE.index);
                cellStyle.setFont(font);
                //往单元格里面写入值
                cell.setCellValue(bootom.get(i).get("value").toString());
                cell.setCellStyle(cellStyle);

                //设置表头单元格合并
                CellRangeAddress callRangeAddress = new CellRangeAddress(index, index, Integer.valueOf(bootom.get(i).get("fc").toString()), Integer.valueOf(bootom.get(i).get("ec").toString()));//起始行,结束行,起始列,结束列
                sheet.addMergedRegion(callRangeAddress);
            }

            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取Excel数据内容
     *
     * @param is
     * @param iscolumnTitle 是否包含序号
     * @return Map 包含单元格数据内容的Map对象
     */
    public static List<List<String>> readExcelContent(InputStream is, boolean iscolumnTitle) throws IOException, ParseException {
        List<List<String>> resultList = new ArrayList<>();
        Workbook wb = new XSSFWorkbook(is);
        Sheet sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        Row row = sheet.getRow(0);
        //拿到总列数
        int colNum = row.getPhysicalNumberOfCells();
        int columnIndex = 0;
        //如果存在列标题
        if (!iscolumnTitle) {
            columnIndex = 1;
        }
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            List<String> list = new ArrayList<>();
            row = sheet.getRow(i);
            if (row == null) {
                resultList.add(new ArrayList<String>());
                continue;
            }
            for (int j = columnIndex; j < colNum; j++) {
                String str = getCellFormatValue(row.getCell(j)).trim();
                list.add(str);
            }
            resultList.add(list);
        }
        return resultList;
    }

    /**
     * 根据Cell类型设置数据
     *
     * @param cell
     * @return
     */
    private static String getCellFormatValue(Cell cell) throws ParseException {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_NUMERIC:
                    System.out.println(cell.getNumericCellValue());
                    DecimalFormat df = new DecimalFormat();
                    cellvalue = String.valueOf(df.parse(cell.getNumericCellValue() + ""));
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式

                        //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        //cellvalue = cell.getDateCellValue().toLocaleString();

                        //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                        // 如果是纯数字
                    } else {
                        cellvalue = "";
                    }
                    break;
                // 如果当前Cell的Type为STRIN
                case Cell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
}
