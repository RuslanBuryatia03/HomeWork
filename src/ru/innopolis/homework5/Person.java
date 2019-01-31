package ru.innopolis.homework5;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@XmlRootElement
public class Person {
    public int id;
    private int age;
    private double height;
    private String firstName;
    public final String origin = "english";
    String education;

    private static transient volatile String staticField;
    Person(){}

    Person (int id, int age, double height, String firstName, String education){
        this.id = id;
        this.age = age;
        this.height = height;
        this.firstName = firstName;
        this.education = education;
    }

    /**
     * Сериализует объект в формат xml
     *
     * @param object объект для сериализации
     * @throws IllegalAccessException выбрасывается при неудачной попытке установить значение поля
     */

    void serialize(Object object, String file) throws IllegalAccessException {

        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        String[] valueFields = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            if (Modifier.isStatic(fields[i].getModifiers())) {
                continue;
            }
            fields[i].setAccessible(true);

            switch (fields[i].getType().toString()) {
                case ("byte"):
                    valueFields[i] = String.valueOf(fields[i].getByte(object));
                    break;
                case ("short"):
                    valueFields[i] = String.valueOf(fields[i].getShort(object));
                    break;
                case ("int"):
                    valueFields[i] = String.valueOf(fields[i].getInt(object));
                    break;
                case ("long"):
                    valueFields[i] = String.valueOf(fields[i].getLong(object));
                    break;
                case ("char"):
                    valueFields[i] = String.valueOf(fields[i].getChar(object));
                    break;
                case ("float"):
                    valueFields[i] = String.valueOf(fields[i].getFloat(object));
                    break;
                case ("double"):
                    valueFields[i] = String.valueOf(fields[i].getDouble(object));
                    break;
                case ("boolean"):
                    valueFields[i] = String.valueOf(fields[i].getBoolean(object));
                    break;
                case ("class java.lang.String"):
                    if (fields[i].get(object) != null) {
                        valueFields[i] = (String) fields[i].get(object);
                    } else {
                        valueFields[i] = "null";
                    }
                    break;
                default:
                    System.out.println("Необрабатываемый данной программой тип");
                    return;
            }
        }
        formingXML(clazz, fields, valueFields, file);
    }


    /**
     * Создает XML файл file по заданным исходным данным
     * @param clazz Class объекта
     * @param fields массив полей
     * @param valueFields - массив значений
     * @param file   - путь, по которому необходимо сохранить файл
     */
    private void formingXML(Class clazz, Field[] fields, String[] valueFields, String file) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement(clazz.getCanonicalName());
            doc.appendChild(rootElement);

            for (int i = 0; i < fields.length; i++) {
                if (Modifier.isStatic(fields[i].getModifiers())) {
                    continue;
                }
                rootElement.appendChild(createElement(doc, fields[i].getName(), valueFields[i]));
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(file));

            transformer.transform(source, result);
            System.out.println("File saved!");
        }
        catch (ParserConfigurationException | TransformerException pce) {
            System.out.println("Что то пошло не так при формировании выходного файла");
            pce.printStackTrace();
        }

    }



    /**
     * Создает элемент из поля объекта в Document
     *
     * @param doc         Document для формирования файла
     * @param nameElement имя элемента
     * @param value       значение элемента
     * @return элемент
     */
    private Element createElement(Document doc, String nameElement, String value) {
        Element element = doc.createElement(nameElement);
        Element valueElement = doc.createElement("value");
        valueElement.appendChild(doc.createTextNode(value));
        element.appendChild(valueElement);
        return element;
    }



    /**
     * Десериализация объекта
     * @param file файл с входными данными для десериализации
     * @return десериализованный объект Object
     */
    Object deSerialize(String file) {
        Object object = null;
        try {

            File inputFile = new File(file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            Class cls = Class.forName(doc.getDocumentElement().getNodeName());
            object = cls.newInstance();

            Node rootNode = doc.getDocumentElement();
            NodeList nList = rootNode.getChildNodes();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                String nValue = nNode.getChildNodes().item(0).getFirstChild().getNodeValue();
                Field field = cls.getDeclaredField(nNode.getNodeName());
                field.setAccessible(true);

                switch (field.getType().getName()) {
                    case "byte":
                        field.set(object, Byte.valueOf(nValue));
                        break;
                    case "short":
                        field.set(object, Short.valueOf(nValue));
                        break;
                    case "int":
                        field.setInt(object, Integer.valueOf(nValue));
                        break;
                    case "long":
                        field.set(object, Long.valueOf(nValue));
                        break;
                    case "float":
                        field.set(object, Float.valueOf(nValue));
                        break;
                    case "double":
                        field.set(object, Double.valueOf(nValue));
                        break;
                    case "char":
                        field.set(object, nValue.charAt(0));
                        break;
                    case "boolean":
                        field.set(object, Boolean.valueOf(nValue));
                        break;
                    case "java.lang.String":
                        if (!(nValue.equals("null"))) {
                            field.set(object, nValue);
                        }
                        break;
                    default:
                        System.out.println("Тип поля не поддерживается данной программой");
                        return null;
                }
            }

        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("Ошибка парсинга файла");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла ");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Класс не найден");
            e.printStackTrace();
        } catch (IllegalAccessException | InstantiationException e) {
            System.out.println("Невозможно создать объект");
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            System.out.println("Поле из файла не найдено в классе объекта");
            e.printStackTrace();
        }
        return object;
    }

    public int getId() {
        return id;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    @XmlElement
    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    @XmlElement
    public void setHeight(double height) {
        this.height = height;
    }

    public String getFirstName() {
        return firstName;
    }

    @XmlElement
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOrigin() {
        return origin;
    }


    public String getEducation() {
        return education;
    }

    @XmlElement
    public void setEducation(String education) {
        this.education = education;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", age=" + age +
                ", height=" + height +
                ", firstName='" + firstName + '\'' +
                ", origin='" + origin + '\'' +
                ", education='" + education + '\'' +
                '}';
    }
}
