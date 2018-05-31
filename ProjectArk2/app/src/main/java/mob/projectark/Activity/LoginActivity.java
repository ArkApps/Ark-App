package mob.projectark.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import mob.projectark.DAO.ConfigFirebase;
import mob.projectark.Entidades.Usuarios;
import mob.projectark.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Usuarios usuarios;
    private DatabaseReference referenciaFirebase;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEmailView.getText().toString().equals("") && !mPasswordView.getText().toString().equals("")) {

                    usuarios = new Usuarios();
                    usuarios.setEmail((mEmailView.getText().toString()));
                    usuarios.setSenha(mPasswordView.getText().toString());

                    validarLogin();
                }
            }
        });
    }

    private void validarLogin() {
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    abrirTelaPrincipal();
                    //Toast.makeText(LoginActivity.this, "Login efetuado com sucesso. Seja bem vindo ao Ark", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "E-mail e/ou senha incorreta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirTelaPrincipal() {

        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        referenciaFirebase = ConfigFirebase.getFirebase();
        referenciaFirebase.child("users").child(UID).child("IsAdmin").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String IsAdmin = dataSnapshot.getValue(String.class);
                        if (IsAdmin.equals("true")){
                            Intent intentAbrirTelaPrincipalAdmin = new Intent(LoginActivity.this, MainAdminActivity.class);
                            startActivity(intentAbrirTelaPrincipalAdmin);
                        } else {
                            Intent intentAbrirTelaPrincipalAluno = new Intent(LoginActivity.this, MainAlunoActivity.class);
                            startActivity(intentAbrirTelaPrincipalAluno);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }
        );




    }
}