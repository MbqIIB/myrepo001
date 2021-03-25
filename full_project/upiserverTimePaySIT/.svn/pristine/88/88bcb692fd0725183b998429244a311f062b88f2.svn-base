package com.npst.upiserver.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Collections;
import java.util.TreeSet;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;

public class DigitalSignUtil {
	private static final Logger log = LoggerFactory.getLogger(DigitalSignUtil.class.getName());
	private static PrivateKey privateKey = null;
	private static PublicKey publicKey = null;
	private static int cntCheck;
	static {
		try {
			File signerFile = new File(Constant.P12FILE);
			FileInputStream is = new FileInputStream(signerFile);
			KeyStore keystore = KeyStore.getInstance(ConstantI.PKCS12);
			/* Information for certificate to be generated */

			/* getting the key */
			keystore.load(is, Constant.password.toCharArray());
			privateKey = (PrivateKey) keystore.getKey(Constant.alias, Constant.password.toCharArray());
			/* Get certificate of public key */
			java.security.cert.Certificate cert = getCertificate(Constant.CERFILESIGN);
			publicKey = cert.getPublicKey();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, DigitalSignUtil.class);
		}
	}

	public static String convertXMLToString(Document doc) {
		String result = "";
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer trans = null;
		try {
			trans = transFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			log.error("error :{}", e);
		}
		try {
			StringWriter writer = new StringWriter();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, ConstantI.YES_SMALL);
			trans.transform(new DOMSource(doc), new StreamResult(writer));
			result = writer.getBuffer().toString().replaceAll(ConstantI.CONST_ESCAPE_CHARS, ConstantI.CONST_BLANK);
		} catch (TransformerException e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, DigitalSignUtil.class);
		}
		return result;
	}

	public static Document generateXMLDigitalSignature(Document doc, String signXML) throws Exception {
		log.trace("");
		// Get the XML Document object
		// Create XML Signature Factory
		XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance(ConstantI.DOM);
		DOMSignContext domSignCtx = new DOMSignContext(privateKey, doc.getDocumentElement());
		Reference ref = null;
		SignedInfo signedInfo = null;
		ref = xmlSigFactory
				.newReference("", xmlSigFactory.newDigestMethod(DigestMethod.SHA256, null),
						Collections.singletonList(
								xmlSigFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
						null, null);
		log.debug("xmlSigFactory.getProvider(): {} ", xmlSigFactory.getProvider());
		signedInfo = xmlSigFactory.newSignedInfo(
				xmlSigFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
						(C14NMethodParameterSpec) null),
				xmlSigFactory.newSignatureMethod(ConstantI.CONST_ALGORITHM, (SignatureMethodParameterSpec) null),
				Collections.singletonList(ref));
		// Pass the Public Key File Path
		KeyInfo keyInfo = getKeyInfo(xmlSigFactory);
		// Create a new XML Signature
		XMLSignature xmlSignature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo);
		// Sign the document
		xmlSignature.sign(domSignCtx);

		// Store the digitally signed document inta a location
		return doc;
	}

	private static Certificate getCertificate(String file) throws Exception {
		log.trace("");
		CertificateFactory cf = CertificateFactory.getInstance(ConstantI.X509);
		InputStream is = new FileInputStream(new File(file));
		InputStream caInput = new BufferedInputStream(is);
		Certificate ca;
		try {
			ca = cf.generateCertificate(caInput);
			return ca;
		} finally {
			try {
				caInput.close();
			} catch (IOException e) {
				ErrorLog.sendError(e, DigitalSignUtil.class);
			}
			try {
				is.close();
			} catch (IOException e) {
				ErrorLog.sendError(e, DigitalSignUtil.class);
			}
		}
	}

	private static KeyInfo getKeyInfo(XMLSignatureFactory xmlSigFactory) throws Exception {
		log.trace("");
		KeyInfo keyInfo = null;
		KeyValue keyValue = null;
		KeyInfoFactory keyInfoFact = xmlSigFactory.getKeyInfoFactory();
		keyValue = keyInfoFact.newKeyValue(publicKey);
		keyInfo = keyInfoFact.newKeyInfo(Collections.singletonList(keyValue));
		return keyInfo;
	}

	public static Document getXmlDocument(String xmlString) throws Exception {
		log.trace("");
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		doc = dbf.newDocumentBuilder().parse(new FileInputStream(xmlString));
		return doc;
	}

	public static Document getXmlDocumentFromString(String xmlString) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			doc = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(xmlString)));
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
			ErrorLog.sendError(ex, DigitalSignUtil.class);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			ErrorLog.sendError(ex, DigitalSignUtil.class);
		} catch (SAXException ex) {
			ex.printStackTrace();
			ErrorLog.sendError(ex, DigitalSignUtil.class);
		} catch (IOException ex) {
			ex.printStackTrace();
			ErrorLog.sendError(ex, DigitalSignUtil.class);
		}
		return doc;
	}

	public static void isXmlDigitalSignatureValid(Document doc) throws Exception {

		NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, ConstantI.CONST_SIGN);
		if (nl.getLength() == 0) {
			throw new Exception("No XML Digital Signature Found, document is discarded");
		}
		DOMValidateContext valContext = new DOMValidateContext(publicKey, nl.item(0));
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance(ConstantI.DOM);
		XMLSignature signature = fac.unmarshalXMLSignature(valContext);
		log.info("XML Signature is valid : {}", signature.validate(valContext));

	}

	public static String signXML(String unsignedXMLString) throws Exception {
		log.trace(unsignedXMLString);
		Document unsignedXML = getXmlDocumentFromString(unsignedXMLString);
		log.debug("privatekey= {}", privateKey);
		log.debug("publicKey= {} ", publicKey);

		XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance(ConstantI.DOM);
		DOMSignContext domSignCtx = new DOMSignContext(privateKey, unsignedXML.getDocumentElement());
		Reference ref = null;
		SignedInfo signedInfo = null;
		try {
			ref = xmlSigFactory.newReference("", xmlSigFactory.newDigestMethod(DigestMethod.SHA256, null),
					Collections.singletonList(
							xmlSigFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
					null, null);
			log.debug("xmlSigFactory.getProvider(): {}", xmlSigFactory.getProvider());
			signedInfo = xmlSigFactory.newSignedInfo(
					xmlSigFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
							(C14NMethodParameterSpec) null),
					xmlSigFactory.newSignatureMethod(ConstantI.CONST_ALGORITHM, (SignatureMethodParameterSpec) null),
					Collections.singletonList(ref));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (InvalidAlgorithmParameterException ex) {
			ex.printStackTrace();
		}
		// Pass the Public Key File Path
		KeyInfo keyInfo = getKeyInfo(xmlSigFactory); // Create a new XML
		// Signature
		XMLSignature xmlSignature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo);
		try {
			// Sign the document
			xmlSignature.sign(domSignCtx);
		} catch (MarshalException ex) {
			ErrorLog.sendError("ERROR IN SIGNING XML_DOC ,ALERT IMPORTANCE HIGH ",ex.getMessage(), DigitalSignUtil.class);
			ErrorLog.sendError(ex, DigitalSignUtil.class);
			ex.printStackTrace();
		} catch (XMLSignatureException ex) {
			ErrorLog.sendError("ERROR IN SIGNING XML_DOC ,ALERT IMPORTANCE HIGH ",ex.getMessage(), DigitalSignUtil.class);
			ErrorLog.sendError(ex, DigitalSignUtil.class);
			ex.printStackTrace();
		}
		isXmlDigitalSignatureValid(unsignedXML);
		return convertXMLToString(unsignedXML);
	}
	
	public static String signMandate(String  mandateBaseString) {
		log.error("**********comming to signMandate ");
		try {
			TreeSet<String> algorithms = new TreeSet<>();
			for (Provider provider : Security.getProviders())
			    for (Service service : provider.getServices())
			        if (service.getType().equals("Signature"))
			            algorithms.add(service.getAlgorithm());
			for (String algorithm : algorithms)
				log.info(algorithm);
			
			log.debug("privatekey= {}", privateKey);
			Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(mandateBaseString.getBytes());
            byte[] mandateSignature =  signature.sign();
            log.error("##############",java.util.Base64.getEncoder().encodeToString(mandateSignature));
            return  java.util.Base64.getEncoder().encodeToString(mandateSignature);
		} catch (Exception e) {
			log.error("error in signing signMandate  {}", e);
			//ErrorLogErrorLog.sendError("ERROR IN SIGNING signMandate ,ALERT IMPORTANCE HIGH ",e.getMessage(), DigitalSignUtil.class);
			//ErrorLogErrorLog.sendError(e, DigitalSignUtil.class);
		}
		return null;
	}
}
