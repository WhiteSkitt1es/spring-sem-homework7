package ru.app.service.serviceProduct;



import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import ru.bikbaev.spring_hw_7_1.model.Order;
import ru.bikbaev.spring_hw_7_1.model.Product;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrder {

    /**
     * Формирует список заявки для пополнения склада с учетом разницы между фактическим остатком и минимально допустимым
     * @param products список товаров
     * @return
     */

    public List<Order> creatOrder(List<Product> products) {
        List<Order> orders = new ArrayList<>();
        for (Product product : products) {

            int id = product.getId();
            String name = product.getName();
            int quantity = product.getMinimum_quantity() - product.getBalance_in_stock();
            int price = product.getPurchase_price();

            Order order = new Order();
            order.setIdProduct(id);
            order.setProductName(name);
            order.setPurchasePriceProduct(price);

            if (quantity <= 0) {
                order.setQuantity(0);
            } else {
                order.setQuantity(quantity);
            }
            orders.add(order);
        }
        return orders;
    }

    /**
     * Формирует Exel файл  Order.xlx с сформированным списком заявок
     * сохраняется в пути src/main/resources/static/document/order.xls
     * @param orders список заявок на пополнения склада
     */
    public void createOrderXLS(List<Order> orders) {
        String url = "src/main/resources/static/document/order.xls";
        try (Workbook workbook = new HSSFWorkbook()) {

            Sheet sheet = workbook.createSheet();
            Row headerRow = sheet.createRow(0);
            String[] headers = {"id", "Наименование", "Цена", "Закупка"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowNum = 1;

            for (Order order : orders) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(order.getIdProduct());
                row.createCell(1).setCellValue(order.getProductName());
                row.createCell(2).setCellValue(order.getPurchasePriceProduct());
                row.createCell(3).setCellValue(order.getQuantity());
            }

            File file = new File(url);
            if (file.exists()) {
                file.delete();
            }


            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            file.createNewFile();

            try (FileOutputStream fileOutputStream = new FileOutputStream(url)) {
                workbook.write(fileOutputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
