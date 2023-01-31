<a id="logo" href="https://www.paymentcomponents.com" title="Payment Components" target="_blank">
    <img loading="lazy" src="https://i.postimg.cc/yN5TNy29/LOGO-HORIZONTAL2.png" alt="Payment Components">
</a>

# SEPA Message Validator Demo

This project is part of the [FINaplo](https://finaplo.paymentcomponents.com) product and is here to demonstrate how our [SDK](https://finaplo.paymentcomponents.com/financial-messages) for 
SEPA Messages Validation works. For our demonstration we are going to use the demo SDK which can parse/validate/generate a pacs.002.001.XX message. 

It's a simple maven project, you can download it and run it, with Java 1.8 or above.

## SDK setup
Incorporate the SDK [jar](https://nexus.paymentcomponents.com/repository/public/gr/datamation/sepa-core-jaxb/21.13.0/sepa-core-jaxb-21.13.0-demo.jar) into your project by the regular IDE means. 
This process will vary depending upon your specific IDE and you should consult your documentation on how to deploy a bean. 
For example in Eclipse all that needs to be done is to import the jar files into a project.
Alternatively, you can import it as a Maven or Gradle dependency.  

##### Maven
Define repository in the repositories section
```xml
<repository>
    <id>paymentcomponents</id>
    <url>https://nexus.paymentcomponents.com/repository/public</url>
</repository>
```
Import the SDK
```xml
<dependency>
    <groupId>gr.datamation</groupId>
    <artifactId>sepa-core-jaxb</artifactId>
    <version>22.1.0</version>
    <classifier>demo</classifier>
</dependency>
```

##### Gradle 
Define repository in the repositories section
```groovy
repositories {
    maven {
        url "https://nexus.paymentcomponents.com/repository/public"
    }
}
```
Import the SDK
```groovy
implementation 'gr.datamation:sepa-core-jaxb:22.1.0:demo@jar'
```
In case you purchase the SDK you will be given a protected Maven repository with a user name and a password. You can configure your project to download the SDK from there.

## HOW-TO Use our SDK

### Variations

There are many variations of SEPA xsd files and the following are supported by our SDK.  
This demo contains the `eba` version of `pacs.002.001.03`. 

- `eba` - European Banking Authority
- `epc` - European Payment Council
- `dias` - DIAS Interbanking System
- `sibs` - SIBS International
- `p27` -  Nordic Payments

All SEPA messages are identified by a code id and a name. 
The code id (`FIToFIPmtStsRptS2`) and the name (`FIToFIPaymentStatusReportV03`) are located in the .xsd file that describes the XML schema of each message.   
Both the name and the code id of the message are available in the SEPA messages catalogue.

*Inside pacs.002.001.03S2.xsd*
```xml
<xs:complexType name="Document">
    <xs:sequence>
        <xs:element name="FIToFIPmtStsRptS2" type="FIToFIPaymentStatusReportV03"/>
    </xs:sequence>
</xs:complexType>
```

### Message objects
For every SEPA message there is an equivalent class in our SDK. The name of the class is equivalent to the name of the message. 
For example the name of the class for the message named `FIToFIPmtStsRptS2` is `FIToFIPaymentStatusReport`.
   
   | Object | Name | XSD | Package |
   | ----- | ----- | ----- | ----- |
   | FIToFIPaymentStatusReport | FIToFIPmtStsRptS2 | pacs.002.001.03S2 | eba |  
   

#### Building and validating messages
There are three steps the user must follow in order to build a new SEPA message:

1. ##### Initialize the class corresponding to the message.
    The initialization of the class is as simple as initializing any class in Java. For the example message we are using here (FIToFIPaymentStatusReport) the initialization would be
    ```java
    FIToFIPaymentStatusReport message = new FIToFIPaymentStatusReport();
    ```
    The above command will initialize a class for this message named FIToFIPaymentStatusReport which is initially empty.  
    
    Parsing a file
    ```java
    // using File object as parameter
    message.parseAndValidate(new File("/path/to/pacs.002.001.03S2.xml"));
    // or using just the path as parameter
    message.parseAndValidate("/path/to/pacs.002.001.03S2.xml");
    // or using path and encoding
    message.parseAndValidate("/path/to/pacs.002.001.03S2.xml", "UTF-8"); 
    ```
    Parsing a text
    ```java
    message.parseAndValidateString("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                     "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.03S2\">\n" +
                     "  <FIToFIPmtStsRptS2>\n" +
                     "     ............\n" +
                     "  </FIToFIPmtStsRptS2>\n" +
                     "</Document>");
    ```

1. ##### Add data to the class.
    The next step is to add data to the new message. In order to add some data to the message, user must know which element in the message tree he wants to add. 
    The element he wants to add is identified by an XML path. The value of the element user wants to add may be a String, a Boolean or a complex type that is described in the SEPA type catalog.
    
    So to enter some data into the message, user must call the following method of the previously instantiated object
    where the path argument is a String identifying the element to add and the value argument is an Object.
    ```java
    message.setElement("path/to/field", "value");
    ```  
    Every path of all SEPA messages starts with `/Document/MessageObjectName` and so we integrated this prefix in every message in order to make the method simpler. 
    In other words the paths must be entererd without the prefix, `/GrpHdr` instead of `/Document/FIToFIPmtStsRptS2/GrpHdr`
        
    User can also work with the XSD defined classes that represent a tag. e.g. S2SCTGroupHeader37 for GrpHdr tag.
    ```java
    message.getRootMessage().setGrpHdr(new S2SCTGroupHeader37()); // setGrpHdr() method accepts gr.datamation.sepa.types.eba.pacs002.S2SCTGroupHeader37 objects
    ```
    
1. ##### Validate the message.
    After building a SEPA message using the appropriate class, user may want to validate this message. 
    Of course validation is not mandatory but is the only way to prove that the message is correct.  
    
    Validation is performed automatically after message parsing (using parseAndValidate() method) or 
    by calling the validate() method. This is internally a two step process:
    
    - Validation against the schema of the message in order to ensure that the message is a well-formed instance of it.  
    - Validation against any Validation Rule as described for that message by the SEPA rules.  
    
    The validate() method fills the message variable errors object with any issue found during validation.
    
    ```java
    messageObject.validate();
   
    messageObject.hasValidationErrors(); // boolean response for checking if any errors occured
   
    List<String> errors = message.getErrors() // get the list of the message's errors  
    ```

1. ##### Extracting data from the message.
   Another matter is to access the data of the message. In order to do this, user must select which element in the message tree he wants to access. 
   So to extract some data off the message, user must call the following method of the previously instantiated object
   where the path argument is a String identifying the element.
   ```java
   Object data = message.getElement("/GrpHdr"); // message.getElement("path/to/field");
   ```  
           
   User can also work with the XSD defined classes that represent a tag. e.g. S2SCTGroupHeader37 for GrpHdr tag.
   ```java
   S2SCTGroupHeader37 data = message.getRootMessage().getGrpHdr(); // getGrpHdr() method returns gr.datamation.sepa.types.eba.pacs002.S2SCTGroupHeader37 object
   ```

### Code Samples

In this project you can see code for all the basic manipulation of an SEPA message, like:
- [Parse a valid file pacs.002](src/main/java/com/paymentcomponents/sepa/ParseValidFilePacs002.java)
- [Parse a valid text pacs.002](src/main/java/com/paymentcomponents/sepa/ParseValidPacs002.java)
- [Parse an invalid pacs.002](src/main/java/com/paymentcomponents/sepa/ParseInvalidPacs002.java) and get the syntax and network validations errors
- [Build a valid pacs.002](src/main/java/com/paymentcomponents/sepa/BuildValidPacs002.java)


### More features are included in the paid version like

- #### [Support for all available messages](https://github.com/Payment-Components/demo-sepa/blob/main/README.md#message-appendix)  
- #### [Resolve Transactions](https://gist.github.com/apolichronopoulos/74db3c490cd30dcdd884af5a10373dce)  
  In addition to the getElement methods, the `resolveTransaction()` or `resolveTransactions()` methods can be used to greatly simplify the parsing of messages.
  The following uses a FIToFICustomerCreditTransfer (`pacs.008.001.02`) message as an example. These methods return vectors of vectors of LeafInfo objects.
  LeafInfo objects have 3 fields:
  
  |Node|Leaf field|Example|
  |---|---|---|
  |0|fieldPath|/CdtTrfTxInf/PmtId|
  |1|fieldCd|InstrId|
  |2|value|DEUTDE0920000891|
- #### [Building Reply Messages](https://gist.github.com/apolichronopoulos/27892018e62f36344044987daa299bc0)  
  The Reply Message (R-Transactions, RT) is a SEPA message that is used to provide an automatic response to a message. 
  An RT can be constructed and modified according to the following instructions. 
  This feature is designed to simplify the creation of an RT since it can be automatically constructed by following the specification of the initiating message.
  The construction of the RT is defined by an internal mechanism whose prime purpose is to:  
  
  1. Transfer "common" data blocks (or reusable information) from the original message to the RT.
  2. Create the required message blocks depending on whether the RT message applies to all the transactions of the original message or not.
  
  The resulting RT contains all the "reusable" data extracted from the initial message and only requires the addition of any further necessary data to complete the creation of the RT.
  _Note that: until all mandatory data has been added, the RT object will not be able to produce a valid message._   
  
  The construction of an RT is implemented in the class of the respective SEPA message, via the `autoReply(CoreMessage, List<MsgReplyInfo>)` method.
  The first parameter specifies the return type of the RT (since an existing message could have more than one possible response types, [RT Message Support List](https://github.com/Payment-Components/demo-sepa/blob/main/README.md#rt-message-support-list)).
  The second parameter specifies the necessary information to build the RT message using our `MsgReplyInfo` object.  
  For example, assuming that:
  
  - the variable named `pacs008message` holds a valid FIToFICustomerCreditTransfer message (pacs.008.001.02).
  - the variable named `pacs004message` with an empty PaymentReturn message (pacs.004.001.02) which is one of the possible replies of the FIToFICustomerCreditTransfer message
  - the RT does not refer to all transactions declared in the FIToFICustomerCreditTransfer. 
  
  Then, the calling of the autoReply method using the following code will return a PaymentReturn object.
  ```java
  PaymentReturn pacs004message = new PaymentReturn();
  Vector<MsgReplyInfo> msgReplyInfo = new Vector<MsgReplyInfo>(); 
  msgReplyInfo.add(new MsgReplyInfo("TXID", new ReasonCode(ReasonCode.CD, "AC01", null, null), MsgReplyInfo.MSGID_BASED, "RETURNID", "", new Date()));
  pacs004message = pacs008message.autoReply(pacs004message, msgReplyInfo);
  ```
- #### [SEPA Instant](https://gist.github.com/PaymentComponents/3fdab3b73885450a65b24c889c93c974)  
   For available SEPA Instant messages please advice [this](#EPC---SEPA-INSTANT) table.  
   For available SEPA Instant RT messages please advice [this](#RT-Message-Support-List) table.

- #### [P27](https://gist.github.com/GeorgeAnt/8ee113ca7528af0017b7cdfdf9204b4e)  
  For available P27 messages please advice [this](#P27---NORDIC-CREDIT-TRANSFERS) table.  
  For available P27 RT messages please advice [this](#RT-Message-Support-List) table.
    

## Message Appendix

##### EBA - SEPA CREDIT TRANSFERS

| Message Name                                      | Msg ID                | Schema ID         |
|---------------------------------------------------|-----------------------|-------------------|
| FIToFICustomerCreditTransfer                      | FIToFICstmrCdtTrf     | pacs.008.001.02   |
| FIToFIPaymentStatusReport                         | FIToFIPmtStsRptS2     | pacs.002.001.03S2 |
| PaymentReturn                                     | PmtRtr                | pacs.004.001.02   |
| FIToFIPaymentInstantStatusInquiryForInvestigation | FIToFIPmtStsReq       | pacs.028.001.01   |
| CustomerCreditTransferInitiation                  | CstmrCdtTrfInitn      | pain.001.001.03   |
| CustomerPaymentStatusReport                       | CstmrPmtStsRpt        | pain.002.001.03   |
| ClaimNonReceipt                                   | ClmNonRct             | camt.027.001.06   |
| ResolutionOfInvestigation                         | RsltnOfInvstgtn       | camt.029.001.03   |
| ResolutionOfInvestigation08                       | RsltnOfInvstgtn       | camt.029.001.08   |
| BankToCustomerStatementV4                         | BkToCstmrStmt         | camt.053.001.04   |
| BankToCustomerDebitCreditNotificationV2           | BkToCstmrDbtCdtNtfctn | camt.054.001.02   |
| BankToCustomerDebitCreditNotificationV3           | BkToCstmrDbtCdtNtfctn | camt.054.001.03   |
| RequestToModifyPayment                            | ReqToModfyPmt         | camt.087.001.05   |
| FIToFIPaymentCancellationRequest                  | FIToFIPmtCxlReq       | camt.056.001.01   |
| SCTCcfBulkCreditTransfer                          | SCTCcfBlkCredTrf      | SCTCcfBlkCredTrf  |
| SCTCvfBulkCreditTransfer                          | SCTCvfBlkCredTrf      | SCTCvfBlkCredTrf  |
| SCTIcfBulkCreditTransfer                          | SCTIcfBlkCredTrf      | SCTIcfBlkCredTrf  |
| SCTIqfBulkCreditTransfer                          | SCTIqfBlkCredTrf      | SCTIqfBlkCredTrf  |
| SCTScfBulkCreditTransfer                          | SCTScfBlkCredTrf      | SCTScfBlkCredTrf  |
| SCTOqfBulkCreditTransfer                          | SCTOqfBlkCredTrf      | SCTOqfBlkCredTrf  |
| SCTQvfBulkCreditTransfer                          | SCTQvfBlkCredTrf      | SCTQvfBlkCredTrf  |
| SCTPcfBulkCreditTransfer                          | SCTPcfBlkCredTrf      | SCTPcfBlkCredTrf  |

##### EPC - SEPA CREDIT TRANSFERS

| Message Name                                      | Msg ID            | Schema ID       |
|---------------------------------------------------|-------------------|-----------------|
| PaymentStatusReport                               | FIToFIPmtStsRpt   | pacs.002.001.03 |
| PaymentReturn                                     | PmtRtr            | pacs.004.001.02 |
| FIToFICustomerCreditTransfer                      | FIToFICstmrCdtTrf | pacs.008.001.02 |
| FIToFIPaymentInstantStatusInquiryForInvestigation | FIToFIPmtStsReq   | pacs.028.001.01 |
| ClaimNonReceipt                                   | ClmNonRct         | camt.027.001.06 |
| ResolutionOfInvestigation                         | RsltnOfInvstgtn   | camt.029.001.03 |
| ResolutionOfInvestigation08                       | RsltnOfInvstgtn   | camt.029.001.08 |
| FIToFIPaymentCancellationRequest                  | FIToFIPmtCxlReq   | camt.056.001.01 |
| RequestToModifyPayment                            | ReqToModfyPmt     | camt.087.001.05 |

##### DIAS - SEPA CREDIT TRANSFERS

| Message Name                     | Msg ID            | Schema ID       |
|----------------------------------|-------------------|-----------------|
| ClaimNonReceipt                  | ClmNonRct         | camt.027.001.06 |
| ResolutionOfInvestigation        | RsltnOfInvstgtn   | camt.029.001.03 |
| ResolutionOfInvestigation08      | RsltnOfInvstgtn   | camt.029.001.08 |
| FIToFIPaymentCancellationRequest | FIToFIPmtCxlReq   | camt.056.001.01 |
| RequestToModifyPayment           | ReqToModfyPmt     | camt.087.001.05 |
| FIToFIPaymentStatusReport        | FIToFIPmtStsRpt   | pacs.002.001.03 |
| PaymentReturn                    | PmtRtr            | pacs.004.001.02 |
| FIToFICustomerCreditTransfer     | FIToFICstmrCdtTrf | pacs.008.001.02 |
| FIToFIPaymentStatusRequest       | FIToFIPmtStsReq   | pacs.028.001.01 |
| DCTBulkCreditTransfer            | DIASFileHdr       | DIASSCTFH       |
   
##### SIBS - SEPA CREDIT TRANSFERS   

| Message Name                                      | Msg ID            | Schema ID          |
|---------------------------------------------------|-------------------|--------------------|
| ClaimNonReceipt                                   | ClmNonRct         | camtx.027.001.06   |
| ResolutionOfInvestigation                         | RsltnOfInvstgtn   | camtx.029.001.03   |
| ResolutionOfInvestigation08                       | RsltnOfInvstgtn   | camtx.029.001.08   |
| FIToFIPaymentCancellationRequest                  | FIToFIPmtCxlReq   | camtx.056.001.01   |
| RequestToModifyPayment                            | ReqToModfyPmt     | camtx.087.001.05   |
| FIToFIPaymentStatusReport                         | FIToFIPmtStsRptS2 | pacsx.002.001.03S2 |
| PaymentReturn                                     | PmtRtr            | pacsx.004.001.02   |
| FIToFICustomerCreditTransfer                      | FIToFICstmrCdtTrf | pacsx.008.001.02   |
| FIToFIPaymentInstantStatusInquiryForInvestigation | FIToFIPmtStsReq   | pacsx.028.001.01   |
| SCTCcxBulkCreditTransfer                          | SCTCcfBlkCredTrf  | SCTCcxBlkCredTrf   |
| SCTCvxBulkCreditTransfer                          | SCTCvfBlkCredTrf  | SCTCvxBlkCredTrf   |
| SCTIcxBulkCreditTransfer                          | SCTIcfBlkCredTrf  | SCTIcxBlkCredTrf   |
| SCTScxBulkCreditTransfer                          | SCTScfBlkCredTrf  | SCTScxBlkCredTrf   |
| SCTIqxBulkCreditTransfer                          | SCTIqfBlkCredTrf  | SCTIqxBlkCredTrf   |
| SCTOqxBulkCreditTransfer                          | SCTOqfBlkCredTrf  | SCTOqxBlkCredTrf   |
| SCTQvxBulkCreditTransfer                          | SCTQvfBlkCredTrf  | SCTQvxBlkCredTrf   |

##### EBA - SEPA DIRECT DEBITS

| Message Name                     | Msg ID             | Schema ID         |
|----------------------------------|--------------------|-------------------|
| FIToFIPaymentCancellationRequest | FIToFIPmtCxlReq    | camt.056.001.01   |
| FIToFIPaymentStatusReport        | FIToFIPmtStsRpt    | pacs.002.001.03   |
| FIToFICustomerDirectDebit        | FIToFICstmrDrctDbt | pacs.003.001.02   |
| PaymentReturn                    | PmtRtr             | pacs.004.001.02   |
| FIToFIPaymentReversal            | FIToFIPmtRvsl      | pacs.007.001.02   |
| MPEDDIdfBlkDirDeb                | MPEDDIdfBlkDirDeb  | MPEDDIdfBlkDirDeb |

##### EPC - SEPA DIRECT DEBITS

| Message Name                  | Msg ID             | Schema ID       |
|-------------------------------|--------------------|-----------------|
| FIToFIPaymentStatusReport     | FIToFIPmtStsRpt    | pacs.002.001.03 |
| FIToFICustomerDirectDebit     | FIToFICstmrDrctDbt | pacs.003.001.02 |
| PaymentReturn                 | PmtRtr             | pacs.004.001.02 |
| FIToFIPaymentReversal         | FIToFIPmtRvsl      | pacs.007.001.02 |
| CustomerDirectDebitInitiation | CstmrDrctDbtInitn  | pain.008.001.02 |

##### SIBS - SEPA DIRECT DEBITS

| Message Name                     | Msg ID             | Schema ID         |
|----------------------------------|--------------------|-------------------|
| FIToFIPaymentCancellationRequest | FIToFIPmtCxlReq    | camt.056.001.01   |
| FIToFICustomerDirectDebit        | FIToFICstmrDrctDbt | pacs.003.001.02   |
| FIToFIPaymentReversal            | FIToFIPmtRvsl      | pacs.007.001.02   |
| FIToFIPaymentStatusReport        | FIToFIPmtStsRpt    | pacs.002.001.03   |
| FIToFIPaymentStatusReportS2      | FIToFIPmtStsRpt    | pacs.002.001.03S2 |
| PaymentReturn                    | PmtRtr             | pacs.004.001.02   |
| MPEDDCdxBulkDirectDebit          | MPEDDCdxBlkDirDeb  | MPEDDCdxBlkDirDeb |
| MPEDDDnxBulkDirectDebit          | MPEDDDnxBlkDirDeb  | MPEDDDnxBlkDirDeb |
| MPEDDDrxBulkDirectDebit          | MPEDDDrxBlkDirDeb  | MPEDDDrxBlkDirDeb |
| MPEDDDvxBulkDirectDebit          | MPEDDDvfBlkDirDeb  | MPEDDDvxBlkDirDeb |
| MPEDDIdxBulkDirectDebit          | MPEDDIdxBlkDirDeb  | MPEDDIdxBlkDirDeb |
| MPEDDIrxBulkDirectDebit          | MPEDDIrxBlkDirDeb  | MPEDDIrxBlkDirDeb |
| MPEDDRsxBulkDirectDebit          | MPEDDRsfBlkDirDeb  | MPEDDRsxBlkDirDeb |
| MPEDDSdxBulkDirectDebit          | MPEDDSdfBlkDirDeb  | MPEDDSdxBlkDirDeb |

##### EPC - SEPA INSTANT

| Message Name                     | Msg ID            | Schema ID           | EpcType Enumeration                                                                                                                |
|----------------------------------|-------------------|---------------------|------------------------------------------------------------------------------------------------------------------------------------|
| FIToFICustomerCreditTransfer     | FIToFICstmrCdtTrf | pacs.008.001.02     | CREDIT_TRANSFER                                                                                                                    |
| FIToFIPaymentStatusReport        | FIToFIPmtStsRpt   | pacs.002.001.03     | NEGATIVE<br/>POSITIVE                                                                                                              |
| FIToFIPaymentStatusRequest       | FIToFIPmtStsReq   | pacs.028.001.01     | TRANSACTION_STATUS_INVESTIGATION<br/>REQUEST_FOR_STATUS_UPDATE_FOR_RECALL<br/>REQUEST_FOR_STATUS_UPDATE_FOR_RECALL_FROM_ORIGINATOR |
| PaymentReturn                    | PmtRtr            | pacs.004.001.02     | POSITIVE_RESPONSE_TO_RECALL<br/>POSITIVE_RESPONSE_TO_RECALL_FROM_ORIGINATOR                                                        |
| FIToFIPaymentCancellationRequest | FIToFIPmtCxlReq   | camt.056.001.01     | REQUEST_FOR_RECALL<br/>REQUEST_FOR_RECALL_FROM_ORIGINATOR                                                                          |
| ResolutionOfInvestigation        | RsltnOfInvstgtn   | camt.029.001.03     | NEGATIVE_RESPONSE_TO_RECALL<br/>NEGATIVE_RESPONSE_TO_RECALL_FROM_ORIGINATOR                                                        |

##### P27 - NORDIC CREDIT TRANSFERS

| Message Name                     | Msg ID            | Schema ID       |
|----------------------------------|-------------------|-----------------|
| FIToFICustomerCreditTransfer     | FIToFICstmrCdtTrf | pacs.008.001.02 |
| FIToFIPaymentStatusReport        | FIToFIPmtStsRpt   | pacs.002.001.03 |
| PaymentReturn                    | PmtRtr            | pacs.004.001.02 |
| FIToFIPaymentCancellationRequest | FIToFIPmtCxlReq   | camt.056.001.01 |
| ResolutionOfInvestigation        | RsltnOfInvstgtn   | camt.029.001.03 |
| ResolutionOfInvestigation08      | RsltnOfInvstgtn   | camt.029.001.08 |
| RequestToModifyPayment           | ReqToModfyPmt     | camt.087.001.05 |
| ClaimNonReceipt                  | ClmNonRct         | camt.027.001.06 |


## RT Message Support List

##### FIToFICustomerCreditTransfer (pacs.008.001.02) autoReply return objects

| Message Name                                      | Schema ID       | Variations support                     |
|---------------------------------------------------|-----------------|----------------------------------------|
| PaymentReturn                                     | pacs.004.001.02 | eba, epc, sibs, dias, epc-instant, p27 |
| FIToFIPaymentInstantStatusInquiryForInvestigation | pacs.028.001.01 | eba, epc, sibs, dias, epc-instant      |
| ClaimNonReceipt                                   | camt.027.001.06 | eba, epc, sibs, dias, p27              |
| ResolutionOfInvestigation                         | camt.029.001.03 | eba, epc, sibs, dias, epc-instant, p27 |
| ResolutionOfInvestigation08                       | camt.029.001.08 | eba, epc, sibs, dias , p27             |
| FIToFIPaymentCancellationRequest                  | camt.056.001.01 | eba, epc, sibs, dias, epc-instant, p27 |
| RequestToModifyPayment                            | camt.087.001.05 | eba, epc, sibs, dias, p27              |
| FIToFIPaymentStatusReport                         | pacs.002.001.03 | epc-instant                            |

##### FIToFICustomerDirectDebit (pacs.003.001.02) autoReply return objects

| Message Name                     | Schema ID       | Variations support |
|----------------------------------|-----------------|--------------------|
| PaymentReturn                    | pacs.004.001.02 | eba, epc, sibs     |
| FIToFIPaymentCancellationRequest | camt.056.001.01 | epc, sibs          |
| FIToFIPaymentReversal            | pacs.007.001.02 | epc, sibs          |
