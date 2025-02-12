package com.solvd.booksyapp.utils.stax;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class XMLValidator {
    private static final Logger logger = LogManager.getLogger(XMLValidator.class);
    private static XMLValidator instance;

    private XMLValidator() {
    }

    public static XMLValidator getInstance() {
        if (instance == null) {
            instance = new XMLValidator();
        }
        return instance;
    }

    public boolean validate(String xmlPath, String xsdPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
            logger.info("XML validation successful");
            return true;
        } catch (Exception e) {
            logger.error("XML validation error: " + e.getMessage());
            return false;
        }
    }
} 