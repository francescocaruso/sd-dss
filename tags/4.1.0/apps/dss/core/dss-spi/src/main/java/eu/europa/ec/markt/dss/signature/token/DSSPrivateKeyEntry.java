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

package eu.europa.ec.markt.dss.signature.token;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import eu.europa.ec.markt.dss.EncryptionAlgorithm;
import eu.europa.ec.markt.dss.exception.DSSException;

/**
 * Interface for a PrivateKey.
 *
 * @version $Revision: 3385 $ - $Date: 2014-01-27 08:45:34 +0100 (Mon, 27 Jan 2014) $
 */
public interface DSSPrivateKeyEntry {

    /**
     * @return the certificate
     */
    X509Certificate getCertificate();

    /**
     * @return the certificateChain
     */
    X509Certificate[] getCertificateChain();

    /**
     * Get the SignatureAlgorithm corresponding to the PrivateKey
     *
     * @return
     */
    EncryptionAlgorithm getEncryptionAlgorithm() throws DSSException;

    /**
     * Returns the encapsulated private key.
     *
     * @return
     */
    public PrivateKey getPrivateKey();

}