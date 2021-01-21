package com.paymentcomponents.sepa;

import gr.datamation.sepa.core.messages.eba.pacs.FIToFIPaymentStatusReport;

public class ParseValidFilePacs002 {

    public static void main(String... args) {
        execute();
    }

    public static void execute() {
        //You have to initiate the message object using the suitable constructor.
        //In order to parse and validate a pacs.002 you need to use FIToFIPaymentStatusReport
        //FIToFIPaymentStatusReport > matches the xml element <FIToFIPmtStsRpt>
        FIToFIPaymentStatusReport messageObject = new FIToFIPaymentStatusReport();
        try {
            //Use parseAndValidateString() to fill the messageObject the content of the message and validate it
            //validation fills the message variable errors object with any issue found during validation
            messageObject.parseAndValidate(validPacs002File);

            Utils.printValidMessageOrErrors(messageObject);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message cannot be parsed");
        }
    }

    private static final String validPacs002File = "./src/test/resources/pacs.002.001.03S2.xml";

}
