import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SendEmail {

    public static void sendEmail(){
        String from = "xiaoyao_shaw@126.com";

        String to = "skylerxiao86@qq.com";

        // 发件箱126授权码
        final String pwd = "GLFHZLFRQSSFMQHL";

        // 指定发送邮件的主机
        String host = "smtp.126.com";

        Properties pros = new Properties();

        pros.setProperty("mail.smtp.host", host);

        pros.put("mail.smtp.port", 25);

        pros.put("mail.transport.protocol", "smtp");

        pros.put("mail.smtp.auth", "true");

        pros.put("mail.smtp.ssl.enable", "false");

        pros.put("mail.debug", "true");

        // 会话session
        Session session = Session.getDefaultInstance(pros);

        try {

            // 创建默认的消息对象
            MimeMessage message = new MimeMessage(session);

            // 设置头部字段 from to
            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // 设置 subject
            message.setSubject("This is the Subject Line!");

            // 消息体
            message.setText("Hello,xiaoxiang! This is a test message!");
//            message.setText(text);

            Transport transport = session.getTransport();

            // 连接
            transport.connect(from, pwd);

            // 发送
            transport.sendMessage(message, message.getAllRecipients());

            System.out.println("Sent message successfully...");

        } catch (MessagingException mex) {

            mex.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public static void sendTask() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(10);
        // 执行任务
        // 1s 后开始执行，每 3s 执行一次
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            sendEmail();
            System.out.println("Send Email：" + new Date());
        }, 0, 3, TimeUnit.SECONDS);

    }

    public static void main(String[] args) throws InterruptedException {

        try {
            sendTask();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread.currentThread().join();

    }

}