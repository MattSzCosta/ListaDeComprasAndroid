package com.example.matt_.listadecompras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.matt_.listadecompras.DAO.ProdutoDAO;
import com.example.matt_.listadecompras.Model.ItensLista;
import com.example.matt_.listadecompras.Model.Produto;

public class CadastroProdutoActivity extends AppCompatActivity {

    private CadastroHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        helper = new CadastroHelper(this);

        Intent intent = getIntent();
        Produto produto = (Produto) intent.getSerializableExtra("produto");

        if(produto != null){
            helper.preencheCadastro(produto);
        }

        Button botaoSalvar = (Button) findViewById(R.id.botao_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!helper.validarCampo()){
                    return;
                }
                Produto produto = helper.getProduto();
                ProdutoDAO dao = new ProdutoDAO(CadastroProdutoActivity.this);

                if(produto.getId() != null){
                    dao.altera(produto);
                }else{
                    Long idProduct = dao.insere(produto);
                    ItensLista itensLista = new ItensLista();
                    itensLista.setIdProduto(idProduct);
                    Intent intent = getIntent();
                    itensLista.setIdLista(intent.getLongExtra("idLista", 0));
                    dao.insertProductAtList(itensLista);
                }

                dao.close();
                Toast.makeText(CadastroProdutoActivity.this, "Produto " + produto.getNome() + " salvo!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Button botaoProdutosCadastrados = (Button) findViewById(R.id.botao_produtosCadastrados);
        botaoProdutosCadastrados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent produtosCadastrados = new Intent(CadastroProdutoActivity.this, ProdutosCadastrados.class);
                startActivity(produtosCadastrados);
            }
        });
    }
}
