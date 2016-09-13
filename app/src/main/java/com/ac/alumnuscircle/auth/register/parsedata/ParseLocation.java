/**
 * @author 吴正凡
 * @date 16.09.01
 * @version 1
 * 功能：解析高级筛选中地区的数据。
 */

package com.ac.alumnuscircle.auth.register.parsedata;

import android.content.Context;
import android.content.res.AssetManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
/**
 * @author 吴正凡
 * @date 16.09.01
 * @version 1
 * 功能：解析高级筛选中地区的数据。
 */
public class ParseLocation {

    private static final String LOCATION_DATA_FILE = "location.xml";
    private static final String COUNTRY = "CountryRegion";
    private static final String STATE = "State";
    private static final String CITY = "City";

    private Context context;
    private AssetManager assetManager;

    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;
    private Element root;

    private InputStream inputStream;

    public ParseLocation(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        try {
            assetManager = context.getAssets();
            inputStream = assetManager.open(LOCATION_DATA_FILE);
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(inputStream);
            root = document.getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> parseLocationLeftData() {
        List<String> result = new ArrayList<String>();
        NodeList countryNodeList = root.getElementsByTagName(COUNTRY);
        for (int i = 0; i < countryNodeList.getLength(); i++) {
            Element countryNode = (Element) (countryNodeList.item(i));
            if(countryNode.getAttribute("Name") != null && countryNode.getAttribute("Name").length() != 0){
                result.add(countryNode.getAttribute("Name"));
            }
        }
        return result;
    }

    public List<String> parseLocationMiddleData(String left) {
        if(left == null || left.length() == 0){
            return null;
        }
        List<String> result = new ArrayList<String>();
        NodeList countryNodeList = root.getElementsByTagName(COUNTRY);
        for (int i = 0; i < countryNodeList.getLength(); i++) {
            Element countryNode = (Element) (countryNodeList.item(i));
            if (countryNode.getAttribute("Name").equals(left)) {
                NodeList stateNodeList = countryNode.getElementsByTagName(STATE);
                for (int j = 0; j < stateNodeList.getLength(); j++) {
                    Element stateNode = (Element) (stateNodeList.item(j));
                    if(stateNode.getAttribute("Name") != null && stateNode.getAttribute("Name").length() != 0){
                        result.add(stateNode.getAttribute("Name"));
                    }
                }
            }
        }
        return result;
    }

    public List<String> parseLocationRightData(String left, String middle) {
        List<String> result = new ArrayList<String>();
        if(left == null || left.length() == 0){
            return null;
        }
        if (middle == null || middle.length() == 0) {
            NodeList countryNodeList = root.getElementsByTagName(COUNTRY);
            for (int i = 0; i < countryNodeList.getLength(); i++) {
                Element countryNode = (Element) (countryNodeList.item(i));
                if (countryNode.getAttribute("Name").equals(left)) {
                    NodeList stateNodeList = countryNode.getElementsByTagName(STATE);
                    for (int j = 0; j < stateNodeList.getLength(); j++) {
                        Element stateNode = (Element) (stateNodeList.item(j));
                        NodeList cityNodeList = stateNode.getElementsByTagName(CITY);
                        for (int k = 0; k < cityNodeList.getLength(); k++) {
                            Element cityNode = (Element) (cityNodeList.item(k));
                            if(cityNode.getAttribute("Name") != null && cityNode.getAttribute("Name").length() != 0){
                                result.add(cityNode.getAttribute("Name"));
                            }
                        }
                    }
                }
            }
            return result;
        }else {
            NodeList countryNodeList = root.getElementsByTagName(COUNTRY);
            for (int i = 0; i < countryNodeList.getLength(); i++) {
                Element countryNode = (Element) (countryNodeList.item(i));
                if (countryNode.getAttribute("Name").equals(left)) {
                    NodeList stateNodeList = countryNode.getElementsByTagName(STATE);
                    for (int j = 0; j < stateNodeList.getLength(); j++) {
                        Element stateNode = (Element) (stateNodeList.item(j));
                        if (stateNode.getAttribute("Name").equals(middle)) {
                            NodeList cityNodeList = stateNode.getElementsByTagName(CITY);
                            for (int k = 0; k < cityNodeList.getLength(); k++) {
                                Element cityNode = (Element) (cityNodeList.item(k));
                                if(cityNode.getAttribute("Name") != null && cityNode.getAttribute("Name").length() != 0){
                                    result.add(cityNode.getAttribute("Name"));
                                }
                            }
                        }
                    }
                }
            }
            return result;
        }
    }


}
