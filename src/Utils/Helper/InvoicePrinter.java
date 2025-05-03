package Utils.Helper;

import BLL.AccountBLL;
import BLL.ComputerSessionBLL;
import BLL.FoodBillBLL;
import BLL.StaffBLL;
import com.itextpdf.text.pdf.BaseFont;
import jdk.jshell.execution.Util;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class InvoicePrinter {
    private FoodBillBLL foodBillBLL;
    private AccountBLL accountBLL;
    private StaffBLL staffBLL;
    private ComputerSessionBLL computerSessionBLL;
    private ArrayList<Object[]> detailsFoodOrderBill;
    private HashMap<String, Object> detailsSessionBill;

    public InvoicePrinter() {
        this.accountBLL = new AccountBLL();
        this.staffBLL = new StaffBLL();
        this.foodBillBLL = new FoodBillBLL();
        this.computerSessionBLL = new ComputerSessionBLL();
    }

    public void printInvoice(String htmlContent) {
        Date date = new Date();
        String fileName = System.getProperty("user.dir") + "/src/Bills/" + "Invoice_" + date.toString().replaceAll(" ", "").replaceAll(":", "-") + ".pdf";

        try {
            ITextRenderer renderer = new ITextRenderer();

            // Đường dẫn font
            String fontPath = System.getProperty("user.dir") + "/src/Utils/Fonts/DejaVuSans.ttf";
            renderer.getFontResolver().addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            renderer.setDocumentFromString(htmlContent);
            renderer.layout();

            try (OutputStream os = new FileOutputStream(fileName)) {
                renderer.createPDF(os);
            }

            JOptionPane.showMessageDialog(null,
                    "In hóa đơn thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi in hóa đơn: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Phần dành cho in hóa đơn thức ăn
    private String buildFoodBillTableRows() {
        StringBuilder result = new StringBuilder();
        int totalMoney = 0;
        result.append("<tbody>");

        for (int i = 0; i < this.detailsFoodOrderBill.size(); i++) {
            Object[] row = this.detailsFoodOrderBill.get(i);
            int quantity = Integer.parseInt(row[6].toString());
            int price = Integer.parseInt(row[8].toString());
            int lineTotal = quantity * price;
            totalMoney += lineTotal;

            result.append("<tr>")
                    .append("<td>").append(i + 1).append("</td>")
                    .append("<td>").append(row[5]).append("</td>")
                    .append("<td>").append(row[7]).append("</td>")
                    .append("<td>").append(quantity).append("</td>")
                    .append("<td>").append(Utils.Helper.Comon.formatMoney(String.valueOf(price))).append("</td>")
                    .append("<td>").append(Utils.Helper.Comon.formatMoney(String.valueOf(lineTotal))).append("</td>")
                    .append("</tr>");
        }

        result.append("</tbody></table>")
                .append("<p class=\"right\"><strong>Total Bill: ")
                .append(Utils.Helper.Comon.formatMoney(String.valueOf(totalMoney)))
                .append("</strong></p>");

        return result.toString();
    }

    private String buildingDetailHeaderPDF() {
        String date = this.detailsFoodOrderBill.get(0)[9].toString();
        String customer = this.detailsFoodOrderBill.get(0)[1].toString().equals("0") ? "Tài khoản khách (Order riêng)" : this.accountBLL.getAccountById(Integer.parseInt(this.detailsFoodOrderBill.get(0)[1].toString())).getUsername();
        String employee = this.accountBLL.getAccountById(Integer.parseInt(this.detailsFoodOrderBill.get(0)[2].toString())).getUsername() + " - " + this.staffBLL.getStaffById(Integer.parseInt(this.detailsFoodOrderBill.get(0)[2].toString())).getFullName();
        String status = this.detailsFoodOrderBill.get(0)[this.detailsFoodOrderBill.get(0).length - 1].toString();
        String result =
                "<p><strong>Billing date:</strong> " + date + "</p>" +
                        "<p><strong>Customer's Name:</strong> " + customer + "</p>" +
                        "<p><strong>Employee Name:</strong> " + employee + "</p>" +
                        "<p><strong>Invoice Status:</strong> " + status + "</p>";
        return result;
    }


    public void printFoodOrder(int foodBillId) {
        System.out.printf("Đã ấn in hóa đơn thức ăn");
        this.detailsFoodOrderBill = this.foodBillBLL.getDetailFoodBillById(foodBillId);

        String foodRows = this.buildFoodBillTableRows();
        String detailFoodOrder = this.buildingDetailHeaderPDF();

        String html =
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                        "<head>" +
                        "<meta charset=\"UTF-8\" />" +
                        "<title>Hóa đơn</title>" +
                        "<style>" +
                        "body {" +
                        "    font-family: 'Roboto', sans-serif;" +
                        "    padding: 40px;" +
                        "    background: #f4f4f4;" +
                        "}" +
                        ".invoice-container {" +
                        "    max-width: 800px;" +
                        "    margin: auto;" +
                        "    background: #fff;" +
                        "    padding: 30px;" +
                        "    border-radius: 10px;" +
                        "}" +
                        "h2 {" +
                        "    text-align: center;" +
                        "    color: #2980b9;" +
                        "}" +
                        "table {" +
                        "    width: 100%;" +
                        "    border-collapse: collapse;" +
                        "    margin-top: 20px;" +
                        "    font-size: 15px;" +
                        "}" +
                        "th, td {" +
                        "    border: 1px solid #ddd;" +
                        "    padding: 12px;" +
                        "    text-align: center;" +
                        "}" +
                        "th {" +
                        "    background-color: #ecf0f1;" +
                        "    font-weight: bold;" +
                        "}" +
                        "tbody tr:nth-child(even) {" +
                        "    background-color: #f9f9f9;" +
                        "}" +
                        ".right {" +
                        "    text-align: right;" +
                        "    margin-top: 20px;" +
                        "    font-size: 16px;" +
                        "}" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class=\"invoice-container\">" +
                        "<h2>Customer Invoice</h2>" +
                        detailFoodOrder +
                        "<table>" +
                        "<thead>" +
                        "<tr>" +
                        "<th>Order Number</th>" +
                        "<th>Item Code</th>" +
                        "<th>Item Name</th>" +
                        "<th>Amount</th>" +
                        "<th>Unit Price</th>" +
                        "<th>Total Money</th>" +
                        "</tr>" +
                        "</thead>" +
                        foodRows +
                        "</div>" +
                        "</body>" +
                        "</html>";
        this.printInvoice(html);
    }

    public void printSessionOrder(int sessionId) {
        this.detailsSessionBill = this.computerSessionBLL.getComputerInfoAndRoomInfoOfSession(sessionId);

        String infoRoom = this.detailsSessionBill.get("room_id") + " - " + this.detailsSessionBill.get("room_name");
        String infoComputer = this.detailsSessionBill.get("computer_id") + " - " + this.detailsSessionBill.get("name");
        String infoPricePerHours = Utils.Helper.Comon.formatMoney(this.detailsSessionBill.get("price_per_hour") + "");
        String startTime = this.detailsSessionBill.get("start_time") + "";
        String endTime = this.detailsSessionBill.get("end_time") + "";
        String totalTimePlay = ChangeMinToDate.changeData((int) this.detailsSessionBill.get("duration"));
        String totalCost = Utils.Helper.Comon.formatMoney(this.detailsSessionBill.get("total_cost") + "");

        String html =
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                        "<head>" +
                        "<meta charset=\"UTF-8\" />" +
                        "<title>Hóa đơn phiên chơi</title>" +
                        "<style>" +
                        "body {" +
                        "    font-family: 'Roboto', sans-serif;" +
                        "    padding: 40px;" +
                        "    background: #f4f4f4;" +
                        "}" +
                        ".invoice-container {" +
                        "    max-width: 800px;" +
                        "    margin: auto;" +
                        "    background: #fff;" +
                        "    padding: 30px;" +
                        "    border-radius: 10px;" +
                        "}" +
                        "h2 {" +
                        "    text-align: center;" +
                        "    color: #2980b9;" +
                        "}" +
                        "table {" +
                        "    width: 100%;" +
                        "    border-collapse: collapse;" +
                        "    margin-top: 20px;" +
                        "    font-size: 15px;" +
                        "}" +
                        "th, td {" +
                        "    border: 1px solid #ddd;" +
                        "    padding: 12px;" +
                        "    text-align: left;" +
                        "}" +
                        "th {" +
                        "    background-color: #ecf0f1;" +
                        "    font-weight: bold;" +
                        "}" +
                        ".right {" +
                        "    text-align: right;" +
                        "    margin-top: 20px;" +
                        "    font-size: 16px;" +
                        "}" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class=\"invoice-container\">" +
                        "<h2>Session Invoice</h2>" +
                        "<table>" +
                        "<tr><th>Room</th><td>" + infoRoom + "</td></tr>" +
                        "<tr><th>Computer</th><td>" + infoComputer + "</td></tr>" +
                        "<tr><th>Price/Hour</th><td>" + infoPricePerHours + "</td></tr>" +
                        "<tr><th>Start Time</th><td>" + startTime + "</td></tr>" +
                        "<tr><th>End Time</th><td>" + endTime + "</td></tr>" +
                        "<tr><th>Total Play Time</th><td>" + totalTimePlay + "</td></tr>" +
                        "<tr><th>Total Cost</th><td>" + totalCost + "</td></tr>" +
                        "</table>" +
                        "</div>" +
                        "</body>" +
                        "</html>";

        this.printInvoice(html);
    }

}
