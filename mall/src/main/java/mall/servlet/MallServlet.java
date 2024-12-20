package mall.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jy.servlet.JdbcProductDao;
import jy.servlet.Product;
import jy.servlet.ProductDao;
import mall.history.History;
import mall.history.HistoryDao;
import mall.history.JdbcHistoryDao;
import mall.younghun.Buyer;
import mall.younghun.BuyerDao;
import mall.younghun.JdbcBuyerDao;

@SuppressWarnings("serial") 
@WebServlet("/index") 
public class MallServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String uri = request.getRequestURI();
		int lastIndex = uri.lastIndexOf("/"); 
		String action = uri.substring(lastIndex+1); 
		
		if(action.equals("index")) {
			
		}else if(action.equals("productinfo")) {
			String productName = request.getParameter("productname");
			ProductDao productDao = new JdbcProductDao();
			Product product = productDao.findByName(productName);
			request.setAttribute("product", product);
		}
		String dispatcherUrl = null;
		
		if(action.equals("index")) {
			dispatcherUrl = "/index.jsp";
		}else if(action.equals("productinfo")) {
			dispatcherUrl = "/products/productinfo.jsp";
		}
	    if(dispatcherUrl != null) {
	    	RequestDispatcher rd = request.getRequestDispatcher(dispatcherUrl);
	    	rd.forward(request, response);
	    }
	}
	
}
