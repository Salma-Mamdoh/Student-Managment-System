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
    public static void createNewXMLFile(String filePath) throws ParserConfigurationException, TransformerException, IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            boolean res = file.createNewFile(); // Ensure the file is created
            if (res) {
                System.out.println("XML file created at: " + file.getAbsolutePath());
            }
        }

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("University");
        doc.appendChild(rootElement);
        writeDocumentToFile(doc, file);
    }

    public static University readStudentsFromXML(String filePath) throws Exception {
        File xmlFile = new File(filePath);
        if (!xmlFile.exists()) {
            throw new IOException("XML file not found: " + filePath);
        }

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        University university = new University();
        NodeList studentList = doc.getElementsByTagName("Student");

        for (int i = 0; i < studentList.getLength(); i++) {
            Node studentNode = studentList.item(i);
            if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element studentElement = (Element) studentNode;
                String studentID = studentElement.getAttribute("ID");
                String firstName = studentElement.getElementsByTagName("FirstName").item(0).getTextContent();
                String lastName = studentElement.getElementsByTagName("LastName").item(0).getTextContent();
                String gender = studentElement.getElementsByTagName("Gender").item(0).getTextContent();
                double gpa = Double.parseDouble(studentElement.getElementsByTagName("GPA").item(0).getTextContent());
                int level = Integer.parseInt(studentElement.getElementsByTagName("Level").item(0).getTextContent());
                String address = studentElement.getElementsByTagName("Address").item(0).getTextContent();

                Student student = new Student(studentID, firstName, lastName, gender, gpa, level, address);
                university.addStudent(student);
            }
        }

        return university;
    }

    // Save the list of students to XML
    public static void saveStudentsToXML(String filePath, University university) throws Exception {
        File xmlFile = new File(filePath);


        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element rootElement = doc.createElement("University");
        doc.appendChild(rootElement);

        List<Student> students = university.getStudents();
        for (Student student : students) {
            Element studentElement = doc.createElement("Student");
            studentElement.setAttribute("ID", student.getId());

            Element firstName = doc.createElement("FirstName");
            firstName.appendChild(doc.createTextNode(student.getFirstName()));
            studentElement.appendChild(firstName);

            Element lastName = doc.createElement("LastName");
            lastName.appendChild(doc.createTextNode(student.getLastName()));
            studentElement.appendChild(lastName);

            Element gender = doc.createElement("Gender");
            gender.appendChild(doc.createTextNode(student.getGender()));
            studentElement.appendChild(gender);

            Element gpa = doc.createElement("GPA");
            gpa.appendChild(doc.createTextNode(String.valueOf(student.getGpa())));
            studentElement.appendChild(gpa);

            Element level = doc.createElement("Level");
            level.appendChild(doc.createTextNode(String.valueOf(student.getLevel())));
            studentElement.appendChild(level);

            Element address = doc.createElement("Address");
            address.appendChild(doc.createTextNode(student.getAddress()));
            studentElement.appendChild(address);

            rootElement.appendChild(studentElement);
        }


        writeDocumentToFile(doc, xmlFile);
    }


    private static void writeDocumentToFile(Document doc, File file) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // Set properties for pretty printing
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); // Set indentation

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
    }
}
