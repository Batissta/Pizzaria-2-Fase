package model;

import java.util.ArrayList;
import java.util.List;

public class Pizza<E>{
    private static final int tamanho = 5;
    private String nomePizza;
	@SuppressWarnings("unchecked")
	private E[] pilha = (E[]) new Object[tamanho];

    private int quantidade = 0;

    public void push(E e){
        pilha[quantidade] = e;
        this.quantidade++;
    }
    public E pop(){
        quantidade--;
        E temp = pilha[quantidade];
        pilha[quantidade] = null;
        return temp;
    }

    public E first(){
        return pilha[quantidade-1];
    }
    
    public String toString() {
    	String ingredientes = "Pizza com ";
    	for (int i = 0; i < 5; i++) {
			switch(i) {
				case 3 -> ingredientes += get(i) + " e ";
				case 4 -> ingredientes += get(i) + ".";
				default -> ingredientes += get(i) + ", ";
			}
		}
    	return ingredientes;
    }
    public boolean isEmpty(){
        return quantidade == 0;
    }
    public E get(int i) {
    	return pilha[i];
    }
    public int size(){
        return quantidade;
    }

    public String getNomePizza() {
        return nomePizza;
    }
    
    public List<E> toList(){
    	List<E> lista = new ArrayList<>();
    	for (E string : pilha) {
			lista.add(string);
		}
        return lista;
    }
    
    public void setNomePizza(String nomePizza) {
        this.nomePizza = nomePizza;
    }
}