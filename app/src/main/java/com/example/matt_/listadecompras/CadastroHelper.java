package com.example.matt_.listadecompras;

import android.widget.EditText;

import com.example.matt_.listadecompras.Model.Produto;

public class CadastroHelper {

    private final EditText campoNome;
    private final EditText campoMarca;
    private final EditText campoTipoProduto;
    private final EditText campoPreco;
    private final EditText campoQuantidade;

    private Produto produto;

    public CadastroHelper(CadastroProdutoActivity activity){
        campoNome = (EditText) activity.findViewById(R.id.cadastro_nome);
        campoMarca = (EditText) activity.findViewById(R.id.cadastro_marca);
        campoTipoProduto = (EditText) activity.findViewById(R.id.cadastro_tipoProduto);
        campoPreco = (EditText) activity.findViewById(R.id.cadastro_preco);
        campoQuantidade = (EditText) activity.findViewById(R.id.cadastro_quantidade);
        produto = new Produto();
    }

    public Produto getProduto() {
        produto.setNome(campoNome.getText().toString());
        produto.setMarca(campoMarca.getText().toString());
        produto.setTipoProduto(campoTipoProduto.getText().toString());
        produto.setPreco(Double.valueOf(campoPreco.getText().toString()));
        produto.setQuantidade(Long.valueOf(campoQuantidade.getText().toString()));

        return produto;
    }

    public void preencheCadastro(Produto produto) {
        campoNome.setText(produto.getNome());
        campoMarca.setText(produto.getMarca());
        campoTipoProduto.setText(produto.getTipoProduto());
        campoPreco.setText(produto.getPreco().toString());
        campoQuantidade.setText(produto.getQuantidade().toString());
        this.produto = produto;
    }
}
