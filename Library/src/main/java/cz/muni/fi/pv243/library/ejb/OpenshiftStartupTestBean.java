package cz.muni.fi.pv243.library.ejb;

import java.sql.Connection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.sql.DataSource;

@Singleton
@Startup
public class OpenshiftStartupTestBean {
	
//	@Resource
//	private DataSource datasource;
//	
//	@PostConstruct
//	public void init() {
//		final boolean isDatasourceNull = this.datasource == null;
//		System.out.println("");
//		System.out.println("OpenshiftStartupTestBean.init()");
//		System.out.println("datasource je null: " + isDatasourceNull);
//		try {
//			Connection connection = this.datasource.getConnection();
//			System.out.println("aktivni spojeni: " + connection);
//			connection.close();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		
//	}

}
