package database;
import java.sql.*;

import javax.swing.JOptionPane;

import classes.Order;
import net.proteanit.sql.DbUtils;

public class DataBase {
	private static DataBase dataBase_instance=null;
	private DataBase(){};
	public static DataBase getInstance() {
		if(dataBase_instance==null)
			dataBase_instance=new DataBase();
		return dataBase_instance;
	}

	
	public static Connection connect() {
		//SQLite connection string
		String url = "jdbc:sqlite:src/database/CoronaDB.db";
		Connection conn=null;
		try {
			conn=DriverManager.getConnection(url);
		} catch (SQLException e) {
		System.out.println(e.getMessage());
	}
		return conn;	
}
	
		public boolean selectManager(String id,String password) {
			String sql ="SELECT * FROM Workers where id =" + id + " AND password =" + password + " AND Job= ?";
			try {
				Connection conn=this.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "Manager");
				ResultSet rs=pstmt.executeQuery();
				boolean isEmpty =!rs.isBeforeFirst();
				System.out.println(isEmpty);
				if(isEmpty) {
					rs.close();
					conn.close();
					return false;
				}
				else {
					rs.close();
					conn.close();
					return true;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			return false;
		
	}
		public boolean selectCashier(String id,String password) {
			String sql ="SELECT * FROM Workers where id =" + id + " AND password =" + password + " AND Job= ?";
			try {
				Connection conn=this.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "Cashier");
				ResultSet rs=pstmt.executeQuery();
				boolean isEmpty =!rs.isBeforeFirst();
				if(isEmpty) {
					rs.close();
					conn.close();
					return false;
				}
				else {
					rs.close();
					conn.close();
					return true;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			return false;
		}
		public boolean selectStorekeeper(String id,String password) {
			String sql ="SELECT * FROM Workers where id =" + id + " AND password =" + password + " AND Job= ?";
			try {
				Connection conn=this.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "Storekeeper");
				ResultSet rs=pstmt.executeQuery();
				boolean isEmpty =!rs.isBeforeFirst();
				if(isEmpty) {
					rs.close();
					conn.close();
					return false;
				}
				else {
					rs.close();
					conn.close();
					return true;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			return false;
		}
		
		public ResultSet getProfitAndLossList() {
			try {
				Connection connection=this.connect();
				String query="select * from ProfitLoss";
				PreparedStatement pst=connection.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				return rs;
				} catch (Exception e1) {
					e1.printStackTrace();
					return null;
				}
			}
		
		public ResultSet getSupllyList() {
			
			
			try {
				Connection connection=this.connect();
				String query="select * from Products";
				PreparedStatement pst=connection.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				return rs;
				
				} catch (Exception e1) {
					e1.printStackTrace();
					return null;
				}
		}
		

		public boolean addNewOrder(Order order) {
			try {
				Connection connection=this.connect();
				String query="insert into Orders (Status,ProductID,ProductName,Department,Quantity,SubDepartment,Price) values (?,?,?,?,?,?,?)";
				PreparedStatement pst=connection.prepareStatement(query);
				pst.setString(1, order.getStatus());
				pst.setString(2,order.getId());
				pst.setString(3, order.getName());
				pst.setString(4, order.getDepartment());
				pst.setString(5, order.getQuantity());
				pst.setString(6, order.getSubDepartment());
				pst.setString(7, order.getPrice());
				pst.execute();
			
				pst.close();
				return true;
			}
			catch (Exception e1) {
				e1.printStackTrace();
				return false;
			}
		}
		
		public Order findOrderById(String ProductId) {
			try {
				Connection connection=this.connect();
				String query="SELECT * FROM Orders WHERE ProductID = ?";
				PreparedStatement pst=connection.prepareStatement(query);
				pst.setString(1, ProductId);
				ResultSet rs = pst.executeQuery();
				
				String status = rs.getString(1);
				String id = rs.getString(2);
				String name = rs.getString(3);
				String department = rs.getString(4);
				String quantity = rs.getString(5);
				String subDepartment = rs.getString(6);
				String price = rs.getString(7);
				
				Order order = new Order(status, id, name, department, quantity, subDepartment, price);
				pst.close();
				return order;
				
				
		
			}
			catch (Exception e1) {
				e1.printStackTrace();
				return null;
			
			}
		}
}
