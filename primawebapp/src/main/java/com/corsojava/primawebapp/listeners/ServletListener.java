package com.corsojava.primawebapp.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServletListener implements ServletContextListener {

	public ServletListener() {
		// TODO Auto-generated constructor stub
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent sce) {
		try {
			
//			ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationcontext.xml" });
//			((AbstractApplicationContext) context).registerShutdownHook();
//			sce.getServletContext().setAttribute("SPRING_CONTEXT", context);
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}

	}

}
