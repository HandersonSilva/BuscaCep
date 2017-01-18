package com.cadanimal.handersonsilva.buscacep;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;




import com.cadanimal.handersonsilva.buscacep.RestClient.RetrofitCepService;
import com.cadanimal.handersonsilva.buscacep.Server.RetrofitServiceGenerator;

import com.cadanimal.handersonsilva.buscacep.entidade.Cep;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public Cep cep = new Cep();
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controleLayoutConteudoInvisivel();

        Button btnpesqui = (Button) findViewById(R.id.button_Pesquisar);
        btnpesqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnpesqui = (Button) findViewById(R.id.button_Pesquisar);
                EditText pegarCep = (EditText)findViewById(R.id.setCepedit);

                cep.setCep(pegarCep.getText().toString());
                setObJ(cep.getCep());
                pegarCep.setEnabled(false);
                btnpesqui.setEnabled(false);

                progress = new ProgressDialog(MainActivity.this);
                progress.setTitle("Procurando por: "+ cep.getCep());
                progress.show();

            }
        });

        Button btnNovaPesquisa =(Button)findViewById(R.id.button_newConsulta);
        btnNovaPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controleLayoutConteudoInvisivel();

                Button btnpesqui = (Button) findViewById(R.id.button_Pesquisar);
                EditText pegarCep = (EditText)findViewById(R.id.setCepedit);

                btnpesqui.setEnabled(true);
                pegarCep.setEnabled(true);
                pegarCep.requestFocus();
                pegarCep.setText("");
            }
        });
    }

    public void controleLayoutConteudo(){
        RelativeLayout conteudo = (RelativeLayout)findViewById(R.id.relativeLayoutConteudo);
        RelativeLayout pesquisa = (RelativeLayout)findViewById(R.id.relativolayoutPesquisa);

        pesquisa.setVisibility(View.VISIBLE);
        conteudo.setVisibility(View.VISIBLE);

    }
    public void controleLayoutConteudoInvisivel(){
        RelativeLayout conteudo = (RelativeLayout)findViewById(R.id.relativeLayoutConteudo);
        RelativeLayout pesquisa = (RelativeLayout)findViewById(R.id.relativolayoutPesquisa);

        pesquisa.setVisibility(View.INVISIBLE);
        conteudo.setVisibility(View.INVISIBLE);
    }



    public void setarValores(String localidade,String cepp,String uff,String codI){
        TextView cep = (TextView)findViewById(R.id.textView_setCep);
        TextView locali =(TextView)findViewById(R.id.textView_setLocalidade);
        TextView uf =(TextView)findViewById(R.id.textView_setUF);
        TextView codIbge =(TextView)findViewById(R.id.textView_setIBGE);

        cep.setText(cepp);
        locali.setText(localidade);
        uf.setText(uff);
        codIbge.setText(codI);
        controleLayoutConteudo();
    }


//função que trata a comunicação retrofit
   public void setObJ( String passeCep) {

        RetrofitCepService retroCep = RetrofitServiceGenerator.createService(RetrofitCepService.class);
        Call<Cep> call = retroCep.listar(passeCep);

        call.enqueue(new Callback<Cep>() {
            @Override
            public void onResponse(Call<Cep> call, Response<Cep> response) {
                if (response.isSuccessful()) {
                    Cep cepRespon = response.body();
                    cep.setCep(cepRespon.getCep());
                    cep.setLocalidade(cepRespon.getLocalidade());
                    cep.setUf(cepRespon.getUf());
                    cep.setIbge(cepRespon.getIbge());

                    progress.dismiss();

                    setarValores(cep.getLocalidade(),cep.getCep(),cep.getUf(),cep.getIbge());


                }

            }
            @Override
            public void onFailure(Call<Cep> call, Throwable t) {
                Log.e("AppCep", "Não foi possível recuperar o Cep", t);

            }
        });


    }

}
