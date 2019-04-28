package com.example.matt_.listadecompras.Model;

import java.io.Serializable;

public class Produto implements Serializable {

    private Long id;
    private String nome;
    private String marca;
    private String tipoProduto;
    private Long quantidade;
    private Double preco;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getTotalPreco(){
        return getQuantidade() * getPreco();
    }

    @Override
    public String toString() {
        return getNome() + " - " + getPreco() + " x "+ getQuantidade() + " = " + getTotalPreco();
    }
}
