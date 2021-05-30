package model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class BDDConector {

    private static Connection singleton;

    private BDDConector() throws IOException, ClassNotFoundException, SQLException {

        Properties param = new Properties();
        URL urlFichierProp = BDDConector.class.getResource("BDparam.properties");

        if (urlFichierProp == null) {
            throw new IOException("Fichier " + "BDparam" + " pas trouve !");
        }

        BufferedInputStream bis = null;

        try {

            bis = new BufferedInputStream(urlFichierProp.openStream());
            param.load(bis);
            String driver = param.getProperty("driver");
            String url = param.getProperty("url");
            String utilisateur = param.getProperty("utilisateur");
            String mdp = param.getProperty("mdp");
            Class.forName(driver);
            singleton = DriverManager.getConnection(url, utilisateur, mdp);

        }

        finally {

            if (bis != null) {
                bis.close();
            }

        }
    }

    public static Connection getInstance() throws IOException, ClassNotFoundException, SQLException {
        if (singleton == null) {
            new BDDConector();
        }
        return singleton;
    }

}
