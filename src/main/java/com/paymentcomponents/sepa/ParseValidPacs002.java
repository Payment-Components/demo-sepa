package com.paymentcomponents.sepa;

import gr.datamation.sepa.core.messages.eba.pacs.FIToFIPaymentStatusReport;
import gr.datamation.sepa.core.messages.interchange.SCTQvfBulkCreditTransfer;

public class ParseValidPacs002 {

    public static void main(String... args) {
        execute();
        executeWithEbaHeader();
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

    public static void executeWithEbaHeader() {
        SCTQvfBulkCreditTransfer messageObject = new SCTQvfBulkCreditTransfer();
        try {
            //Use parseAndValidateString() to fill the messageObject the content of the message and validate it
            //validation fills the message variable errors object with any issue found during validation
            messageObject.parseAndValidateString(validPacs002EbaHeaderString);
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

    private static final String validPacs002EbaHeaderString = "<urn:SCTQvfBlkCredTrf xmlns:urn=\"urn:S2SCTQvf:xsd:$SCTQvfBlkCredTrf\">\n" +
            "  <urn:SndgInst>TESTBICA</urn:SndgInst>\n" +
            "  <urn:RcvgInst>TESTBICB</urn:RcvgInst>\n" +
            "  <urn:SrvcId>SCT</urn:SrvcId>\n" +
            "  <urn:TstCode>T</urn:TstCode>\n" +
            "  <urn:FType>QVF</urn:FType>\n" +
            "  <urn:FileRef>string</urn:FileRef>\n" +
            "  <urn:FDtTm>2014-09-19T02:18:33</urn:FDtTm>\n" +
            "  <!--Optional:-->\n" +
            "  <urn:OrigFRef>string</urn:OrigFRef>\n" +
            "  <urn:OrigFName>string</urn:OrigFName>\n" +
            "  <!--Optional:-->\n" +
            "  <urn:OrigDtTm>2006-08-19T20:27:14+03:00</urn:OrigDtTm>\n" +
            "  <urn:FileRjctRsn>str</urn:FileRjctRsn>\n" +
            "  <urn:FileBusDt>2009-05-16</urn:FileBusDt>\n" +
            "  <urn:FileCycleNo>01</urn:FileCycleNo>\n" +
            "  <!--Zero or more repetitions:-->\n" +
            "  <urn:FIToFIPmtStsRptS2 xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.03S2\">\n" +
            "    <GrpHdr>\n" +
            "      <MsgId>0006936999</MsgId>\n" +
            "      <CreDtTm>2010-02-23T14:29:02.0Z</CreDtTm>\n" +
            "      <InstgAgt>\n" +
            "        <FinInstnId>\n" +
            "          <BIC>BCYPCY2N</BIC>\n" +
            "        </FinInstnId>\n" +
            "      </InstgAgt>\n" +
            "    </GrpHdr>\n" +
            "    <OrgnlGrpInfAndSts>\n" +
            "      <OrgnlMsgId>BCYPCY2N20100223152604686001</OrgnlMsgId>\n" +
            "      <OrgnlMsgNmId>pacs.004</OrgnlMsgNmId>\n" +
            "      <OrgnlNbOfTxs>4</OrgnlNbOfTxs>\n" +
            "      <OrgnlCtrlSum>1200</OrgnlCtrlSum>\n" +
            "      <GrpSts>ACCP</GrpSts>\n" +
            "      <StsRsnInf>\n" +
            "        <Orgtr>\n" +
            "          <Id>\n" +
            "            <OrgId>\n" +
            "              <BICOrBEI>DEUTDEFF</BICOrBEI>\n" +
            "            </OrgId>\n" +
            "          </Id>\n" +
            "        </Orgtr>\n" +
            "        <Rsn>\n" +
            "          <Prtry>B00</Prtry>\n" +
            "        </Rsn>\n" +
            "      </StsRsnInf>\n" +
            "    </OrgnlGrpInfAndSts>\n" +
            "  </urn:FIToFIPmtStsRptS2>\n" +
            "</urn:SCTQvfBlkCredTrf>";

}
