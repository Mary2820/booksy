package com.solvd.jaxbdemo;

import com.solvd.jaxbdemo.wrappers.BooksyDataWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class JAXBMarshal {
    private static final Logger logger = LogManager.getLogger(JAXBMarshal.class.getName());
    private static final File MARSHALED_XML = new File("src/main/resources/xmlfiles/marshaled_data.xml");
    private static final File ORIGINAL_XML = new File("src/main/resources/xmlfiles/booksy_data.xml");

    public static void main(String[] args) {
        BooksyDataWrapper data = unmarshalFromXML(ORIGINAL_XML);
        logData(data);

        marshalToXML(MARSHALED_XML, data);
    }

    private static void marshalToXML(File file, BooksyDataWrapper data) {
        if (data == null) {
            logger.error("Skipping marshalling: No data available.");
            return;
        }
        try {
            JAXBContext context = JAXBContext.newInstance(data.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(data, file);
            logger.info("Successfully saved XML to: " + file);
        } catch (JAXBException e) {
            logger.error("Marshalling failed for " + file, e);
        }
    }

    private static BooksyDataWrapper unmarshalFromXML(File xmlFile) {
        try {
            JAXBContext context = JAXBContext.newInstance(BooksyDataWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (BooksyDataWrapper) unmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            logger.error("Unmarshalling failed for file: {}", xmlFile, e);
            return null;
        }
    }

    private static void logData(BooksyDataWrapper data) {
        if (data == null) {
            logger.error("No data to log.");
            return;
        }
        logger.info("Users count: {}", data.getUsers().size());
        logger.info("Businesses count: {}", data.getBusinesses().size());
        logger.info("Employees count: {}", data.getEmployees().size());
        logger.info("Procedures count: {}", data.getProcedures().size());
        logger.info("Appointments count: {}", data.getAppointments().size());

        data.getUsers().forEach(user -> logger.info("User: {}", user));
        data.getBusinesses().forEach(business -> logger.info("Business: {}", business));
        data.getEmployees().forEach(employee -> logger.info("Employee: {}", employee));
        data.getProcedures().forEach(procedure -> logger.info("Procedure: {}", procedure));
        data.getAppointments().forEach(appointment -> logger.info("Appointment: {}", appointment));
    }
}
