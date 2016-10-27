package frame;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MyExcel {
    public static Object[][] getExcelData(String filePath, String fileName, String sheetName) throws IOException {
        java.io.File file = new java.io.File(filePath + fileName);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = null;
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        if (fileExtensionName.equals(".xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        }
        else if (fileExtensionName.equals(".xls")) {
            workbook = new HSSFWorkbook(inputStream);
        }
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        List<Object> records = new ArrayList<Object>();
        String fields[] = null;
        for (int i = 1; i < rowCount + 1; i++) {
            Row row = sheet.getRow(i);
            int cellNum = row.getLastCellNum();
            if (cellNum > 0) {
                fields = new String[row.getLastCellNum()];
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    try {
                        fields[j] = row.getCell(j).getStringCellValue();
                    }
                    catch (Exception e) {
                        fields[j] = null;
                    }
                }
            }
            else {
                break;
            }
            records.add(fields);
        }
        Object[][] results = new Object[records.size()][];
        for (int i = 0; i < records.size(); i++) {
            results[i] = (Object[]) records.get(i);
        }
        return results;
    }
}
