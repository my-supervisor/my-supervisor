package com.supervisor.billing.impl;

import com.supervisor.billing.MobileMoney;
import com.supervisor.billing.Order;
import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.PaymentReceiptState;
import com.supervisor.billing.PaymentRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class Momo extends MobileMoneyBase implements MobileMoney {

	public Momo(PaymentMethod method) throws IOException {
		super(method);
	}

	@Override
	public PaymentReceiptState check(PaymentReceipt payment) throws IOException {
		return payment.state();
	}

	@Override
	protected PaymentReceipt proceed(PaymentRequest paymentRequest, Map<String, String> metadata) throws IOException {
		
		final String fullNumber = metadata.get("FullNumber");
		final Order order = paymentRequest.order();
		final String object = String.format("Paiement par %s (Numéro %s) du bon de commande N° %s", name(), fullNumber, order.reference());
		final PaymentReceipt receipt = generatePayment(paymentRequest, object, metadata);
		
		//Code
		URL obj = new URL("https://billmap.mtn.ci/WebServices/BillPayment.asmx/ProcessOnlinePayment_V1.4");
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(String.format("Code=%s&Password=%s&MSISDN=%s&Reference=%s&Amount=%s&MetaData=MLK:%s", username(), password(), fullNumber, receipt.reference(), paymentRequest.amount(), receipt.reference()));
		
		wr.flush();
		wr.close();
		
		String inputLine;
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
		    response.append(inputLine);
		}
		in.close();
		String xml = response.toString();
		
		final Map<String, String> fields = new HashMap<>();
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xml));
			Document document = builder.parse(is);
			Element racine = document.getDocumentElement();
			NodeList entityNodes = racine.getChildNodes();
			final int nbEntityNodes = entityNodes.getLength();
			for (int i = 0; i < nbEntityNodes; i++) {
				if(entityNodes.item(i).getNodeType() != Node.ELEMENT_NODE)
					continue;
				
				Element node = (Element) entityNodes.item(i);
				fields.put(node.getNodeName(), node.getTextContent());			
			}
		} catch (Exception e) {
			throw new IOException(e);
		}
		
		if(fields.get("ResponseCode").equals("1000")) {
			metadata.put("TransactionId1", fields.get("BillMapTransactionId"));
			metadata.put("TransactionId2", fields.get("EWPTransactionId"));
			receipt.updateMetadata(metadata); 
			return receipt;
		}else {
			if(fields.get("ResponseCode").equals("529")) {
				throw new IllegalArgumentException("Fonds insuffisants !");
			}else {
				throw new IllegalArgumentException("La requête n'a pas pu être effectuée !");
			}
		}
		
	}

}
