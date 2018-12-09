package nescaupower.br.com.keepsoft.EmailController;


import java.text.SimpleDateFormat;

import nescaupower.br.com.keepsoft.Factory.Model.Reuniao;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.Utils.Email;

public class ReuniaoEmail {
    public static void enviarEmail(Usuario rementente, Usuario destino, Reuniao reuniao) {

        String msg;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String horario = format.format(reuniao.getDataInicio()) + " - das "+reuniao.getHoraInicioFormat() + " às "+reuniao.getHoraFimFormat();
        msg = destino.getNome() +
                " agora é participante da reunião feita pelo " +
                rementente.getNome() +
                ".<br>Nome: " +
                reuniao.getNome() +
                ".<br> Asssunto: " +
                reuniao.getAssunto()+
                ".<br> Local: " +
                reuniao.getLocal() +
                ".<br> Horário: " +
                horario +
                ".<br> Projeto: " +
                reuniao.getProjeto().getNome();

        Email email = new Email(destino.getEmail(), "Reunião do projeto " + reuniao.getProjeto().getNome(),
                msg);
        new Thread() {
            @Override
            public void run() {
                email.enviarGmail();
            }
        }.start();

    }


}
