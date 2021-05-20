package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import it.polito.tdp.rivers.db.RiversDAO;

public class Model {

	private RiversDAO dao;
	Map<Integer,River> idMap;
	Simulator s;
	
	public Model() {
		dao=new RiversDAO();
		idMap=new HashMap<>();
		s=new Simulator();
	}
	
	public Collection<River> getAllFiumi() {
		dao.getAllRivers(idMap);
		return  idMap.values();
	}
	
	public void setFiume(River r) {
		dao.setFlow(r);
		dao.setMedia(r);
	}
	
	public void simula(double fmed,LocalDate start, LocalDate stop ,double k,List<Flow> lista) {
		s.run(fmed, start, stop, k, lista);
	}
	
	public int getGiorniNoMinimo() {
		return s.getGiorniNoMinimo();
	}
	
	public double mediaC() {
		return s.MediaC();
	}
}
