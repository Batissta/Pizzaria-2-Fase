package model;

import static model.Uteis.leInt;
import static model.Uteis.leString;
import static model.Uteis.mostrarLinha;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Pizzaria {
	private ArrayList<Pedido> pedidos = new ArrayList<>();
	private List<Pizza<String>> pizzas = new ArrayList<>();
	private HashMap<String, Integer> ingredientes = new HashMap<>();
	private int ingredientesCorretos, mediaIngredientesCorretos;
	private int pizzasServidas = 0;
    private int mediaIngredientes;
	
	public void menu(){
		//Ingredientes
		ingredientes.put("calabresa", 0);
		ingredientes.put("ovos", 0);
		ingredientes.put("cebola", 0);
		ingredientes.put("azeitona", 0);
		ingredientes.put("muçarela", 0);
		ingredientes.put("presunto", 0);
		ingredientes.put("bacon", 0);
		ingredientes.put("frango", 0);
		ingredientes.put("cheddar", 0);
		ingredientes.put("catupiry", 0);
		ingredientes.put("gorgonzola", 0);
		ingredientes.put("parmesão", 0);
		ingredientes.put("nutella", 0);
		
        int escolha = 0;
        System.out.println("Seja bem-vindo(a) ao melhor sistema de pizzaria do universo!");
        while (escolha!=6){
            mostrarLinha();
            escolha = leInt("""
1) Receber um pedido
2) Olhar pedido atual
3) Preparar uma pizza
4) Servir pedido
5) Estatísticas dos pedidos
6) Sair do programa
O que iremos fazer agora?\s""");
            mostrarLinha();
            switch (escolha) {
			case 1 -> {
				Pizza<String> pizzaAleatoria = embaralhaIngredientes();
				atualizaEstatisticas(pizzaAleatoria);
				String nomeCliente = Uteis.leString("Digite o nome do cliente: ");
				System.out.println("Novo pedido escolhido por "+ nomeCliente +". -> " + pizzaAleatoria);
				pedidos.add(new Pedido(pizzaAleatoria, nomeCliente));
			}
			case 2 -> {
				if(pedidos.isEmpty()) System.out.println("Não há nenhum pedido na fila!");
				else System.out.println(pedidos.get(0));
			}
			case 3 -> {
				Pizza<String> novaPizza = new Pizza<>();
                String confirmacao;
                while (novaPizza.size() < 5) {
                    if (!novaPizza.isEmpty()) {
                        confirmacao = leString("Deseja remover o último item escolhido? ");
                        if (confirmacao.toLowerCase().startsWith("s"))
                            System.out.println(novaPizza.pop() + " foi excluído com sucesso!");
                        Uteis.mostrarLinha();
                    }
                    
                    listaIngredientes();
                    confirmacao = leString("Sua escolha: ").toLowerCase();
                    if(ingredientes.containsKey(confirmacao)){
                        novaPizza.push(confirmacao);
                        System.out.println(confirmacao + " foi adicionado à pizza com sucesso!");
                    } else System.out.println("Opção inválida!");
                    
                }
                this.criarPizza(novaPizza);
            }
			case 4 -> {
				if(pizzas.isEmpty() || pedidos.isEmpty()) System.out.println("Não há nenhum pizza preparada!");
				else servirPizza();
			}
			case 5 -> {
				if(this.getPizzasServidas()>0) {
					this.atualizaMedia();
					this.mostraEstatisticas();
				} else {
					System.out.println("Nenhuma pizza foi servida!");
				}
			}
			case 6 -> {
				System.out.println("Encerrando o sistema...");
			}
			default ->{
				System.out.println("Opção inválida!");
	            mostrarLinha();
			}
			}
        }
	}
	public Pizza<String> embaralhaIngredientes(){
		ArrayList<String> arrayList = new ArrayList<>();
		for (String s : ingredientes.keySet()) {
			arrayList.add(s);
		}
		Collections.shuffle(arrayList);
		Pizza<String> pizza = new Pizza<>();
		for (int i = 0; i < 5; i++) {
			pizza.push(arrayList.get(i));
		}
		return pizza;
	}
	
	public void criarPizza(Pizza<String> pizza){
	    pizzas.add(pizza);
	    System.out.println("Pizza criada: "+ pizzas.get(pizzas.size()-1));
	}
	
	public void atualizaMedia() {
		 this.mediaIngredientes = (this.ingredientesCorretos / (this.getPizzasServidas()+this.pedidos.size()));
	}
	
	public void atualizaEstatisticas(Pizza<String> pizza){
	       if(getPizzasServidas()>0) {
	    	   this.atualizaMedia();
	       }
	       for(String p : pizza.toList()) {
	    	   if(ingredientes.containsKey(p)) ingredientes.put(p, ingredientes.get(p)+1);
	       }
	   }
	 
	public void servirPizza() {
		List<String> daVez = pedidos.get(0).getPedido().toList();
		ArrayList<String> maisSemelhante = new ArrayList<>();
		Pizza<String> pizzaMaisSemelhante = null;
		for (Pizza<String> p : pizzas) {
			ArrayList<String> emComum = new ArrayList<>();
			Pizza<String> pizza = p;
			List<String> pizzaToList = pizza.toList();
			for (String str : daVez) {
				if(pizzaToList.contains(str)) {
					emComum.add(str);
					this.ingredientesCorretos++;	
					}
				}
			
			if(emComum.size() >= 3 && emComum.size()>maisSemelhante.size()) {
				maisSemelhante = emComum;
				pizzaMaisSemelhante = pizza;	
				}
			}
		
		if(pizzaMaisSemelhante!=null) {
			System.out.println("Pizza solicitada: " + pedidos.get(0));
			System.out.println("Pizza servida: "+ pizzas.get(pizzas.size()-1));
			pizzas.remove(pizzaMaisSemelhante);
			pedidos.remove(0);
			this.pizzasServidas++;
			
		} else {
			System.out.println("Nenhuma pizza tem pelo menos 3 dos ingredientes pedidos...\nPreze pela satisfação do cliente!");
		}
	}
	
	
	public void mostraEstatisticas(){
        String maisVisado = getMaisEscolhido();
        System.out.println("Pizzas servidas: " + this.getPizzasServidas());
        System.out.printf("O ingrediente favorito é o(a) %s, foi pedido %d vezes.\n", maisVisado, ingredientes.get(maisVisado));
       
        if(!getNaoEscolhidos().isEmpty()) System.out.println("Ingredientes que ainda não foram pedidos: " + getNaoEscolhidos());
        else System.out.println("Todos os ingredientes foram escolhidos pelo menos uma vez.");
        
        if(pedidos.size()>0) System.out.printf("Temos %d pedidos na fila...\n", pedidos.size());
        else System.out.println("Não temos nenhum pedido na fila!");
        System.out.println("Média de ingredientes corretos pela cozinha: " + mediaIngredientes);
    }


    public String getMaisEscolhido() {
        if(ingredientes.isEmpty())
            return "Nenhum ingrediente na cozinha";
        Integer maior = 0;
        String nomeDoMaior = "";
        for (String s:ingredientes.keySet()) {
            if(ingredientes.get(s) > maior){
                maior = ingredientes.get(s);
                nomeDoMaior = s;
            }
        }
        return nomeDoMaior;
    }
    
    public List<String> getNaoEscolhidos(){
        List<String> zeroVezes = new ArrayList<>();
        for (String s : ingredientes.keySet()) {
            if (ingredientes.get(s) == 0){
                zeroVezes.add(s);
            }
        }
        return zeroVezes;
    }
    
	public void listaIngredientes(){
        System.out.println("Ingredientes disponíveis: ");
        for (String s : ingredientes.keySet()) {
            System.out.printf("- %s\n", s);
        }
    }
	
	public int getPizzasServidas() {
		return pizzasServidas;
	}
	public static void main(String[] args) {
		Pizzaria pi = new Pizzaria();
		pi.menu();
	}
}
