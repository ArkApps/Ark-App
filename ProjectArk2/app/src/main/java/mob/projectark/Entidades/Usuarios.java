package mob.projectark.Entidades;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import mob.projectark.DAO.ConfigFirebase;

public class Usuarios {

    private String id;
    private String nome;
    private String sobrenome;
    private String NomePJCad;
    private String TelCad;
    private String Email;
    private String Senha;
    private String Senhaconfirm;
    private String Usertype;

    public Usuarios() {
    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfigFirebase.getFirebase();
        referenciaFirebase.child("usuario").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude

    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id", getId());
        hashMapUsuario.put("nome", getNome());
        hashMapUsuario.put("sobrenome", getSobrenome());
        hashMapUsuario.put("NomePJ", getNomePJCad());
        hashMapUsuario.put("Tel", getTelCad());
        hashMapUsuario.put("Email", getEmail());
        hashMapUsuario.put("Senha", getSenha());
        hashMapUsuario.put("Usertype", getUsertype());
    }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getSobrenome() {
            return sobrenome;
        }

        public void setSobrenome(String sobrenome) {
            this.sobrenome = sobrenome;
        }

        public String getNomePJCad() {
            return NomePJCad;
        }

        public void setNomePJCad(String NomePJCad) {
            this.NomePJCad = NomePJCad;
        }

        public String getTelCad() {
            return TelCad;
        }

        public void setTelCad(String TelCad) {
            this.TelCad = TelCad;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getSenha() {
            return Senha;
        }

        public void setSenha(String Senha) {
            this.Senha = Senha;
        }

        public String getSenhaconfirm() {
            return Senhaconfirm;
        }

        public void setSenhaconfirm(String Senhaconfirm) {
            this.Senhaconfirm = Senhaconfirm;
        }

        public String getUsertype() {
            return Usertype;
        }

        public void setUsertype(String usertype) {
            Usertype = usertype;
        }
}
