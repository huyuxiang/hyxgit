package com.sitech.dss.orm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.EntityPersister;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.sitech.dss.orm.Pagination;
import com.sitech.dss.orm.PageDao;
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

	//@Test
	public void allClassMapping() throws Exception {
		Session session = sessionFactory.openSession();

		try {
			Map metadata = sessionFactory.getAllClassMetadata();
			for (Object o : metadata.values()) {
				EntityPersister persister = (EntityPersister) o;
				String className = persister.getEntityName();
				Query q = session.createQuery("from " + className + " c");
				q.iterate();
				logger.debug("ok: " + className);
			}
		} finally {
			session.close();
		}
	}
	
	@Test
	public void pageTest(){
		Pagination page = new Pagination(5);
		
		page =pageDao.query(page,"select * from SS_USER",( Object[])null);
		
		logger.info("cnt:"+page.getTotalCount()
				+" pageCnt"+page.getTotalPages()
				+" pageSize:"+page.getPageSize()
				 
				);
		
		List<Object[]> l = page.getResult();
		for (Object[] objects : l) {
			System.out.println(Arrays.toString(objects));
		}
		
		 
	}
	
	@Test
	public void pageTest2(){
		Pagination page = new Pagination(5);
		
		page =pageDao.query(page,"select * from SS_USER where email like ? and name like ?", 
				"%cn","%n");
		
		List<Object[]> l = page.getResult();
		for (Object[] objects : l) {
			System.out.println(Arrays.toString(objects));
		}		 
	}
	
	//@Test
	public void testQueryForMap(){
		Pagination page = new Pagination(5);
		
		page =pageDao.queryForMap(page,"select * from SS_USER where email like ? and name like ?", 
				"%cn","%n");
		
		List<Map> l = page.getResult();
		for (Map map : l) {
			System.out.println(map.get("EMAIL"));
		}	
		
		
		
		
		Map param=new HashMap();
		param.put("email", "%cn");
		param.put("name", "%n");
		
		page = new Pagination(5);
			
		page =pageDao.queryForMap(page,"select * from SS_USER where email like :email and name like :name", 
					param);
		
		l = page.getResult();
		for (Map map : l) {
			System.out.println(map.get("EMAIL"));
		}	
		
		
		
	} 
}
