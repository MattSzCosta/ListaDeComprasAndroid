package com.example.matt_.listadecompras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt_.listadecompras.DAO.ProdutoDAO;
import com.example.matt_.listadecompras.Model.Produto;

import java.util.List;

public class ListProdutoActivity extends AppCompatActivity {

    private ListView listaProdutos;
    private TextView textTotalPagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produto);

        listaProdutos = (ListView) findViewById(R.id.lista_prdutos);
        textTotalPagamento = (TextView) findViewById(R.id.total_pagamento);

        listaProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Produto produto = (Produto) listaProdutos.getItemAtPosition(position);
                Intent intentVaiProCadastro = new Intent(ListProdutoActivity.this, CadastroProdutoActivity.class);
                intentVaiProCadastro.putExtra("produto", produto);
                Intent intent = getIntent();
                intentVaiProCadastro.putExtra("idLista", intent.getSerializableExtra("idLista"));
                startActivity(intentVaiProCadastro);
            }
        });

        Button novoProtudo = (Button) findViewById(R.id.novo_produto);
        novoProtudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFormulario = new Intent(ListProdutoActivity.this, CadastroProdutoActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });

        registerForContextMenu(listaProdutos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Produto produto = (Produto) listaProdutos.getItemAtPosition(info.position);
                Toast.makeText(ListProdutoActivity.this, "Deletar o produto " + produto.getNome(), Toast.LENGTH_SHORT).show();

                ProdutoDAO dao = new ProdutoDAO(ListProdutoActivity.this);
                dao.deleta(produto);
                dao.close();

                carregaLista();
                return false;
            }
        });
    }

    private void carregaLista() {
        Intent intent = getIntent();
        ProdutoDAO dao = new ProdutoDAO(this);
        List<Produto> produtos = dao.getListItems(intent.getIntExtra("idLista", 0));
        dao.close();

        ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, produtos);
        listaProdutos.setAdapter(adapter);

        Double totalPreco = 0.0;
        for(Produto produto : produtos){
           totalPreco += produto.getTotalPreco();
        }

        textTotalPagamento.setText("Total a pagar: " + totalPreco);
    }
}
