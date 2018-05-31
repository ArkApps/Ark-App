package mob.projectark.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import mob.projectark.DAO.ConfigFirebase;
import mob.projectark.Entidades.Usuarios;
import mob.projectark.R;

public class PerfilAlunoActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Usuarios usuarios;
    private DatabaseReference referenciaFirebase;
    private TextView PlayerName;
    private TextView NomePJ;
    private TextView PJAge;
    private TextView Race;
    private TextView PJPower;
    private TextView dinheiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_aluno);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        PlayerName = (TextView) findViewById(R.id.PlayerName);
        NomePJ = (TextView) findViewById(R.id.NomePJ);
        PJAge = (TextView) findViewById(R.id.PJAge);
        Race = (TextView) findViewById(R.id.Race);
        PJPower = (TextView) findViewById(R.id.PJPower);
        dinheiro = (TextView) findViewById(R.id.ArkCoinsView);

        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        referenciaFirebase = ConfigFirebase.getFirebase();
        referenciaFirebase.child("users").child(UID).child("public").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        String nome = map.get("playername").toString();
                        String nomepj = map.get("pjname").toString();
                        String pjage = map.get("pjage").toString();
                        PlayerName.setText(nome);
                        NomePJ.setText(nomepj);
                        PJAge.setText(pjage);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }
        );
        referenciaFirebase.child("users").child(UID).child("optional").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Map<String, Object> map2 = (Map<String, Object>) dataSnapshot.getValue();
                        String power = map2.get("power").toString();
                        String racepj = map2.get("race").toString();
                        PJPower.setText(power);
                        Race.setText(racepj);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }
        );
        referenciaFirebase.child("users").child(UID).child("restrict").child("ArkCoins").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String ArkCoinsValue = dataSnapshot.getValue(String.class);
                        dinheiro.setText(ArkCoinsValue);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }
        );
    }
}
