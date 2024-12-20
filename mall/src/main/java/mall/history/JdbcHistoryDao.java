package mall.history;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mall.common.DataSource;
import mall.younghun.Buyer;

public class JdbcHistoryDao implements HistoryDao {

	@Override
	public void insert(History history) {


	    try (Connection connection = DataSource.getDataSource();
	         PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO HISTORY (BUYER_NUMBER) VALUES (?)")) {

	        // Set the parameters for the insert statement
	        preparedStatement.setInt(1, history.getBuyer().getBuyer_number());

	        // Execute the insert statement
	        preparedStatement.executeUpdate();

	        // Retrieve the generated keys
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void deleteByHistoryNumber(int history_number) {
		// TODO Auto-generated method stub
		try(Connection connection = DataSource.getDataSource()){
			
			PreparedStatement preparedStatement = connection.prepareStatement("DROP FROM HISTORY WHERE HISTORY_NUMBER = ?");
			preparedStatement.setInt(1, history_number);
			preparedStatement.executeQuery();
			
		}catch(SQLException e) {
		e.printStackTrace();
		}
	}
	

	
	
	@Override
	public void deleteByBuyerNumber(int buyer_number) {
		// TODO Auto-generated method stub
		try(Connection connection = DataSource.getDataSource()){
			
			PreparedStatement preparedStatement = connection.prepareStatement("DROP FROM HISTORY WHERE BUYER_NUMBER = ?");
			preparedStatement.setInt(1, buyer_number);
			preparedStatement.executeQuery();
			
		}catch(SQLException e) {
		e.printStackTrace();
		}
		
	}
	
	@Override
	public List<History> findByBuyerNumber(int buyer_number) {
		// TODO Auto-generated method stub
		List<History> buyer_history = new ArrayList<>();
		try(Connection connection = DataSource.getDataSource()) {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM HISTORY WHERE BUYER_NUMBER = ?");
			preparedStatement.setInt(1, buyer_number);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				History history = new History();
				history.setHistory_number(rs.getInt("HISTORY_NUMBER"));
				Buyer buyer = new Buyer();
				buyer.setBuyer_number(buyer_number);
				history.setBuyer(buyer);
				buyer_history.add(history);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return buyer_history;
	}

	@Override
	public int findLatestHistory() {
		// TODO Auto-generated method stub
		int latest_key = -1;
		try(Connection connection = DataSource.getDataSource()){
			
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM HISTORY ORDER BY HISTORY_NUMBER DESC FETCH FIRST 1 ROW ONLY");
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				latest_key = rs.getInt("HISTORY_NUMBER");
			}
			
			
		}catch(SQLException e) {
		e.printStackTrace();
		}
		return latest_key;
	}


}
