package com.solvd.booksyapp.utils.stax;

import com.solvd.booksyapp.models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class StAXParser {
    private static final Logger logger = LogManager.getLogger(StAXParser.class);
    private static final String XML_PATH = "src/main/resources/xmlfiles/booksy_data.xml";
    private static final String XSD_PATH = "src/main/resources/xmlfiles/booksy_schema.xsd";

    public static void main(String[] args) {
        if (!XMLValidator.getInstance().validate(XML_PATH, XSD_PATH)) {
            logger.error("XML validation failed. Stopping parsing.");
            return;
        }

        try (FileInputStream fileInputStream = new FileInputStream(XML_PATH)) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(fileInputStream);

            List<User> users = new ArrayList<>();
            List<Business> businesses = new ArrayList<>();
            List<Employee> employees = new ArrayList<>();
            List<Procedure> procedures = new ArrayList<>();
            List<Appointment> appointments = new ArrayList<>();

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String elementName = startElement.getName().getLocalPart();

                    switch (elementName) {
                        case "user":
                            users.add(parseUser(eventReader, startElement));
                            break;
                        case "business":
                            businesses.add(parseBusiness(eventReader, startElement));
                            break;
                        case "employee":
                            employees.add(parseEmployee(eventReader, startElement));
                            break;
                        case "procedure":
                            procedures.add(parseProcedure(eventReader, startElement));
                            break;
                        case "appointment":
                            appointments.add(parseAppointment(eventReader, startElement));
                            break;
                    }
                }
            }
            eventReader.close();

            logger.info("Parsed {} users", users.size());
            users.forEach(user -> logger.info("User: {}", user));
            
            logger.info("Parsed {} businesses", businesses.size());
            businesses.forEach(business -> logger.info("Business: {}", business));
            
            logger.info("Parsed {} employees", employees.size());
            employees.forEach(employee -> logger.info("Employee: {}", employee));
            
            logger.info("Parsed {} procedures", procedures.size());
            procedures.forEach(procedure -> logger.info("Procedure: {}", procedure));
            
            logger.info("Parsed {} appointments", appointments.size());
            appointments.forEach(appointment -> logger.info("Appointment: {}", appointment));

        } catch (Exception e) {
            logger.error("Error parsing XML: " + e.getMessage());
        }
    }

    private static User parseUser(XMLEventReader eventReader, StartElement startElement) throws XMLStreamException {
        User user = new User();
        user.setId(Long.parseLong(startElement.getAttributeByName(new javax.xml.namespace.QName("id")).getValue()));

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            
            if (event.isStartElement()) {
                String elementName = event.asStartElement().getName().getLocalPart();
                String data = eventReader.nextEvent().asCharacters().getData();
                
                switch (elementName) {
                    case "firstName":
                        user.setFirstName(data);
                        break;
                    case "lastName":
                        user.setLastName(data);
                        break;
                    case "email":
                        user.setEmail(data);
                        break;
                    case "phone":
                        user.setPhone(data);
                        break;
                    case "password":
                        user.setPassword(data);
                        break;
                    case "roleId":
                        user.setRoleId(Long.parseLong(data));
                        break;
                }
            }

            if (event.isEndElement() && 
                event.asEndElement().getName().getLocalPart().equals("user")) {
                break;
            }
        }
        return user;
    }

    private static Business parseBusiness(XMLEventReader eventReader, StartElement startElement) throws XMLStreamException {
        Business business = new Business();
        business.setId(Long.parseLong(startElement.getAttributeByName(new javax.xml.namespace.QName("id")).getValue()));

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            
            if (event.isStartElement()) {
                String elementName = event.asStartElement().getName().getLocalPart();
                String data = eventReader.nextEvent().asCharacters().getData();
                
                switch (elementName) {
                    case "name":
                        business.setName(data);
                        break;
                    case "address":
                        business.setAddress(data);
                        break;
                    case "city":
                        business.setCity(data);
                        break;
                    case "postcode":
                        business.setPostcode(data);
                        break;
                }
            }

            if (event.isEndElement() && 
                event.asEndElement().getName().getLocalPart().equals("business")) {
                break;
            }
        }
        return business;
    }

    private static Employee parseEmployee(XMLEventReader eventReader, StartElement startElement) throws XMLStreamException {
        Employee employee = new Employee();
        employee.setId(Long.parseLong(startElement.getAttributeByName(new javax.xml.namespace.QName("id")).getValue()));

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            
            if (event.isStartElement()) {
                String elementName = event.asStartElement().getName().getLocalPart();
                String data = eventReader.nextEvent().asCharacters().getData();
                
                switch (elementName) {
                    case "userId":
                        employee.setUserId(Long.parseLong(data));
                        break;
                    case "description":
                        employee.setDescription(data);
                        break;
                    case "rating":
                        employee.setRating(new BigDecimal(data));
                        break;
                    case "businessId":
                        employee.setBusinessId(Long.parseLong(data));
                        break;
                }
            }

            if (event.isEndElement() && 
                event.asEndElement().getName().getLocalPart().equals("employee")) {
                break;
            }
        }
        return employee;
    }

    private static Procedure parseProcedure(XMLEventReader eventReader, StartElement startElement) throws XMLStreamException {
        Procedure procedure = new Procedure();
        procedure.setId(Long.parseLong(startElement.getAttributeByName(new javax.xml.namespace.QName("id")).getValue()));

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            
            if (event.isStartElement()) {
                String elementName = event.asStartElement().getName().getLocalPart();
                String data = eventReader.nextEvent().asCharacters().getData();
                
                switch (elementName) {
                    case "categoryId":
                        procedure.setCategoryId(Long.parseLong(data));
                        break;
                    case "name":
                        procedure.setName(data);
                        break;
                    case "description":
                        procedure.setDescription(data);
                        break;
                    case "duration":
                        procedure.setDuration(Integer.parseInt(data));
                        break;
                }
            }

            if (event.isEndElement() && 
                event.asEndElement().getName().getLocalPart().equals("procedure")) {
                break;
            }
        }
        return procedure;
    }

    private static Appointment parseAppointment(XMLEventReader eventReader, StartElement startElement) throws XMLStreamException {
        Appointment appointment = new Appointment();
        appointment.setId(Long.parseLong(startElement.getAttributeByName(new javax.xml.namespace.QName("id")).getValue()));

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            
            if (event.isStartElement()) {
                String elementName = event.asStartElement().getName().getLocalPart();
                String data = eventReader.nextEvent().asCharacters().getData();
                
                switch (elementName) {
                    case "clientId":
                        appointment.setClientId(Long.parseLong(data));
                        break;
                    case "procedureId":
                        appointment.setProcedureId(Long.parseLong(data));
                        break;
                    case "employeeId":
                        appointment.setEmployeeId(Long.parseLong(data));
                        break;
                    case "status":
                        appointment.setStatus(data);
                        break;
                    case "createdAt":
                        appointment.setCreatedAt(LocalDateTime.parse(data));
                        break;
                    case "date":
                        appointment.setDate(LocalDate.parse(data));
                        break;
                    case "dayOfWeek":
                        appointment.setDayOfWeek(data);
                        break;
                    case "startTime":
                        appointment.setStartTime(LocalTime.parse(data));
                        break;
                    case "endTime":
                        appointment.setEndTime(LocalTime.parse(data));
                        break;
                }
            }

            if (event.isEndElement() && 
                event.asEndElement().getName().getLocalPart().equals("appointment")) {
                break;
            }
        }
        return appointment;
    }
}
