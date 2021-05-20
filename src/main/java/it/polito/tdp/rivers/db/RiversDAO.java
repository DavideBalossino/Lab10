package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public void getAllRivers(Map<Integer,River> idMap) {
		
		String sql = "SELECT id, name FROM river";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				River r=new River(res.getInt("id"), res.getString("name"));
				if(!idMap.containsKey(r.getId()))
				idMap.put(r.getId(),r);
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

	}
	
	public void setFlow(River r){
		String sql="SELECT * "
				+ "FROM flow AS f "
				+ "WHERE f.river=? "
				+ "ORDER BY(DAY)";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet rs = st.executeQuery();
			List<Flow> result=new LinkedList<>();
			while(rs.next()) {
				result.add(new Flow(rs.getDate("day").toLocalDate(),rs.getDouble("flow"),r));
			}
			r.setFlows(result);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setMedia(River r) {
		String sql="SELECT AVG(f.flow) AS m "
				+ "FROM flow AS f "
				+ "WHERE f.river=?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet rs = st.executeQuery();
			if(rs.next())
				r.setFlowAvg(rs.getDouble("m"));
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
