@org.hibernate.annotations.GenericGenerator(name="abc", strategy = "org.hibernate.id.enhanced.TableGenerator"
		 ,   parameters = {
	        @org.hibernate.annotations.Parameter(name="increment_size", value = "15")
	       ,@org.hibernate.annotations.Parameter(name="prefer_entity_table_as_segment_value", value = "true")
	   }
	)
	
package hiber.test;
