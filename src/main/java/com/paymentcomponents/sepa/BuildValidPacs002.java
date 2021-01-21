package com.paymentcomponents.sepa;

import gr.datamation.sepa.core.common.EnvironmentConstants;
import gr.datamation.sepa.core.messages.eba.pacs.FIToFIPaymentStatusReport;
import gr.datamation.sepa.types.eba.pacs002.*;

import java.math.BigDecimal;
import java.util.Calendar;

public class BuildValidPacs002 {

    public static void main(String... args) {
        execute();
    }

    public static void execute() {
        buildPacs002_method1();
        buildPacs002_method2();
    }

    public static void buildPacs002_method1() {
        try {
            //You have to initiate the message object using the suitable constructor.
            //In order to parse and validate a pacs.002 you need to use FIToFIPaymentStatusReport whose relations are available inside the object
            //    rootElement = "FIToFIPmtStsRptS2",
            //    namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.002.001.03S2",
            //    xsd = "/xsd/eba/pacs.002.001.03S2.xsd"
            System.out.println("Build pacs.002.001.03 using setElement()");
            FIToFIPaymentStatusReport messageObject = new FIToFIPaymentStatusReport();

            // Set elements for GroupHeader
            messageObject.setElement("/GrpHdr/MsgId", "MESSAGEID");
            messageObject.setElement("/GrpHdr/CreDtTm", Calendar.getInstance(), EnvironmentConstants.DETAILED_DATE_FORMAT);

            // Set elements for OriginalGroupInformation
            messageObject.setElement("/OrgnlGrpInfAndSts[0]/OrgnlMsgId", "ORIGINALMESSAGEID");
            messageObject.setElement("/OrgnlGrpInfAndSts[0]/OrgnlMsgNmId", "pacs.008.001");
            messageObject.setElement("/OrgnlGrpInfAndSts[0]/OrgnlNbOfTxs", "1");
            messageObject.setElement("/OrgnlGrpInfAndSts[0]/OrgnlCtrlSum", new BigDecimal("1"));
            messageObject.setElement("/OrgnlGrpInfAndSts[0]/GrpSts", "ACCP");

            messageObject.setElement("/OrgnlGrpInfAndSts[0]/StsRsnInf/Rsn/Prtry", "B00");
            messageObject.setElement("/OrgnlGrpInfAndSts[0]/StsRsnInf/Orgtr/Id/OrgId/BICOrBEI", "DEUTDEFF");

            //Use validate() to check the messageObject and validate the content
            //message variable errors is filled with any issue found during validation
            messageObject.validate();
            Utils.printValidMessageOrErrors(messageObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void buildPacs002_method2() {
        try {
            //You have to initiate the message object using the suitable constructor.
            //In order to parse and validate a pacs.002 you need to use FIToFIPaymentStatusReport whose relations are available inside the object
            //    rootElement = "FIToFIPmtStsRptS2",
            //    namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.002.001.03S2",
            //    xsd = "/xsd/eba/pacs.002.001.03S2.xsd"
            System.out.println("Build pacs.002.001.03 using message classes()");
            FIToFIPaymentStatusReport messageObject = new FIToFIPaymentStatusReport();

            // Set elements for GroupHeader
            messageObject.getRootMessage().setGrpHdr(new S2SCTGroupHeader37());
            messageObject.getRootMessage().getGrpHdr().setMsgId("MESSAGEID");
            messageObject.getRootMessage().getGrpHdr().setCreDtTm(Utils.xmlGregorianCalendar());

            // Set elements for OriginalGroupInformation
            messageObject.getRootMessage().setOrgnlGrpInfAndSts(new OriginalGroupInformation20());
            messageObject.getRootMessage().getOrgnlGrpInfAndSts().setOrgnlMsgId("ORIGINALMESSAGEID");
            messageObject.getRootMessage().getOrgnlGrpInfAndSts().setOrgnlMsgNmId("pacs.008.001");
            messageObject.getRootMessage().getOrgnlGrpInfAndSts().setOrgnlNbOfTxs("1");
            messageObject.getRootMessage().getOrgnlGrpInfAndSts().setOrgnlCtrlSum(new BigDecimal("1"));
            messageObject.getRootMessage().getOrgnlGrpInfAndSts().setGrpSts(S2SCTTransactionGroupStatus3Code.ACCP);

            messageObject.getRootMessage().getOrgnlGrpInfAndSts().setStsRsnInf(new S2SCTStatusReasonInformation8());
            messageObject.getRootMessage().getOrgnlGrpInfAndSts().getStsRsnInf().setRsn(new StatusReason6Choice());
            messageObject.getRootMessage().getOrgnlGrpInfAndSts().getStsRsnInf().getRsn().setPrtry("B00");

            messageObject.getRootMessage().getOrgnlGrpInfAndSts().getStsRsnInf().setOrgtr(new S2SCTId5());
            messageObject.getRootMessage().getOrgnlGrpInfAndSts().getStsRsnInf().getOrgtr().setId(new S2SCTParty3Choice());
            messageObject.getRootMessage().getOrgnlGrpInfAndSts().getStsRsnInf().getOrgtr().getId().setOrgId(new S2SCTOrganisationIdentification4());
            messageObject.getRootMessage().getOrgnlGrpInfAndSts().getStsRsnInf().getOrgtr().getId().getOrgId().setBICOrBEI("DEUTDEFF");

            //Use validate() to check the messageObject and validate the content
            //message variable errors is filled with any issue found during validation
            messageObject.validate();
            Utils.printValidMessageOrErrors(messageObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
