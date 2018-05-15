package com.appfifa.gabriel.appfifa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appfifa.gabriel.appfifa.dao.CampeonatoDao;
import com.appfifa.gabriel.appfifa.dao.DAO;
import com.appfifa.gabriel.appfifa.model.Campeonato;

public class FormularioCampeonatoActivity extends AppCompatActivity {

    private EditText nomeCampeonato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_campeonato);

        nomeCampeonato = (EditText) findViewById(R.id.form_nome_campeonato);
        Button btnSalvar;
        btnSalvar = (Button) findViewById(R.id.btn_criar_campeonato);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Campeonato campeonato = new Campeonato();
                campeonato.setNome(nomeCampeonato.getText().toString());
                campeonato.setStatus("Add jogadores");
                DAO dao = new DAO(FormularioCampeonatoActivity.this);
                CampeonatoDao campeonatoDao = new CampeonatoDao(dao);
                campeonatoDao.insere(campeonato);
                Toast.makeText(FormularioCampeonatoActivity.this, "Adicionem os players para começar", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
