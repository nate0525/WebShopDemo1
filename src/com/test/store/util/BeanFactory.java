package com.test.store.util;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.test.store.dao.UserDao;
import com.test.store.domain.User;

public class BeanFactory {

	public static Object createObject(String name) {

		try {
			SAXReader reader = new SAXReader();

			InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");

			Document doc;
			doc = reader.read(is);
			Element rootElement = doc.getRootElement();

			List<Element> list = rootElement.elements();

			for (Element ele : list) {
				String id = ele.attributeValue("id");
				if (id.equals(name)) {
					String str = ele.attributeValue("class");
					Class clazz = Class.forName(str);
					return clazz.newInstance();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public static void main(String[] args) {

		try {
			UserDao ud = (UserDao) BeanFactory.createObject("UserDao");
			User user = new User();
			user.setUsername("123");
			user.setPassword("123");
			User userLogin = ud.userLogin(user);
			System.out.println(userLogin);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
