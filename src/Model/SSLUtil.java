package Model;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class SSLUtil {

    public static void configureSSL() {
        try {
            InputStream cert = SSLUtil.class.getResourceAsStream("/Certs/up.railway.app.crt");

            // Crear un KeyStore con el certificado
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate caCert = (X509Certificate) cf.generateCertificate(cert);
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(null, null);
            ks.setCertificateEntry("caCert", caCert);

            // Configurar TrustManagerFactory con el KeyStore
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);

            // Crear el contexto SSL y configurar HttpsURLConnection
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
