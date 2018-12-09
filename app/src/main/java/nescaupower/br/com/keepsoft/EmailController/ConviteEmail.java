package nescaupower.br.com.keepsoft.EmailController;

import nescaupower.br.com.keepsoft.Factory.Model.Convite;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.Utils.Email;

public class ConviteEmail {

    public static  void enviarEmail(Usuario rementente, Usuario destino, Convite convite, Projeto projeto){

        String msg;
        msg =  destino.getNome() +
                " recebeu um convite de " +
                rementente.getNome() +
                " para o projeto " +
                projeto.getNome() +
                " participando como: " +
                convite.getFuncao().toString();

        Email email = new Email(destino.getEmail(), "Convite para projeto "+projeto.getNome(),
                msg);
        new Thread(){
            @Override
            public void run() {
                email.enviarGmail();
            }
        }.start();

    }
}
