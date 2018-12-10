package nescaupower.br.com.keepsoft.EmailController;

import android.util.Base64;
import android.util.Log;

import nescaupower.br.com.keepsoft.Config.Settings;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.Utils.Email;

public class ConfirmEmail {

    public void enviarEmail(Usuario usuario){
        String msg;
        String id = usuario.getId().toString();
        byte[] bytes = id.getBytes();
        String idBytes = Base64.encodeToString(bytes, Base64.DEFAULT);
        msg = "Acesse o link abaixo para confirmar seu email e acessar o aplicativo do KeepSoft! <br>" +
                "<a href='"+Settings.IPSITE+"/confirmEmail/"+idBytes+"'>Confirme aqui!</a>";
        Log.e("myConfirm", id + " - " + idBytes);
        Email email = new Email(usuario.getEmail(), "Cofirmar email.",
                msg);
        new Thread() {
            @Override
            public void run() {
                email.enviarGmail();
            }
        }.start();
    }
}
