package com.example.matt_.listadecompras;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.matt_.listadecompras.DAO.ProdutoDAO;
import com.example.matt_.listadecompras.Model.Lista;
import com.example.matt_.listadecompras.Model.Produto;

import java.util.List;

public class ProdutosCadastrados extends AppCompatActivity {

    private ListView listProdutos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos_cadastrados);
        listProdutos = (ListView) findViewById(R.id.lista_produtos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista(){
        ProdutoDAO dao = new ProdutoDAO(this);
        List<Produto> itens = dao.getProdutos();
        dao.close();

        ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, itens);
        listProdutos.setAdapter(adapter);
    }
}
