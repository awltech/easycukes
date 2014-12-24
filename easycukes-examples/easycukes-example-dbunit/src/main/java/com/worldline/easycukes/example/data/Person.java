package com.worldline.easycukes.example.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {

	private String code;
	private String name;
	private String title;
	private String nationality;

	public Person() {

	}

	public Person(String code, String name, String title, String nationality) {
		this.code = code;
		this.name = name;
		this.title = title;
		this.nationality = nationality;
	}

	@XmlElement
	public String getName() {
		return this.name;
	}

	@XmlElement
	public String getCode() {
		return code;
	}

	@XmlElement
	public String getTitle() {
		return title;
	}

	@XmlElement
	public String getNationality() {
		return nationality;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Override
	public String toString() {
		return "[code = " + this.code + ", name = " + this.name + ", title = "
				+ title + ", nationality = " + this.nationality + "]";
	}

}
