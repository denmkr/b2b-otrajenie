package ru.dm.shop.modul;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.dm.shop.entity.GroupChild;
import ru.dm.shop.entity.GroupRelation;
import ru.dm.shop.entity.Product;
import ru.dm.shop.entity.ProductGroup;


import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Denis on 24.01.16.
 */
public class XMLParser {

    private HttpServletRequest request;

    public Iterator<GroupChild> iterator;
    public GroupChild group;

    public ArrayList<GroupRelation> relations;
    public ArrayList<Product> products;
    public ArrayList<ProductGroup> productGroups;

    public XMLParser(HttpServletRequest request) {

        this.request = request;
        relations = new ArrayList<GroupRelation>();

        parseProducts(); // Скачиваем товары с файла
        parseGroups(); // Скачиваем группы с файла

    }


    public void parseGroups() {

        try {

            ArrayList<GroupChild> list = new ArrayList<GroupChild>();

            String path = request.getServletContext().getRealPath("/WEB-INF/files"); // Путь к файлам XML

            File inputFile = new File(path + "/import.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Группа");

            for (int i=0; i<nList.getLength(); i++) {
                Node node = nList.item(i);
                // System.out.print(node.getChildNodes().item(3).getTextContent());
                GroupChild p = new GroupChild(node.getChildNodes().item(3).getTextContent(), node.getChildNodes().item(1).getTextContent());
                if (node.getChildNodes().item(5) != null) { // Есть подуровни
                    //System.out.print(node.getChildNodes().item(5).getChildNodes().getLength()/2);
                    p.setChildsCount(node.getChildNodes().item(5).getChildNodes().getLength()/2);
                }

                list.add(p);
            }

            productGroups = getGroupList(list); // Получаем список групп
            relations = getRelationsList(list); // Получаем отношения групп между собой

            for (int i=0; i<relations.size(); i++) {
                System.out.println(relations.get(i).getGroupId() + " " + relations.get(i).getChildGroupId());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void parseProducts() {

        products = new ArrayList<Product>();

        try {

            String path = request.getServletContext().getRealPath("/WEB-INF/files"); // Путь к файлам XML

            File inputFile = new File(path + "/import.xml");

            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();


            File inputFileOffers = new File(path + "/offers.xml");

            Document doc2 = dBuilder.parse(inputFileOffers);
            doc2.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Товар");
            NodeList nList2 = doc2.getElementsByTagName("Предложение");

            for (int i = 0; i < nList.getLength(); i++) { // Берем товары из import.xml
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String article;
                    if (eElement.getElementsByTagName("Артикул").item(0) != null ) {
                        article = eElement.getElementsByTagName("Артикул").item(0).getTextContent();
                        article = article.replaceAll("\\s+", "");
                    }
                    else {
                        article = " ";
                    }

                    String code;
                    if (eElement.getElementsByTagName("Код").item(0) != null) {
                        code = eElement.getElementsByTagName("Код").item(0).getTextContent();
                        code = code.replaceAll(" ", "");
                    }
                    else {
                        code = " ";
                    }

                    String name = eElement.getElementsByTagName("Наименование").item(0).getTextContent();
                    String groupId = eElement.getElementsByTagName("Группы").item(0).getChildNodes().item(1).getTextContent();

                    System.out.println("Артикул : " + article);
                    System.out.println("Наименование : " + name);
                    System.out.println("Код : " + code);
                    System.out.println("Группа : " + groupId);

                    for (int j = 0; j < nList2.getLength(); j++) { // Находим цену из offers.xml

                        Node nNode2 = nList2.item(j);
                        Element eElement2 = (Element) nNode2;

                        if (eElement2.getElementsByTagName("Ид").item(0).getTextContent().equals(eElement.getElementsByTagName("Ид").item(0).getTextContent())) {
                            float wholesalePrice = 0;
                            float retailPrice = 0;
                            String currency = " ";
                            if (eElement2.getElementsByTagName("Цены").item(0) != null) {

                                wholesalePrice = (float) Double.parseDouble(eElement2.getElementsByTagName("ЦенаЗаЕдиницу").item(0).getTextContent());
                                System.out.println("Цена : " + wholesalePrice);

                                if (eElement2.getElementsByTagName("ЦенаЗаЕдиницу").item(1) != null) {
                                    retailPrice = (float) Double.parseDouble(eElement2.getElementsByTagName("ЦенаЗаЕдиницу").item(1).getTextContent());
                                    System.out.println("Цена : " + retailPrice);
                                } else {
                                    retailPrice = (float) 0;
                                    wholesalePrice = (float) 0;
                                }


                                currency = eElement2.getElementsByTagName("Валюта").item(0).getTextContent();
                                System.out.println("Валюта : " + currency);
                            }
                            else {
                                System.out.println("Цена : " + 0);
                            }

                            int stock = 0;

                            if (eElement2.getElementsByTagName("Количество").item(0) == null) {
                                System.out.println("Количество : " + 0);
                            }
                            else {
                                double stockDouble = Double.parseDouble(eElement2.getElementsByTagName("Количество").item(0).getTextContent());
                                stock = (int) stockDouble;
                                System.out.println("Количество : " + stock);
                            }

                            ProductGroup productGroup = new ProductGroup();
                            productGroup.setGroupId(groupId);
                            products.add(new Product(article, name, stock, retailPrice, wholesalePrice, currency, productGroup));

                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }


    /* Связи групп */

    public ArrayList<GroupRelation> getRelationsList(ArrayList<GroupChild> list) {

        iterator = list.iterator();

        while (iterator.hasNext()) {
            toRelations();
        }

        return relations;
    }


    /* Список групп */
    public ArrayList<ProductGroup> getGroupList(ArrayList<GroupChild> list) {

        ArrayList<ProductGroup> productGroups = new ArrayList<ProductGroup>();

        for (int i=0; i<list.size(); i++) {
            ProductGroup productGroup = new ProductGroup();
            productGroup.setName(list.get(i).getName());
            productGroup.setGroupId(list.get(i).getId());

            productGroups.add(productGroup);
        }

        for (int i = 0; i< productGroups.size(); i++) {
            System.out.println(productGroups.get(i).getName() + " " + productGroups.get(i).getId());
        }

        return productGroups;
    }

    /* Функция по получению отношений */

    public void toRelations() {

        group = iterator.next();
        GroupChild root = group;

        for (int i=0; i<root.getChildsCount(); i++) {
            GroupRelation relation = new GroupRelation(root.getId());

            toRelations();

            relation.setChildGroupId(group.getId());
            relations.add(relation);
            group = root;

        }

    }


    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<ProductGroup> getProductGroups() {
        return productGroups;
    }

    public ArrayList<GroupRelation> getRelations() {
        return relations;
    }


}
