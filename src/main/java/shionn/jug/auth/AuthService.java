package shionn.jug.auth;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;
import org.mongojack.JacksonDBCollection;

import shionn.jug.database.Collection;
import shionn.jug.database.bean.User;


/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y-
 */
@Path("auth")
public class AuthService {

    @Inject
    @Collection(data = User.class, key = String.class)
    private JacksonDBCollection<User, String> users;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean authentify(Authentification auth, @Context HttpServletRequest request) {
        User user = users.findOneById(auth.getUser());
        String pass = DigestUtils.md5Hex(auth.getPassword());
        Session session = new Session(request);
        session.clearUser();
        boolean valid = false;
        if (user != null && user.getPassword().equals(pass)) {
            session.setUser(auth.getUser());
            valid = true;
        }
        return valid;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public boolean authentified(@Context HttpServletRequest request) {
        return new Session(request).hasUser();
    }

    @POST()
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User register(Authentification auth) {
        String pass = DigestUtils.md5Hex(auth.getPassword());
        User user = users.insert(new User(auth.getUser(), pass)).getSavedObject();
        return user;
    }

}
