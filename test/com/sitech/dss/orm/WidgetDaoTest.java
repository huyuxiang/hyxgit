package com.sitech.dss.orm;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.sitech.dss.portal.model.Page;
import com.sitech.dss.portal.model.Template;
import com.sitech.dss.portal.model.Widget;
import com.sitech.dss.portal.model.WidgetInstance;
import com.sitech.dss.test.DbUnitUtils;
import com.sitech.dss.test.SpringTxTestCase;

@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class WidgetDaoTest extends SpringTxTestCase {
	private static Logger logger = LoggerFactory.getLogger(WidgetDaoTest.class);
	private static DataSource dataSourceHolder = null;

	@Autowired
	private SessionFactory sessionFactory;

	private EntityDao<Widget, Integer> widgetDao;
	private EntityDao<Template, String> templateDao;
	private EntityDao<WidgetInstance, String> widgetInstanceDao;
	private EntityDao<Page, String> pageDao;

	@Before
	public void loadDefaultData() throws Exception {
		widgetDao = new EntityDao(sessionFactory, Widget.class);
		pageDao = new EntityDao(sessionFactory,Page.class);
		templateDao = new EntityDao(sessionFactory, Template.class);
		widgetInstanceDao = new EntityDao(sessionFactory,WidgetInstance.class);
	 
		
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
	public void save() throws Exception {
		
		
		Widget widget = new Widget();
		widget.setName("test");
		widget.setUrl("/test/123");
		widgetDao.save(widget);
		
		Widget saved = widgetDao.get(widget.getId());
		
		System.out.println(saved.getName());
		System.out.println(saved.getUrl());
		System.out.println(saved.getIframe());
		System.out.println("same ? "+(widget==saved));
		
		
		for(Widget i:widgetDao.getAll()){
			System.out.println(i.getUrl());
		}
		
		
		Template tpl = new Template();
		tpl.setName("左右布局");
		
		templateDao.save(tpl);
	
		Page page = new Page();
		page.setTitle("首页");
		page.setTemplate(tpl);
		
		pageDao.save(page);

		
		WidgetInstance wi =new WidgetInstance();
		wi.setWidget(widget);
		wi.setPosition("col-2");
		page.addWidget(wi);
		
		
		widgetInstanceDao.save(wi);
		
		
		System.out.println(wi.getPage().getTitle());
		
		System.out.println(page.getWidgets().size());
		
		
		
	}

}
