package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Simulator {
	
	//Parametri in ingresso
	private double Q;
	private LocalDate start;
	private LocalDate stop;
	private double fmin;
	private double felevato;
	private List<Flow> listaFlow;
	
	//stato del mondo
	private double C;
	private double fin;
	private double fout;
	
	//output
	private int noMinimo;
	private double Cmed;
	private List<Double> valoriC;
	
	public void run(double fmed, LocalDate start, LocalDate stop, double k, List<Flow> lista) {
		Q=k*fmed*30*3600*24;
		C=Q/2;
		fmin=0.8*fmed*3600*24;
		felevato=10*fmin;
		noMinimo=0;
		Cmed=0;
		this.start=start;
		this.stop=stop;
		listaFlow=lista;
		LocalDate giornoCorrente=start;
		int cont=0;
		valoriC=new ArrayList<>();
	//	System.out.println(""+start);
	//	System.out.println(""+stop);
//		System.out.println(""+giornoCorrente);
	//	System.out.println(""+giornoCorrente.plusDays(1));
		
		while(giornoCorrente.isBefore(stop)) {

			fin= listaFlow.get(cont).getFlow()*3600*24;
			double random=Math.random()*100;
			if(random<=5) {
				fout=10*fmin;
			}
			else
				fout=fmin;
			
			double differenziale=fin-fout;
			//double result= C+fin-fout;
			C=C+fin-fout;
			
			if(C>Q) {
				C=Q;
			}
			
			if(C<0) {
				noMinimo++;
				C=0;
			}
			
			
			giornoCorrente=giornoCorrente.plusDays(1);
			cont++;
			System.out.println(""+C);
			valoriC.add(C);
			
			
		}
		
	}
	
	public Integer getGiorniNoMinimo() {
		return noMinimo;
	}
	
	public double MediaC() {
		double tot=0;
		for(Double d:valoriC) {
			tot=tot+d;
		}
		return tot/valoriC.size();
	}

}
