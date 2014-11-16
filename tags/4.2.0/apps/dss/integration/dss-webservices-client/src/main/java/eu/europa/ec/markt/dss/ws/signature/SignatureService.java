
package eu.europa.ec.markt.dss.ws.signature;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * The signing web service allows to create a new signature or to extend existing one. Different forms of signature:XAdES, CAdES, PAdES, ASiC-S are accepted.
 *  The digital signature of a document in a web environment is performed in three steps:
 *  1. Creating a byte stream representing the data to be signed.
 *  2. Hashing of the data previously obtained and its encryption. This step is performed locally (not by the web service).
 *  3. The creation of the envelope containing all the elements of a digital signature.
 *  The process is controlled by a set of parameters.
 * 
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "SignatureService", targetNamespace = "http://ws.dss.markt.ec.europa.eu/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SignatureService {


    /**
     * This method Extends the level of the signature(s) linked to the given document. It takes two parameters: the document with the signature(s), the set of driving parameters.
     * 
     * @param signedDocument
     * @param wsParameters
     * @return
     *     returns eu.europa.ec.markt.dss.ws.signature.WsDocument
     * @throws DSSException_Exception
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "")
    @RequestWrapper(localName = "extendSignature", targetNamespace = "http://ws.dss.markt.ec.europa.eu/", className = "eu.europa.ec.markt.dss.ws.signature.ExtendSignature")
    @ResponseWrapper(localName = "extendSignatureResponse", targetNamespace = "http://ws.dss.markt.ec.europa.eu/", className = "eu.europa.ec.markt.dss.ws.signature.ExtendSignatureResponse")
    public WsDocument extendSignature(
        @WebParam(name = "signedDocument", targetNamespace = "")
        WsDocument signedDocument,
        @WebParam(name = "wsParameters", targetNamespace = "")
        WsParameters wsParameters)
        throws DSSException_Exception
    ;

    /**
     * This method creates the signature containing the provided encrypted hash value and all requested elements. It requests three parameters: the document to sign, the set of driving parameters and the encrypted hash value of bytes that need to be protected by the digital signature.
     * 
     * @param document
     * @param signatureValue
     * @param wsParameters
     * @return
     *     returns eu.europa.ec.markt.dss.ws.signature.WsDocument
     * @throws DSSException_Exception
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "")
    @RequestWrapper(localName = "signDocument", targetNamespace = "http://ws.dss.markt.ec.europa.eu/", className = "eu.europa.ec.markt.dss.ws.signature.SignDocument")
    @ResponseWrapper(localName = "signDocumentResponse", targetNamespace = "http://ws.dss.markt.ec.europa.eu/", className = "eu.europa.ec.markt.dss.ws.signature.SignDocumentResponse")
    public WsDocument signDocument(
        @WebParam(name = "document", targetNamespace = "")
        WsDocument document,
        @WebParam(name = "wsParameters", targetNamespace = "")
        WsParameters wsParameters,
        @WebParam(name = "signatureValue", targetNamespace = "")
        byte[] signatureValue)
        throws DSSException_Exception
    ;

    /**
     * This method retrieves the stream of data that need to be hashed and encrypted. It takes two parameters: the document to sign and the set of parameters.
     * 
     * @param document
     * @param wsParameters
     * @return
     *     returns byte[]
     * @throws DSSException_Exception
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "")
    @RequestWrapper(localName = "getDataToSign", targetNamespace = "http://ws.dss.markt.ec.europa.eu/", className = "eu.europa.ec.markt.dss.ws.signature.GetDataToSign")
    @ResponseWrapper(localName = "getDataToSignResponse", targetNamespace = "http://ws.dss.markt.ec.europa.eu/", className = "eu.europa.ec.markt.dss.ws.signature.GetDataToSignResponse")
    public byte[] getDataToSign(
        @WebParam(name = "document", targetNamespace = "")
        WsDocument document,
        @WebParam(name = "wsParameters", targetNamespace = "")
        WsParameters wsParameters)
        throws DSSException_Exception
    ;

}
