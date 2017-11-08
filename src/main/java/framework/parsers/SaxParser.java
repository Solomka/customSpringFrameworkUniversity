package framework.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParser extends DefaultHandler implements Parser {

	String xmlFileName;

	List<Bean> beanList;
	List<Bean> interceptorList;

	String tmpValue;
	Bean beanTmp;

	public SaxParser(String xmlFileName) {
		this.xmlFileName = xmlFileName;

		beanList = new ArrayList<Bean>();
		interceptorList = new ArrayList<Bean>();

		parseDocument();
	}

	private void parseDocument() {
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			SAXParser parser = factory.newSAXParser();
			// sax parseer parses xml-document
			parser.parse(xmlFileName, this);

		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfig error");
		} catch (SAXException e) {
			System.out.println("SAXException : xml not well formed");
		} catch (IOException e) {
			System.out.println("IO error");
		}
	}

	public List<Bean> getBeanList() {
		return beanList;
	}

	public List<Bean> getInterceptorList() {
		return interceptorList;
	}

	@Override
	public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {

		if (elementName.equalsIgnoreCase("bean") || elementName.equalsIgnoreCase("interceptor")) {
			beanTmp = new Bean();
			beanTmp.setName(attributes.getValue("id"));
			beanTmp.setClassName(attributes.getValue("class"));
		}

		if (elementName.equalsIgnoreCase("constructor-arg")) {

			if (attributes.getValue("ref") != null) {
				for (int i = 0; i < beanList.size(); i++) {
					if (beanList.get(i).getName().equals(attributes.getValue("ref"))) {
						beanTmp.getConstructorArg().add(beanList.get(i));
					}
				}
			} else {
				// (type, value) => Bean
				Bean bean = new Bean();
				bean.setClassName(attributes.getValue("type"));
				bean.setValue(attributes.getValue("value"));
				beanTmp.getConstructorArg().add(bean);
			}
		}

		if (elementName.equalsIgnoreCase("property")) {
			String name = attributes.getValue("name");
			if (attributes.getValue("ref") != null) {
				String ref = attributes.getValue("ref");
				List<Bean> candidates= beanList.stream().filter(x -> x.getName().equals(ref)).collect(Collectors.toList());
				Bean bean = candidates.size() == 1 ? candidates.get(0) : null;
				beanTmp.getProperties().add(new PropertyBean(name, bean));
			}

			else {
				// (type, value) => Property
				Bean bean = new Bean();
				bean.setClassName(attributes.getValue("type"));
				bean.setValue(attributes.getValue("value"));
				PropertyBean property = new PropertyBean(name, bean);
				beanTmp.getProperties().add(property);
			}
		}
	}

	@Override
	public void endElement(String s, String s1, String element) throws SAXException {
		if (element.equals("bean")) {
			beanList.add(beanTmp);
		}

		if (element.equals("interceptor")) {
			interceptorList.add(beanTmp);
		}
	}

	@Override
	public void characters(char[] ac, int i, int j) throws SAXException {
		tmpValue = new String(ac, i, j);
	}

	public String toString() {
		String res = "";
		for (Bean tmpB : beanList) {
			res += tmpB.toString() + "; ";
		}

		return res;
	}

}
