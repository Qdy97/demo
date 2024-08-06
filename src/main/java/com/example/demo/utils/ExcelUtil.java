package com.example.demo.utils;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public class ExcelUtil {

    public static Map<String, List<Double>> readExcelLis(MultipartFile file) {
        if (file.getOriginalFilename().endsWith(".xlsx")) {
            return readExcelLisForXlsx(file);
        } else {
//            return readExcelLisForXls(startrow, startcol, sheetnum, file);
            return new HashMap<>();
        }
    }

    private static Map<String, List<Double>> readExcelLisForXlsx(MultipartFile multipartFile) {
        Map<String, List<Double>> map = new HashMap<>();
        try {

            File file = null;
            InputStream ins = multipartFile.getInputStream();
            file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();

            InputStream inputStream = null;
            inputStream = new FileInputStream(file);

            //查看文件时间
            ReadFileTimeUtils.getCreationTime(file);

            file.deleteOnExit();
            FileInputStream fi = (FileInputStream) inputStream;
            XSSFWorkbook wb = new XSSFWorkbook(fi);
            //循环每个工作页

            for (Sheet sheet : wb) {
                //循环每行
                for (Row row : sheet) {
                    List<Double> list = new ArrayList<>();
                    for (int i = 0; i < row.getLastCellNum(); i++) {
                        String cellValue = String.valueOf(getCellFormatValue(row.getCell(i)));
                        if (!isNumber(cellValue) || StringUtils.isEmpty(cellValue)) {
                            list.add(0.0);
                        } else {
                            list.add(Double.valueOf(cellValue));
                        }

                        System.out.println(cellValue);
                    }
                    map.put(String.valueOf(row.getRowNum()), list);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;

    }

    private static File mulToFile(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        File file = new File(fileName);
        OutputStream out = null;
        try

        {
            //获取文件流，以文件流的方式输出到新文件
//    InputStream in = multipartFile.getInputStream();
            out = new FileOutputStream(file);
            byte[] ss = multipartFile.getBytes();
            for (int i = 0; i < ss.length; i++) {
                out.write(ss[i]);
            }
        } catch (
                IOException e)

        {
            e.printStackTrace();
        } finally

        {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        File f = new File(file.toURI());
        if (f.delete())

        {
            System.out.println("删除成功");
        } else

        {
            System.out.println("删除失败");
        }
        return file;
    }

    private static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA: {
                    //判断cell是否为日期格式
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    /**
     * 数字校验
     *
     * @attention: 可以是整数，也可以是小数
     * @date: 2020年11月12日 0012 10:45
     * @param: number 整数、小数
     * @return: boolean true-可能是整数，也可能是小数，false-既不是整数也不是小数
     */

    public static boolean isNumber(String number) {

        if (StringUtils.isEmpty(number)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        if (!pattern.matcher(number).matches()) {
            return false;
        }
        return true;
    }


    /**
     * @param response 使用response可以导出到浏览器
     * @throws Exception
     * @MethodName : listToExcel
     * @Description : 导出Excel（导出到浏览器，工作表的大小是2003支持的最大值）
     */
    public static <T> void listToExcel(
            Map<String, List<Double>> map,
            HttpServletResponse response,
            String name) throws Exception {
        try {
            //设置response头信息
            response.reset();
            //改成输出excel文件
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-disposition", "attachment;filename="+name+".xlsx");
            //创建工作簿并发送到浏览器
            OutputStream out = response.getOutputStream();

            if (map.size() == 0) {
                throw new Exception("数据源中没有任何数据");
            }

            // 创建工作薄 xlsx
            XSSFWorkbook xssWorkbook = new XSSFWorkbook();

            // 创建工作表
            XSSFSheet sheet = xssWorkbook.createSheet("1");
            fillSheet(sheet, map);

            xssWorkbook.write(out);
        } catch (Exception e) {
            //如果是Exception，则直接抛出
            throw e;
            //否则将其它异常包装成Exception再抛出
        }

    }

    private static void fillSheet(XSSFSheet sheet, Map<String, List<Double>> map) {

        for (String s : map.keySet()) {
            //取当前行
            XSSFRow row = sheet.createRow(Integer.valueOf(s));
            //取当前行的所有基础数据
            List<Double> doubles = map.get(s);
            //循环基础数据
            for (int i = 0; i < doubles.size(); i++) {
                row.createCell(i).setCellValue(calculation(doubles.get(i)));
            }
        }
    }

    private static double calculation(Double aDouble) {
        if (aDouble < 2000){
            return aDouble*0.03;
        }
        //定义一个总量
        double result = 2000*0.03;
        //如果大于2000,则减去2000 在除以3000  根据得到的数字大小进行循环
        double b = aDouble - 2000;
        int index = (int) (b/3000);
        for (int i = 0; i < index; i++) {
            result += 3000 * (0.05 * (i+1) );
        }

        result += (aDouble - 2000 - 3000*index) * (0.05 * (index+1) );

        return result;
    }


}
