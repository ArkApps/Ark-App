package mob.projectark.Entidades;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import mob.projectark.DAO.ConfigFirebase;

public class Usuarios {

    private String id;
    private String nome;
    private String IdadePlayer;
    private String TelCad;
    private String Email;
    private String Senha;
    private String Senhaconfirm;
    private String NomePJCad;
    private String PJAge;
    private String Race;
    private String power;
    private String ArkCoins;
    private String Usertype;

    public Usuarios() {
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id", getId());
        hashMapUsuario.put("nome", getNome());;
        hashMapUsuario.put("NomePJ", getNomePJCad());
        hashMapUsuario.put("Tel", getTelCad());
        hashMapUsuario.put("Email", getEmail());
        hashMapUsuario.put("Senha", getSenha());
        hashMapUsuario.put("Usertype", getUsertype());
        hashMapUsuario.put("IdadePlayer", getIdadePlayer());
        hashMapUsuario.put("Raça", getRace());
        hashMapUsuario.put("Power", getPower());
        hashMapUsuario.put("ArkCoins", getArkCoins());
        hashMapUsuario.put("IdadePJ", getPJAge());

        return hashMapUsuario;
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

        public String getIdadePlayer() {
            return IdadePlayer;
        }

        public void setIdadePlayer(String idadePlayer) {
            IdadePlayer = idadePlayer;
        }

        public String getPJAge() {
            return PJAge;
        }

        public void setPJAge(String PJAge) {
            this.PJAge = PJAge;
        }

    public String getRace() {
            return Race;
        }

        public void setRace(String race) {
            Race = race;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getArkCoins() {
            return ArkCoins;
        }

        public void setArkCoins(String arkCoins) {
            ArkCoins = arkCoins;
        }
}
