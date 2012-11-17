package com.sitech.dss.orm;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import com.sitech.dss.orm.a.Org;
import com.sitech.dss.orm.b.Emp;
import com.sitech.dss.test.SpringTxTestCase;

 
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class KeyGeneratorTest extends SpringTxTestCase {
	private static Logger logger = LoggerFactory.getLogger(KeyGeneratorTest.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	 
	private EntityDao<Emp, Integer> empDao;
	

	@Before
	public void loadDefaultData() throws Exception {
		empDao =new EntityDao(sessionFactory,Emp.class );
		
		//pageDao = new PageDao(sessionFactory);
		 
	}

	@AfterClass
	public static void cleanDefaultData() throws Exception {
		 
	}
 
 
	public void displayGenerator(){

		List<Map> list=empDao.queryForMap("select * from hibernate_sequences");
		 
		 for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map map = (Map) iterator.next();
			System.out.println(map);
		}
	}
 
	
	@Test
	@Rollback(false)
	public void testQueryForMap(){
		
		//displayGenerator();
	 

	 
	 
	 for(int i=0;i<21;i++){
		 Emp e=new Emp("a"+i);
		 empDao.insert(e);
		 System.out.println("¡¾id:¡¿"+e.getId());
		 displayGenerator();
	 }
		
		Org o =new Org("o");
		empDao.save(o);
		
		 displayGenerator();
		/*Emp e2 =new Emp("a");
		
		empDao.insert(e2);
		
		
		displayGenerator(); */
		
		/*Pagination page = new Pagination(5);
		
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
		*/
		
		
	} 
}
