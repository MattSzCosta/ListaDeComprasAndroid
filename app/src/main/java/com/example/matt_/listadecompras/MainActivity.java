package com.example.matt_.listadecompras;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.matt_.listadecompras.DAO.ProdutoDAO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nova Lista");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Criar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ProdutoDAO dao = new ProdutoDAO(MainActivity.this);
                Intent intentListaProdutos = new Intent(MainActivity.this, ListProdutoActivity.class);
                String nomeLista = input.getText().toString();
                Long idLista = dao.newLista(nomeLista);
                intentListaProdutos.putExtra("idLista", idLista);
                startActivity(intentListaProdutos);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        Button botaoNovaLista = (Button) findViewById(R.id.nova_lista);
        botaoNovaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.show();
            }
        });
    }
}
