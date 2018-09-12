package nescaupower.br.com.keepsoft.Views.Projeto;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import nescaupower.br.com.keepsoft.Controller.PerfilController;
import nescaupower.br.com.keepsoft.Controller.ProjetoController;
import nescaupower.br.com.keepsoft.Factory.Factory;
import nescaupower.br.com.keepsoft.Factory.Model.Perfil;
import nescaupower.br.com.keepsoft.Factory.Model.Projeto;
import nescaupower.br.com.keepsoft.Factory.Model.Usuario;
import nescaupower.br.com.keepsoft.R;
import nescaupower.br.com.keepsoft.Views.Usuario.PaginaInicialActivity;

public class CadastroProjetoActivity extends AppCompatActivity {

    private ConstraintLayout root;
    private Calendar dataAtual = Calendar.getInstance();
    private EditText txtNome;
    private EditText txtDescricao;
    private EditText txtDataPrevista;
    private ProjetoController projetoController;
    private PerfilController perfilController;
    private DatePickerDialog dialogDataPrevista;
    private DatePickerDialog.OnDateSetListener listenerDataSelecionadaDataPrevista;
    private DatePickerDialog.OnCancelListener listenerSelecaoCanceladaDataPrevista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_projeto);
        root = findViewById(R.id.teste);

        projetoController = new ProjetoController(getApplicationContext());

        perfilController = new PerfilController(getApplicationContext());

        txtNome = findViewById(R.id.txtNome);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtDataPrevista = findViewById(R.id.txtDataPrevista);
        txtDataPrevista.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialogDataPrevista.show();
                }
            }
        });

        //TODO: ajustar foco após selecionar data
        listenerDataSelecionadaDataPrevista = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date data = new GregorianCalendar(year, month, dayOfMonth).getTime();
                txtDataPrevista.setText(sdf.format(data));
                root.clearFocus();
            }
        };
        listenerSelecaoCanceladaDataPrevista = new DatePickerDialog.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                root.clearFocus();
            }
        };
        dialogDataPrevista = new DatePickerDialog(CadastroProjetoActivity.this, listenerDataSelecionadaDataPrevista, dataAtual
                .get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH));
        dialogDataPrevista.setOnCancelListener(listenerSelecaoCanceladaDataPrevista);
    }

    public void cadastrar(View v) {

        if (txtNome.getText().toString() == "" || txtNome.getText().toString() == null) {
            Toast.makeText(this, "O Projeto deve ter um nome", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDescricao.getText().toString() == "" || txtDescricao.getText().toString() == null) {
            Toast.makeText(this, "O Projeto deve ter um descrição", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtDataPrevista.getText().toString() == "" || txtDataPrevista.getText().toString() == null) {
            Toast.makeText(this, "O Projeto deve ter uma data de previsão final", Toast.LENGTH_SHORT).show();
            return;
        }

        Projeto novoProjeto = Factory.startProjeto();

        novoProjeto.setNome(txtNome.getText().toString());
        novoProjeto.setDescricao(txtDescricao.getText().toString());
        novoProjeto.setDataCriacao(Calendar.getInstance().getTime());

        try {
            novoProjeto.setDataPrevFinalizacao(new SimpleDateFormat("dd/MM/yyyy").parse(txtDataPrevista.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        novoProjeto.setIdUsuario(Usuario.getUsuarioLogado().getId());

        long cadastrouProjeto = projetoController.cadastrar(novoProjeto);

        if (cadastrouProjeto != 0) {
            Intent i = new Intent(CadastroProjetoActivity.this, PaginaInicialActivity.class);
            startActivity(i);
            CadastroProjetoActivity.this.finish();
        } else {
            Toast.makeText(this, "Projeto já existe", Toast.LENGTH_SHORT).show();
            return;
        }

        Perfil novoPerfil = Factory.startPerfil();

        novoPerfil.setCodProjeto(novoProjeto.getCodigo());
        novoPerfil.setDataInicio(novoProjeto.getDataCriacao());
        novoPerfil.setIdUsuario(Usuario.getUsuarioLogado().getId());
        novoPerfil.setCodProjeto(cadastrouProjeto);
        novoPerfil.setPerfil(nescaupower.br.com.keepsoft.Enum.Perfil.SCRUM_MASTER);

        boolean cadastrouPerfil = perfilController.cadastrar(novoPerfil);

        if (cadastrouPerfil) {
            Toast.makeText(getApplicationContext(), "Cadastrou: " + novoPerfil.getPerfil().toString(), Toast.LENGTH_SHORT);
            Intent i = new Intent(CadastroProjetoActivity.this, PaginaInicialActivity.class);
            startActivity(i);
            CadastroProjetoActivity.this.finish();
        } else {
            Toast.makeText(this, "Não foi possível criar Perfil", Toast.LENGTH_SHORT).show();
            return;
        }
    }

}
