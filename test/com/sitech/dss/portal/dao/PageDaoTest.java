package com.sitech.dss.portal.dao;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.sitech.dss.orm.PageDao;
import com.sitech.dss.portal.model.Page;
import com.sitech.dss.test.DbUnitUtils;
import com.sitech.dss.test.SpringTxTestCase;

 
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class PageDaoTest extends SpringTxTestCase {
	private static Logger logger = LoggerFactory.getLogger(PageDaoTest.class);
	private static DataSource dataSourceHolder = null;

	@Autowired
	private SessionFactory sessionFactory;
	
	 
	private PageDao pageDao;

	@Before
	public void loadDefaultData() throws Exception {
		pageDao =new PageDao(sessionFactory);
		if (dataSourceHolder == null) {
			DbUnitUtils.loadData(dataSource, "/test-data.xml");
			dataSourceHolder = dataSource;
		}
	}

	@AfterClass
	public static void cleanDefaultData() throws Exception {
		DbUnitUtils.removeData(dataSourceHolder, "/test-data.xml");
	}

 
 
	
	@Test
	public void testPageModelMapping(){
		
		List <Page> pages = pageDao.queryByHql("from Page");
		
		for (Page p : pages) {
			
			System.out.print("id:"+p.getId()+" title:"+p.getTitle()+" SUBPAGE: "  );
			
			for(Page sp: p.getSubPages()){
				System.out.print( sp.getId()+", ");
			}
			
			System.out.println();
			
		}
		
		
		
		 
		
		
		
	} 
}
