import ee.zacc.einvoice.gen.EInvoice;
import ee.zacc.einvoice.gen.Invoice;
import ee.zacc.einvoice.gen.InvoiceParties;
import ee.zacc.einvoice.gen.SellerPartyRecord;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringWriter;

public class SerializationTest {

    @Test
    public void parseInvoiceAllFields() throws Exception {
        File file = new File("src/test/data/EARVE_1.2_all_fields.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(EInvoice.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        EInvoice invoice = (EInvoice) jaxbUnmarshaller.unmarshal(file);

        Assert.assertEquals("Verifying that some field is filled", "NormalText", invoice.getInvoice().get(0).getInvoiceInformation().getContractNumber());
    }

    @Test
    public void parseInvoiceMandatoryFields() throws Exception {
        File file = new File("src/test/data/EARVE_1.2_mandatory_fields.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(EInvoice.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        EInvoice invoice = (EInvoice) jaxbUnmarshaller.unmarshal(file);

        Assert.assertEquals("TESTOSTJA AS", invoice.getInvoice().get(0).getPaymentInfo().getContent().get(4).getValue());
    }

    @Test
    public void serializeInvoice() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(EInvoice.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();

        EInvoice einvoice = new EInvoice();

        Invoice invoice = new Invoice();
        invoice.setInvoiceId("");
        invoice.setInvoiceParties(new InvoiceParties());
        invoice.getInvoiceParties().setSellerParty(new SellerPartyRecord());
        invoice.getInvoiceParties().getSellerParty().setName("Mati OÜ");
        invoice.getInvoiceParties().getSellerParty().setRegNumber("82937498");

        einvoice.getInvoice().add(invoice);

        jaxbMarshaller.marshal(einvoice, sw);
    }

}
