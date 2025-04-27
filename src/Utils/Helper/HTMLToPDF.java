package Utils.Helper;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

public class HTMLToPDF {
    public static void main(String[] args) {
        try {
            String outputFile = "table_output.pdf";

            // Nội dung HTML với CSS cho bảng
            String htmlContent = """
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; margin: 20px; }
                        h1 { color: #2E86C1; text-align: center; font-size: 24px; }
                        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
                        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
                        th { background-color: #4CAF50; color: white; }
                        tr:nth-child(even) { background-color: #f2f2f2; }
                        tr:hover { background-color: #ddd; }
                        .footer { margin-top: 20px; font-size: 12px; color: #555; text-align: center; }
                    </style>
                </head>
                <body>
                    <h1>Danh Sách Nhân Viên</h1>
                    <table>
                        <tr>
                            <th>ID</th>
                            <th>Tên</th>
                            <th>Phòng Ban</th>
                            <th>Lương (VNĐ)</th>
                        </tr>
                        <tr>
                            <td>001</td>
                            <td>Nguyễn Văn A</td>
                            <td>IT</td>
                            <td>15,000,000</td>
                        </tr>
                        <tr>
                            <td>002</td>
                            <td>Trần Thị B</td>
                            <td>Nhân Sự</td>
                            <td>12,000,000</td>
                        </tr>
                        <tr>
                            <td>003</td>
                            <td>Lê Văn C</td>
                            <td>Kế Toán</td>
                            <td>18,000,000</td>
                        </tr>
                    </table>
                    <div class="footer">Báo cáo được tạo bởi hệ thống - 2025</div>
                </body>
                </html>
                """;

            // Tạo PdfWriter
            PdfWriter writer = new PdfWriter(outputFile);
            PdfDocument pdf = new PdfDocument(writer);

            // Chuyển HTML sang PDF
            HtmlConverter.convertToPdf(htmlContent, pdf, null);

            // Đóng tài liệu
            pdf.close();
            System.out.println("PDF đã được tạo tại: " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}