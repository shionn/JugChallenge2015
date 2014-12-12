/**
 * 
 */
package shionn.jug.database;

import javax.enterprise.inject.spi.InjectionPoint;
import javax.ws.rs.Produces;

import org.mongojack.JacksonDBCollection;

import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y-
 */
public class Database {

    @SuppressWarnings("unchecked")
    @Produces
    @Collection
    public <DATA, KEY> JacksonDBCollection<DATA, KEY> build(InjectionPoint params) {
        Collection source = params.getAnnotated().getAnnotation(Collection.class);
        Class<DATA> dataClazz = (Class<DATA>) source.data();
        Class<KEY> keyClass = (Class<KEY>) source.key();
        return JacksonDBCollection.wrap(collection(dataClazz), dataClazz, keyClass);
    }

    private DB db() {
        return MongoClientHolder.get().client().getDB(MongoClientHolder.get().database());
    }

    private DBCollection collection(Class<?> type) {
        return db().getCollection(type.getSimpleName().toLowerCase());
    }

}
