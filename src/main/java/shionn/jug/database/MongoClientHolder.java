package shionn.jug.database;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a- C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+
 *         G- e+++ h+ r- !y-
 */
public class MongoClientHolder extends Thread {

    private MongoClient client;
    private String database;

    private static class InstanceHolder {
        private final static MongoClientHolder INSTANCE = new MongoClientHolder();
    }

    private MongoClientHolder() {
        initialize();
    }

    public static MongoClientHolder get() {
        return InstanceHolder.INSTANCE;
    }

    private void initialize() {
        try {
            Properties conf = loadConfiguration();
            database = conf.getProperty("database");
            ServerAddress adresse = buildServerAdress(conf);
            System.out.println("Configuration du client <" + database + "@" + adresse + ">");
            client = new MongoClient(adresse);
            Runtime.getRuntime().addShutdownHook(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ServerAddress buildServerAdress(Properties conf) throws UnknownHostException {
        String host = conf.getProperty("host");
        int port = Integer.parseInt(conf.getProperty("port"));
        return new ServerAddress(host, port);
    }

    private Properties loadConfiguration() throws IOException {
        Properties props = new Properties();
        props.load(this.getClass().getClassLoader().getResourceAsStream("mongodb.properties"));
        return props;
    }

    @Override
    public void run() {
        close();
    }

    private void close() {
        if (client != null) {
            client.close();
        }
    }

    protected MongoClient client() {
        return client;
    }

    protected String database() {
        return database;
    }
}
