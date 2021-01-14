package com.paymentcomponents.sepa;

import gr.datamation.sepa.core.messages.eba.pacs.FIToFIPaymentStatusReport;

public class ParseValidPacs002 {

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
            messageObject.parseAndValidateString(validPacs002String);
            Utils.printValidMessageOrErrors(messageObject);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message cannot be parsed");
        }
    }

    private static final String validPacs002String = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.03S2\">\n" +
            "    <FIToFIPmtStsRptS2>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>0006936999</MsgId>\n" +
            "            <CreDtTm>2010-02-23T14:29:02.0Z</CreDtTm>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BIC>BCYPCY2N</BIC>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "        </GrpHdr>\n" +
            "        <OrgnlGrpInfAndSts>\n" +
            "            <OrgnlMsgId>BCYPCY2N20100223152604686001</OrgnlMsgId>\n" +
            "            <OrgnlMsgNmId>pacs.004</OrgnlMsgNmId>\n" +
            "            <OrgnlNbOfTxs>4</OrgnlNbOfTxs>\n" +
            "            <OrgnlCtrlSum>1200</OrgnlCtrlSum>\n" +
            "            <GrpSts>ACCP</GrpSts>\n" +
            "            <StsRsnInf>\n" +
            "                <Orgtr>\n" +
            "                    <Id>\n" +
            "                        <OrgId>\n" +
            "                            <BICOrBEI>DEUTDEFF</BICOrBEI>\n" +
            "                        </OrgId>\n" +
            "                    </Id>\n" +
            "                </Orgtr>\n" +
            "                <Rsn>\n" +
            "                    <Prtry>B00</Prtry>\n" +
            "                </Rsn>\n" +
            "            </StsRsnInf>\n" +
            "        </OrgnlGrpInfAndSts>\n" +
            "    </FIToFIPmtStsRptS2>\n" +
            "</Document>";

}
