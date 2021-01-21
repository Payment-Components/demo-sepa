package com.paymentcomponents.sepa;

import gr.datamation.sepa.core.messages.eba.pacs.FIToFIPaymentStatusReport;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleTests {

    @Test
    public void simpleParseFileTest() {
        //You have to initiate the message object using the suitable constructor.
        //In order to parse and validate a pacs.002 you need to use FIToFIPaymentStatusReport
        //FIToFIPaymentStatusReport > matches the xml element <FIToFIPmtStsRpt>
        FIToFIPaymentStatusReport messageObject = new FIToFIPaymentStatusReport();
        try {
            //Use parseAndValidateString() to fill the messageObject the content of the message and validate it
            //validation fills the message variable errors object with any issue found during validation
            messageObject.parseAndValidate(validPacs002File);

            Utils.printValidMessageOrErrors(messageObject);

            assertEquals(messageObject.getRootMessage().getGrpHdr().getMsgId(), "0006936999");

            assertEquals(messageObject.getRootMessage().getOrgnlGrpInfAndSts().getStsRsnInf().getOrgtr().getId().getOrgId().getBICOrBEI(), "DEUTDEFF");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message cannot be parsed");
        }
    }

    private static final String validPacs002File = "./src/test/resources/pacs.002.001.03S2.xml";

}
