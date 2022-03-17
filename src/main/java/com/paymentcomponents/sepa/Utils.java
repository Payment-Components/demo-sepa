package com.paymentcomponents.sepa;

import gr.datamation.sepa.core.messages.CoreMessage;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utils {

    public static void printValidMessageOrErrors(CoreMessage message) {
        if (!message.hasValidationErrors()) {
            System.out.println("Message is valid");
            System.out.println(message);
        } else {
            System.err.println("Message is invalid, and the errors are the following:");
            for (Object error : message.getErrors()) {
                System.err.println(error);
            }
        }
    }

    public static XMLGregorianCalendar xmlGregorianCalendar() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        XMLGregorianCalendar xmlDate = null;
        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return xmlDate;
    }

}
