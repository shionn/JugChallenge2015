package shionn.jug.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Code sous licence GPLv3 (http://www.gnu.org/licenses/gpl.html)
 *
 * @author <b>Shionn</b>, shionn@gmail.com <i>http://shionn.org</i><br>
 *         GCS d- s+:+ a+ C++ UL/M P L+ E--- W++ N K- w-- M+ t+ 5 X R+ !tv b+ D+ G- e+++ h+ r- y-
 */
public class Session {

    private static final String USER = "user";

    private HttpSession session;

    public Session(HttpServletRequest request) {
        session = request.getSession();
    }

    public void clearUser() {
        session.removeAttribute(USER);
    }

    public void setUser(String user) {
        session.setAttribute(USER, user);
    }

    public boolean hasUser() {
        return session.getAttribute(USER) != null;
    }

    public String getUser() {
        return (String) session.getAttribute(USER);
    }

}
