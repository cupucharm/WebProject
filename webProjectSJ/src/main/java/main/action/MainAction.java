package main.action;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MainAction {
	public String main(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/MainPage.jsp";
	}
}
