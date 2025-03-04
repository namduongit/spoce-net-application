package Utils.Helper;

import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.*;

import Utils.Config.ConfigEmail;

public class SendEmail {
    private Session session;

    public void setUpServerProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Máy chủ SMTP Gmail
        properties.put("mail.smtp.port", "587"); // Cổng SMTP (587 cho TLS, 465 cho SSL)
        properties.put("mail.smtp.auth", "true"); // Yêu cầu xác thực
        properties.put("mail.smtp.starttls.enable", "true"); // Bật TLS mã hóa kết nối

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ConfigEmail.EMAIL_SENDER, ConfigEmail.PASSWORD_SENDER);
            }
        });
    }

    public String createDigitToReset() {
        String result = "";
        for (int i = 0; i < 8; i++) {
            result += new Random().nextInt(0, 9);
        }
        return result;
    }

    public boolean sendNewOTP(String recipient, String titleEmail, String digitToReset, String userName) {
        this.setUpServerProperties();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ConfigEmail.EMAIL_SENDER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(titleEmail);


            String htmlContent =
                    "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100vh;\">"
                    + "<div style=\"max-width: 500px; width: 100%; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); margin: auto;\">"

                    + "<div style=\"display: flex; align-items: center; border-bottom: 2px solid #000; padding-bottom: 15px;\">"
                    + "<img src=\"https://upload.wikimedia.org/wikipedia/commons/5/5f/Black_Kawaii_Illustration_Gaming_Logo.png\" alt=\"Spoce Net Gaming\" width=\"50px\" style=\"border-radius: 100%;\">"
                    + "<span style=\"font-size: 20px; font-weight: bold; margin-left: 20px; margin-top: 10px; color: black;\">Spoce Net Gaming</span>"
                    + "</div>"

                    + "<div style=\"padding-top: 20px;\">"
                    + "<h2 style=\"color: #000;\">Xin chào " + userName + ",</h2>"
                    + "<p style=\"color: #333; font-size: 16px;\">Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu đăng nhập cho tài khoản Spoce Net Gaming của bạn.</p>"
                    + "<p style=\"font-size: 22px; font-weight: bold; text-align: center; background: #f8f8f8; padding: 10px; border-radius: 5px; border: 1px dashed red; color: black;\">"
                    + digitToReset + "</p>"
                    + "<p style=\"color: #333;\">Mã khôi phục mật khẩu của bạn có giá trị trong <strong>10 phút</strong>, hãy cập nhật lại mật khẩu cho tài khoản của bạn.</p>"
                    + "<p style=\"color: #333;\">Tuyệt đối không chia sẻ mã này cho bất kì ai dù người đó tự nhận mình là quản trị viên của <strong>Spoce Net Gaming</strong>.</p>"
                    + "<p style=\"color: #333;\">Nếu bạn không thực hiện yêu cầu này, bạn có thể bỏ qua hoặc liên hệ hỗ trợ với chúng tôi.</p>"
                    + "<p style=\"color: #777; margin-top: 20px;\">Trân trọng,<br><strong>Spoce Net Gaming Team</strong></p>"
                    + "</div>"

                    + "<div style=\"background-color: #333; color: #fff; text-align: center; padding: 15px; border-radius: 0 0 8px 8px; margin-top: 20px;\">"
                    + "<p style=\"margin: 0; font-size: 14px;\">Liên hệ với đội ngũ hỗ trợ:</p>"
                    + "<div style=\"margin-top: 10px;\">"
                    + "<a href=\"https://www.facebook.com/nduongit\" style=\"margin: 0 10px;\">"
                    + "<img src=\"https://cdn3.iconfinder.com/data/icons/social-media-black-white-2/512/BW_Facebook_glyph_svg-512.png\" alt=\"Facebook\" width=\"30px\" style=\"background-color: white; border-radius: 100%; padding: 1px;\">"
                    + "</a>"
                    + "<a href=\"https://github.com/namduongit\" style=\"margin: 0 10px;\">"
                    + "<img src=\"https://cdn1.iconfinder.com/data/icons/logotypes/32/github-512.png\" alt=\"GitHub\" width=\"30px\" style=\"background-color: white; border-radius: 100%; padding: 1px;\">"
                    + "</a>"
                    + "<a href=\"mailto:nguyennamduong205@gmail.com\" style=\"margin: 0 10px;\">"
                    + "<img src=\"https://cdn0.iconfinder.com/data/icons/social-circle-3/72/Email-512.png\" alt=\"Email\" width=\"30px\" style=\"background-color: white; border-radius: 100%; padding: 1px;\">"
                    + "</a>"
                    + "</div>"
                    + "</div>"

                    + "</div>"
                    + "</body>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
