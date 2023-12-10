package model;

public class Pedido {
	private static int pedidoAtual = 0;
	
	private int id;
    private Pizza<String> pedido;
    private String nomeCliente;
    
    public Pedido(Pizza<String> pizza, String nomeCliente){
    	pedidoAtual++;
    	this.setId(pedidoAtual);
        this.setPedido(pizza);
        this.setNomeCliente(nomeCliente);
    }
    
    @Override
    public String toString() {
    	return String.format("Pedido %d - %s -> %s", this.getId(), this.getNomeCliente(), this.getPedido());
    }
    
    public Pizza<String> getPedido() {
        return pedido;
    }

    public void setPedido(Pizza<String> pedido) {
        this.pedido = pedido;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public static int getPedidoAtual() {
		return pedidoAtual;
	}
 
}

