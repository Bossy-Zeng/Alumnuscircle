/**
 * @author Zhengfan
 * @date 16.09.01
 * @version 1
 * 功能：解析高级筛选中专业的数据。
 */

package com.ac.alumnuscircle.main.ctc.hlyflt.parsedata;


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

public class ParseMajor {


    private static final String MAJOR_DATA_FILE = "major.xml";
    private static final String COLLEGE = "College";
    private static final String MAJOR = "Major";

    private Context context;
    private AssetManager assetManager;

    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;
    private Element root;

    private InputStream inputStream;

    public ParseMajor(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        try {
            assetManager = context.getAssets();
            inputStream = assetManager.open(MAJOR_DATA_FILE);
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(inputStream);
            root = document.getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> parseMajorLeftData(){
        List<String> result = new ArrayList<String>();
        NodeList collegeNodeList = root.getElementsByTagName(COLLEGE);
        for (int i = 0; i < collegeNodeList.getLength(); i++) {
            Element collegeNode = (Element) (collegeNodeList.item(i));
            if(collegeNode.getAttribute("Name") != null && collegeNode.getAttribute("Name").length() != 0){
                result.add(collegeNode.getAttribute("Name"));
            }
        }
        result.add(result.size() / 2, "所有学院");
        if (!result.get(result.size() / 2).equals("所有学院")) {
            int index = result.indexOf("所有学院");
            String temp = result.get(result.size() / 2);
            result.set(index, temp);
            result.set(result.size() / 2, "所有学院");
        }
        return result;
    }

    public List<String> parseMajorRightData(String left){
        List<String> result = new ArrayList<String>();
        if (left.equals("所有学院")) {
            result.add(result.size() / 2, "所有专业");
            return result;
        } else {
            NodeList collegeNodeList = root.getElementsByTagName(COLLEGE);
            for (int i = 0; i < collegeNodeList.getLength(); i++) {
                Element collegeNode = (Element) (collegeNodeList.item(i));
                if (collegeNode.getAttribute("Name").equals(left)) {
                    NodeList majorNodeList = collegeNode.getElementsByTagName(MAJOR);
                    for (int j = 0; j < majorNodeList.getLength(); j++) {
                        Element majorNode = (Element) (majorNodeList.item(j));
                        if (majorNode.getAttribute("Name") != null && majorNode.getAttribute("Name").length() != 0) {
                            result.add(majorNode.getAttribute("Name"));
                        }
                    }
                }
            }
            result.add(result.size() / 2, "所有专业");
            if (!result.get(result.size() / 2).equals("所有专业")) {
                int index = result.indexOf("所有专业");
                String temp = result.get(result.size() / 2);
                result.set(index, temp);
                result.set(result.size() / 2, "所有专业");
            }
            return result;
        }
    }

}
