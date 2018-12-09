package nescaupower.br.com.keepsoft.EmailController;


import android.util.Base64;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.Utils.Email;

public class EsqueceuSenhaEmail {
    public void enviarEmail(Usuario usuario) {

        String msg;
        String id = usuario.getId().toString();
        byte[] bytes = id.getBytes();
        String idBytes = Base64.encodeToString(bytes, Base64.DEFAULT);
        msg = "Acesse o link abaixo para alterar sua senha e acessar novamente o aplicativo do KeepSoft! <br>" +
                "Link: "+Settings.IPSITE+"/senha/"+idBytes;

        Email email = new Email(usuario.getEmail(), "Esqueceu a senha?",
                msg);
        new Thread() {
            @Override
            public void run() {
                email.enviarGmail();
            }
        }.start();
    }
}
