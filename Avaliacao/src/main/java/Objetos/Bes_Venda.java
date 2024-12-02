
package Objetos;

public class Bes_Venda 
{
    private int idvenda;
    private String data_venda;
    private double total_venda;
    private Bes_Cliente cliente;

    public int getIdvenda() {
        return idvenda;
    }

    public void setIdvenda(int idvenda) {
        this.idvenda = idvenda;
    }

    public String getData_venda() {
        return data_venda;
    }

    public void setData_venda(String data_venda) {
        this.data_venda = data_venda;
    }

    public double getTotal_venda() {
        return total_venda;
    }

    public void setTotal_venda(double total_venda) {
        this.total_venda = total_venda;
    }

    public Bes_Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Bes_Cliente cliente) {
        this.cliente = cliente;
    }
    
    
}
