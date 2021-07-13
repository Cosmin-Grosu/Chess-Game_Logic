package chessgame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ContainerPieces {
	TableSettingsDao tableSettingsDao;
	List<TableContainer> tableContainer;

	public ContainerPieces(TableSettingsDao tableSettingsDao) {
		this.tableSettingsDao = tableSettingsDao;
		this.tableContainer = new ArrayList<>();
	}

	public List<TableContainer> readContainerFromDB() {
		return tableSettingsDao.getContainer();
	}

	public List<TableContainer> readContainerFromXML(String file)
			throws ParserConfigurationException, SAXException, IOException {

		var factory = DocumentBuilderFactory.newInstance();
		var builder = factory.newDocumentBuilder();
		var document = builder.parse(new File(file));
		document.getDocumentElement().normalize();
		NodeList nList = document.getElementsByTagName("piece");
		for (var i = 0; i < nList.getLength(); i++) {
			var node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				var eElement = (Element) node;
				tableContainer.add(new TableContainer(eElement.getElementsByTagName("type").item(0).getTextContent(),
						eElement.getElementsByTagName("number").item(0).getTextContent()));
			}
		}
		return tableContainer;
	}
}
