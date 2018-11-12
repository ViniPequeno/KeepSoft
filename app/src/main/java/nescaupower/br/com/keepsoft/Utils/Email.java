package nescaupower.br.com.keepsoft.Utils;

import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
    private final String EmailEnvio = "viniciuspedro350@gmail.com";
    private final String SenhaEnvio = "Pedro8251";

    private String emailDestinario;
    private String assunto;
    private String msg;

    public Email(String emailDestinario, String assunto, String msg) {
        this.emailDestinario = emailDestinario;
        this.assunto = assunto;
        this.msg = msg;
    }

    public String getEmailDestinario() {
        return emailDestinario;
    }

    public void setEmailDestinario(String emailDestinario) {
        this.emailDestinario = emailDestinario;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean enviarGmail() {
        boolean retorno = false;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session s = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailEnvio, SenhaEnvio);
            }
        });

        try {
            MimeMessage message = new MimeMessage(s);
            message.setFrom(new InternetAddress(EmailEnvio));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.emailDestinario));
            message.setHeader("content-type", "text/html");
            message.setSubject((this.assunto));
            message.setContent(this.msg, "text/html; charset=utf-8");

            Transport.send(message);

            retorno = true;
        } catch (AddressException ex) {
            Log.e("ErroEmail", ex.getMessage());
            retorno = false;
        } catch (MessagingException ex) {
            Log.e("ErroEmail", ex.getMessage());
            retorno = false;
        }
        return retorno;
    }

}
