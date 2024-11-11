package com.studentApp.util;

import com.studentApp.Model.Student;
import com.studentApp.Model.University;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLUtil {

    // Create a new empty XML file if it does not exist
    public static void createNewXMLFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists() && file.createNewFile()) {
            System.out.println("XML file created at: " + file.getAbsolutePath());
        }

        Document doc = createDocument();
        writeDocumentToFile(doc, file);
    }

    // Read students from XML file
    public static University readStudentsFromXML(String filePath) throws Exception {
        File xmlFile = new File(filePath);
        if (!xmlFile.exists()) throw new IOException("XML file not found: " + filePath);

        Document doc = parseDocument(xmlFile);
        University university = new University();
        NodeList studentList = doc.getElementsByTagName("Student");

        for (int i = 0; i < studentList.getLength(); i++) {
            Element studentElement = (Element) studentList.item(i);
            Student student = parseStudent(studentElement);
            university.addStudent(student);
        }

        return university;
    }

    // Save the list of students to XML
    public static void saveStudentsToXML(String filePath, University university) throws Exception {
        Document doc = createDocument();
        Element rootElement = doc.createElement("University");
        doc.appendChild(rootElement);

        for (Student student : university.getStudents()) {
            Element studentElement = createStudentElement(doc, student);
            rootElement.appendChild(studentElement);
        }

        writeDocumentToFile(doc, new File(filePath));
    }

    // Helper methods
    private static Document createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

    private static Document parseDocument(File xmlFile) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc;
    }

    private static Student parseStudent(Element studentElement) {
        String studentID = studentElement.getAttribute("ID");
        String firstName = getTextContent(studentElement, "FirstName");
        String lastName = getTextContent(studentElement, "LastName");
        String gender = getTextContent(studentElement, "Gender");
        double gpa = Double.parseDouble(getTextContent(studentElement, "GPA"));
        int level = Integer.parseInt(getTextContent(studentElement, "Level"));
        String address = getTextContent(studentElement, "Address");
        return new Student(studentID, firstName, lastName, gender, gpa, level, address);
    }

    private static String getTextContent(Element parent, String tagName) {
        return parent.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private static Element createStudentElement(Document doc, Student student) {
        Element studentElement = doc.createElement("Student");
        studentElement.setAttribute("ID", student.getId());
        appendChildWithText(doc, studentElement, "FirstName", student.getFirstName());
        appendChildWithText(doc, studentElement, "LastName", student.getLastName());
        appendChildWithText(doc, studentElement, "Gender", student.getGender());
        appendChildWithText(doc, studentElement, "GPA", String.valueOf(student.getGpa()));
        appendChildWithText(doc, studentElement, "Level", String.valueOf(student.getLevel()));
        appendChildWithText(doc, studentElement, "Address", student.getAddress());
        return studentElement;
    }

    private static void appendChildWithText(Document doc, Element parent, String tagName, String text) {
        Element child = doc.createElement(tagName);
        child.appendChild(doc.createTextNode(text));
        parent.appendChild(child);
    }

    private static void writeDocumentToFile(Document doc, File file) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
    }
}