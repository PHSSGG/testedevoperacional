package phss.testedevoperacional.data.repository;

import phss.testedevoperacional.data.DataResult;
import phss.testedevoperacional.database.Database;
import phss.testedevoperacional.models.User;

import java.util.Optional;

/**
 * Essa classe é usada para simular um sistema de login e manter o usário logado na memória.
 */
public class UserRepository {

    Database database;

    public UserRepository(Database database) {
        this.database = database;
    }

    User loggedUser = null;

    public boolean isLogged() {
        return loggedUser != null;
    }

    public DataResult<User> login(String username, String password) {
        DataResult<User> result = new DataResult<>();
        Optional<User> foundUser = database.getUsers().stream().filter(user -> user.getUsername().equals(username)).findFirst();

        if (foundUser.isPresent()) {
            User user = foundUser.get();
            if (!user.getPassword().equals(password)) {
                result.setSuccess(false);
                result.setError("Senha incorreta, tente novamente");
            } else {
                result.setSuccess(true);
                result.setValue(foundUser.get());

                loggedUser = user;
            }
        } else {
            result.setSuccess(false);
            result.setError("Usuário não encontrado");
        }

        return result;
    }

    public void logout() {
        loggedUser = null;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

}
