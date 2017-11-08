package framework.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import framework.parsers.Bean;
import framework.parsers.PropertyBean;

public class XmlBeanFactory implements BeanFactory {

	HashMap<String, Object> beanTable = new HashMap<String, Object>();
	HashMap<String, Object> interceptorTable = new HashMap<String, Object>();

	XmlBeanFactory(String xmlFilePath, XmlBeanDefinitionReader xbdr) {
		xbdr.loadBeanDefinitions(xmlFilePath);
		generateBeans(xbdr.getBeanList());
		setupInterceptors(xbdr.getInterceptorList());
	}

	private Object createBean(Bean beanDefinition) {
		try {
			Object object = null;

			if (!beanDefinition.getProperties().isEmpty()) {
				Class<?> clazz = Class.forName(beanDefinition.getClassName());
				Constructor<?> ctor = clazz.getConstructor();
				object = ctor.newInstance();

				for (PropertyBean propertyBean : beanDefinition.getProperties()) {

					char first = Character.toUpperCase(propertyBean.getName().charAt(0));
					String methodName = "set" + first + propertyBean.getName().substring(1);

					Object createdBean;
					if (propertyBean.getBean().getName() != null) {
						// bean should exist in beantable
						createdBean = beanTable.get(propertyBean.getBean().getName());
					} else {
						// createBean
						createdBean = createBean(propertyBean.getBean());
					}

					Class<?> propertyClazz = getClassObject(propertyBean.getBean().getClassName());
					Method method = object.getClass().getMethod(methodName, new Class[] { propertyClazz });
					method.invoke(object, createdBean);
				}
			} else if (!beanDefinition.getConstructorArg().isEmpty()) {
				Class<?> clazz = Class.forName(beanDefinition.getClassName());
				Class<?>[] consClasses = new Class[beanDefinition.getConstructorArg().size()];
				Object[] consArgs = new Object[beanDefinition.getConstructorArg().size()];

				for (int i = 0; i < beanDefinition.getConstructorArg().size(); i++) {
					Bean constructorBean = beanDefinition.getConstructorArg().get(i);
					consClasses[i] = getClassObject(constructorBean.getClassName());

					// real object that has the spec type
					Object createdBean;
					if (constructorBean.getName() != null) {
						// bean should exist in beantable
						createdBean = beanTable.get(constructorBean.getName());
					} else {
						System.out.println(constructorBean);
						// createBean
						createdBean = createBean(constructorBean);
					}
					consArgs[i] = createdBean;
				}

				Constructor<?> ctor = clazz.getConstructor(consClasses);
				object = ctor.newInstance(consArgs);

			} else if (beanDefinition.getValue() != null) {
				if (beanDefinition.getClassName() == null || beanDefinition.getClassName().contains("String")) {
					object = beanDefinition.getValue();
				} else {
					Class<?> primitiveClass = getPrimitiveClassForName(beanDefinition.getClassName());
					object = getWrapperClassValueForPrimitiveType(primitiveClass, beanDefinition.getValue());
				}
			} else {
				Class<?> clazz = Class.forName(beanDefinition.getClassName());
				Constructor<?> ctor = clazz.getConstructor();
				object = ctor.newInstance();
			}

			if (beanDefinition.getName() != null) {
				beanTable.put(beanDefinition.getName(), object);
			}
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private Class<?> getClassObject(String name) {
		if (name == null || name.contentEquals("String")) {
			return String.class;
		} else if (classLibrary.containsKey(name)) {
			return getPrimitiveClassForName(name);
		} else {
			try {
				return Class.forName(name);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	private void generateBeans(List<Bean> beanList) {
		for (Bean b : beanList) {
			createBean(b);
		}
	}

	public Object getBean(String string) {
		return beanTable.get(string);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String string, Class<T> type) {
		return (T) beanTable.get(string);
	}

	public Object[] getInterceptors() {
		return (Object[]) interceptorTable.values().toArray();
	}

	private void setupInterceptors(List<Bean> interceptorList) {
		for (Bean b : interceptorList) {
			try {
				final Class<?> clazz = Class.forName(b.getClassName());
				Object interceptor = clazz.getConstructor().newInstance();
				interceptorTable.put(b.getName(), interceptor);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
