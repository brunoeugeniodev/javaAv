package Objetos;

public class Bes_Venda_Produto 
{
    private Bes_Venda idvenda;
    private Bes_Produto idproduto;
    private double quantidade;
    private double total_item;
    private double desconto_item;

    public Bes_Venda getIdvenda() {
        return idvenda;
    }

    public void setIdvenda(Bes_Venda idvenda) {
        this.idvenda = idvenda;
    }

    public Bes_Produto getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(Bes_Produto idproduto) {
        this.idproduto = idproduto;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getTotal_item() {
        return total_item;
    }

    public void setTotal_item(double total_item) {
        this.total_item = total_item;
    }

    public double getDesconto_item() {
        return desconto_item;
    }

    public void setDesconto_item(double desconto_item) {
        this.desconto_item = desconto_item;
    }
    
    
}
