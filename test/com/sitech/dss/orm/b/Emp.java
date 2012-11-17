
package com.sitech.dss.orm.b;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Emp {
	
	@Id
	@GeneratedValue(generator="default")
	private int id;
	private String name;
	
	
	public Emp(String string) {
		setName(string);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
