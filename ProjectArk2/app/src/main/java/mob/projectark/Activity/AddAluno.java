package mob.projectark.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import mob.projectark.DAO.ConfigFirebase;
import mob.projectark.Entidades.Usuarios;
import mob.projectark.Helper.Preferencias;
import mob.projectark.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class AddAluno extends AppCompatActivity {

    private EditText NomePlayerCad;
    private EditText SobrenomePlayerCad;
    private EditText NomePJCad;
    private EditText TelCad;
    private EditText EmailCad;
    private EditText SenhaCad;
    private EditText SenhaConfirmCad;
    private RadioButton UsertypeStaff;
    private RadioButton UsertypeAluno;
    private FloatingActionButton fab;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aluno);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NomePlayerCad = (EditText)findViewById(R.id.NomePlayerCad);
        SobrenomePlayerCad = (EditText)findViewById(R.id.SobrenomePlayerCad);
        NomePJCad = (EditText)findViewById(R.id.NomePJCad);
        TelCad = (EditText)findViewById(R.id.TelCad);
        EmailCad = (EditText)findViewById(R.id.EmailCad);
        SenhaCad = (EditText)findViewById(R.id.SenhaCad);
        SenhaConfirmCad = (EditText)findViewById(R.id.SenhaConfirmCad);
        UsertypeAluno = (RadioButton) findViewById(R.id.UsertypeAluno);
        UsertypeStaff = (RadioButton)findViewById(R.id.UsertypeStaff);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                if (SenhaCad.getText().toString().equals(SenhaConfirmCad.getText().toString())) {
                    usuarios = new Usuarios();
                    usuarios.setNome(NomePlayerCad.getText().toString());
                    usuarios.setSobrenome(SobrenomePlayerCad.getText().toString());
                    usuarios.setNomePJCad(NomePJCad.getText().toString());
                    usuarios.setTelCad(TelCad.getText().toString());
                    usuarios.setEmail(EmailCad.getText().toString());
                    usuarios.setSenha(SenhaCad.getText().toString());
                    usuarios.setSenhaconfirm(SenhaConfirmCad.getText().toString());

                    cadastrarUsuario();
                    if (UsertypeAluno.isChecked()) {
                        usuarios.setUsertype("Aluno");
                    } else if (UsertypeStaff.isChecked()) {
                        usuarios.setUsertype("Staff");}
                    } else {
                        Toast.makeText(AddAluno.this, "As senhas não são correspondentes", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    private void cadastrarUsuario(){
            autenticacao = ConfigFirebase.getFirebaseAutenticacao();
            autenticacao.createUserWithEmailAndPassword(
                    usuarios.getEmail(),
                    usuarios.getSenha()
            ).addOnCompleteListener(AddAluno.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddAluno.this, "Aluno cadastrado com sucesso", Toast.LENGTH_LONG).show();

                        String identificadorUsuario = usuarios.getEmail();

                        FirebaseUser usuarioFirebase = task.getResult().getUser();
                        usuarios.setId(identificadorUsuario);
                        usuarios.salvar();

                        Preferencias Preferencias = new Preferencias((AddAluno.this));
                        Preferencias.salvarUsuarioPreferencias(identificadorUsuario, usuarios.getNome());

                        abrirJornal();
                    }else {
                        String erroExcecao = "";

                        try{
                            throw task.getException();
                        }catch (FirebaseAuthWeakPasswordException e){
                            erroExcecao = "Digite uma senha mais forte, contendo no mininmo 8 caracteres de letras e numeros";
                        }catch (FirebaseAuthInvalidCredentialsException e){
                            erroExcecao = "O e-mail digitado é inválido, digite um novo e-mail";
                        }catch (FirebaseAuthUserCollisionException e){
                            erroExcecao = "O usuário já está cadastrado";
                        }catch (Exception e){
                            erroExcecao = "Erro ao efetuar o cadastro";
                            e.printStackTrace();
                        }
                        Toast.makeText(AddAluno.this,"Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void abrirJornal() {
        Intent intent = new Intent(AddAluno.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
