package br.com.fatec.tcc.rotasegura.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.fatec.tcc.rotasegura.R;
import br.com.fatec.tcc.rotasegura.ui.LoginActivity;
import br.com.fatec.tcc.rotasegura.ui.Registrar;


public class Mensagens extends AsyncTask<String, Void, Boolean>{

    private Activity a;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("usuarios");

    public Mensagens(Activity a) {
        this.a = a;
    }

    public Toast toastMensagem(String msg, int x, int y, int duration, int imageDrawable) {

        LayoutInflater inflater = a.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) a.findViewById(R.id.toast_layout_root));
        ImageView imageToast = (ImageView) layout.findViewById(R.id.iconeToast);
        imageToast.setImageResource(imageDrawable);

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(msg);

        Toast toast = new Toast(a);
        toast.setGravity(Gravity.TOP, x, y);
        toast.setDuration(duration); //0 = short; 1 = long
        toast.setView(layout);

        return toast;
    }

    public AlertDialog alertDialogMensagemOK(String title, String msg, int iconDrawable) {

        AlertDialog.Builder alert = new AlertDialog.Builder(a);
        alert.setTitle(title);
        alert.setIcon(iconDrawable);

        alert.setMessage(msg);

        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                System.exit(0);

            }
        });
        AlertDialog alert2 = alert.create();
        alert2.show();

        return alert2;
    }

    public <T> AlertDialog alertDialogMensagemSIMeNAO(String title, String msg, final String uid) {

        AlertDialog.Builder alert = new AlertDialog.Builder(a);
        alert.setTitle(title);
        //alert.setIcon(R.drawable.ic_splash_preto);
        alert.setMessage(msg);
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(uid != null){
                    myRef.child(uid).removeValue();

                } else {
                    //TODO: LÓGICA DE ALTERAÇÃO DE PERCURSO
                }
            }
        });
        alert.setNegativeButton("Não", null);
        AlertDialog alert2 = alert.create();

        return alert2;
    }

    @Override
    protected Boolean doInBackground(String... params) {



        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
