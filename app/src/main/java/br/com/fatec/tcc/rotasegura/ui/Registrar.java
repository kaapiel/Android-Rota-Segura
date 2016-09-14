package br.com.fatec.tcc.rotasegura.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.fatec.tcc.rotasegura.R;
import br.com.fatec.tcc.rotasegura.model.Usuario;
import br.com.fatec.tcc.rotasegura.utils.Mensagens;

public class Registrar extends AppCompatActivity{

    private EditText editEmail, editSenha, editNome, editConfirmSenha;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "Registrar_Activity";
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("usuarios");
    private int usuarios = 0;
    private String alterarUsuarioIndex;
    private RelativeLayout loadingContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cadastro);

        loadingContent = (RelativeLayout) findViewById(R.id.loading_content);

        editNome = (EditText) findViewById(R.id.registrar_nome);
        editEmail = (EditText) findViewById(R.id.registrar_email);
        editSenha = (EditText) findViewById(R.id.registrar_senha);
        editConfirmSenha = (EditText) findViewById(R.id.registrar_confirmar_senha);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in: " + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        final Button botaoEnviar = (Button) findViewById(R.id.botao_registrar_enviar);
        Button botaoBuscar = (Button) findViewById(R.id.botao_editar_buscar);
        final Button botaoExcluir = (Button) findViewById(R.id.botao_editar_excluir);
        botaoExcluir.setEnabled(false);

        botaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(Registrar.this);
                alert.setTitle("Exclusão de usuário");
                //alert.setIcon(R.drawable.ic_splash_preto);
                alert.setMessage("Você tem certeza que deseja excluir o usuário "+editNome.getText()+" ?");
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(mAuth.getCurrentUser().getUid() != null){
                            myRef.child(mAuth.getCurrentUser().getUid()).removeValue();
                            startActivity(new Intent(Registrar.this, LoginActivity.class));
                            finish();

                        } else {
                            //TODO: LÓGICA DE ALTERAÇÃO DE PERCURSO
                        }
                    }
                });
                alert.setNegativeButton("Não", null);
                AlertDialog alert2 = alert.create();
                alert2.show();
            }
        });

        assert botaoBuscar != null;
        botaoBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingContent.setVisibility(View.VISIBLE);
                Query query = null;
                try{
                    query = myRef.orderByChild("email").equalTo(editEmail.getText().toString());
                }catch (NullPointerException npe){
                    return;
                }

                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        alterarUsuarioIndex = dataSnapshot.getKey();
                        Usuario user = dataSnapshot.getValue(Usuario.class);

                        if(user == null){

                            new Mensagens(Registrar.this).toastMensagem("Email não encontrado!",
                                    0, 0, 1, R.drawable.com_facebook_button_like_icon_selected).show();
                            loadingContent.setVisibility(View.GONE);
                            return;
                        }

                        editEmail.setText(user.getEmail());
                        editSenha.setText(user.getSenha());
                        editNome.setText(user.getNome());

                        botaoEnviar.setText("Atualizar cadastro");
                        botaoExcluir.setEnabled(true);

                        loadingContent.setVisibility(View.GONE);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        assert botaoEnviar != null;
        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingContent.setVisibility(View.VISIBLE);
                if(validations()){

                    mAuth.createUserWithEmailAndPassword(editEmail.getText().toString(), editSenha.getText().toString())
                            .addOnCompleteListener(Registrar.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) { //ATUALIZAR

                                if(task.getException().getMessage().contains("address is badly")){
                                    new Mensagens(Registrar.this).toastMensagem(task.getException().getMessage(), 0, 0, 0, R.drawable.com_facebook_button_like_icon_selected).show();
                                    loadingContent.setVisibility(View.GONE);
                                    return;
                                }

                                myRef.child(String.valueOf(alterarUsuarioIndex)).child("nome").setValue(editNome.getText().toString());
                                myRef.child(String.valueOf(alterarUsuarioIndex)).child("email").setValue(editEmail.getText().toString());
                                myRef.child(String.valueOf(alterarUsuarioIndex)).child("senha").setValue(editSenha.getText().toString());

                                loadingContent.setVisibility(View.GONE);

                                new Mensagens(Registrar.this).toastMensagem("Usuario Atualizado!", 0, 0, 0, R.drawable.com_facebook_button_like_icon_selected).show();
                                Intent i = new Intent(Registrar.this, LoginActivity.class);
                                startActivity(i);
                                finish();

                            } else { //CADASTRAR

                                myRef.child(String.valueOf(usuarios+1)).child("nome").setValue(editNome.getText().toString());
                                myRef.child(String.valueOf(usuarios+1)).child("email").setValue(editEmail.getText().toString());
                                myRef.child(String.valueOf(usuarios+1)).child("senha").setValue(editSenha.getText().toString());

                                loadingContent.setVisibility(View.GONE);

                                new Mensagens(Registrar.this).toastMensagem("Usuario cadastrado!", 0, 0, 0, R.drawable.com_facebook_button_like_icon_selected).show();
                                Intent i = new Intent(Registrar.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }

                        }
                    });
                }
            }
        });

    }



    private boolean validations() {

        if(!editEmail.getText().toString().contains("@")){
            new Mensagens(this).toastMensagem("Email inválido", 0, 0, 0, R.drawable.com_facebook_button_like_icon_selected).show();
            return false;
        }

        String s1 = editSenha.getText().toString();
        String s2 = editConfirmSenha.getText().toString();

        if(s1.length() < 6){
            new Mensagens(this).toastMensagem("Senha inválida (Minimo 6 chars)", 0, 0, 0, R.drawable.com_facebook_button_like_icon_selected).show();
            return false;
        }

        if(editEmail.getText().toString().equals(null) | editEmail.getText().toString().equals("") |
                editEmail.getText().toString() == "" | editEmail.getText().toString() == null |
                editNome.getText().toString().equals(null) | editNome.getText().toString().equals("") |
                editNome.getText().toString() == "" | editNome.getText().toString() == null |
                editConfirmSenha.getText().toString().equals(null) | editConfirmSenha.getText().toString().equals("") |
                editConfirmSenha.getText().toString() == "" | editConfirmSenha.getText().toString() == null |
                editSenha.getText().toString().equals(null) | editSenha.getText().toString().equals("") |
                editSenha.getText().toString() == "" | editSenha.getText().toString() == null){

            new Mensagens(this).toastMensagem("Não podem haver campos vazios.", 0, 0, 1, R.drawable.com_facebook_button_like_icon_selected).show();
            return false;
        }

          if(!s1.equals(s2)){
              Log.e("SENHA 1", s1);
              Log.e("SENHA 2", s2);
              new Mensagens(this).toastMensagem("As senhas não conferem.", 0, 0, 1, R.drawable.com_facebook_button_like_icon_selected).show();
              return false;
          }

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        mAuth.signInAnonymously()
                .addOnCompleteListener(Registrar.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(Registrar.this, "Falha na Autenticação: "+task.getException().getMessage().toString(),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("LOG-IN ", "LOGGED AS ANONYMOUS");
                        }
                    }
                });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot users: dataSnapshot.getChildren()) {
                    usuarios++;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
