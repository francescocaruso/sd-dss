/*
 * DSS - Digital Signature Services
 *
 * Copyright (C) 2013 European Commission, Directorate-General Internal Market and Services (DG MARKT), B-1049 Bruxelles/Brussel
 *
 * Developed by: 2013 ARHS Developments S.A. (rue Nicolas Bové 2B, L-1253 Luxembourg) http://www.arhs-developments.com
 *
 * This file is part of the "DSS - Digital Signature Services" project.
 *
 * "DSS - Digital Signature Services" is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * DSS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * "DSS - Digital Signature Services".  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.europa.ec.markt.dss.signature.xades;

import org.apache.xml.security.Init;

import eu.europa.ec.markt.dss.exception.DSSException;
import eu.europa.ec.markt.dss.parameter.SignatureParameters;
import eu.europa.ec.markt.dss.signature.DSSDocument;
import eu.europa.ec.markt.dss.validation102853.CertificateVerifier;

/**
 * Contains B level baseline profile for XAdES signature.
 *
 * @version $Revision: 2688 $ - $Date: 2013-10-02 16:48:13 +0200 (Wed, 02 Oct 2013) $
 */

public class XAdESLevelBaselineB {

    static {

        Init.init();
    }

    /**
     * Reference to the object in charge of certificates validation
     */
    CertificateVerifier certificateVerifier;

    /**
     * The default constructor for XAdESLevelBaselineB.
     *
     * @param certificateVerifier
     */
    public XAdESLevelBaselineB(final CertificateVerifier certificateVerifier) {

        this.certificateVerifier = certificateVerifier;
    }

    /**
     * Returns the canonicalized <ds:SignedInfo> XML segment under the form of InputStream
     *
     *
     * @param dssDocument The original dssDocument to sign.
     * @param params      The set of parameters relating to the structure and process of the creation or extension of the
     *                    electronic signature.
     * @return bytes
     */
    public byte[] getDataToSign(final DSSDocument dssDocument, final SignatureParameters params) throws DSSException {

        final SignatureBuilder signatureBuilder = SignatureBuilder.getSignatureBuilder(params, dssDocument);
        params.getContext().setBuilder(signatureBuilder);
        final byte[] signedInfoBytes = signatureBuilder.build();
        return signedInfoBytes;
    }

    /*
     * Adds the signature value to the signature
     *
     * @see eu.europa.ec.markt.dss.signature.SignatureLevel#signDocument(eu.europa.ec.markt.dss.signature.Document,
     * eu.europa.ec.markt.dss.signature.SignatureParameters, byte[])
     */
    public DSSDocument signDocument(final DSSDocument document, final SignatureParameters parameters, final byte[] signatureValue) throws DSSException {

        SignatureBuilder builder = parameters.getContext().getBuilder();
        if (builder != null) {

            builder = parameters.getContext().getBuilder();
        } else {

            builder = SignatureBuilder.getSignatureBuilder(parameters, document);
        }
        final DSSDocument dssDocument = builder.signDocument(signatureValue);
        parameters.getContext().setBuilder(builder);
        return dssDocument;
    }
}
