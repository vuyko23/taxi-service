package taxi.service;

import java.util.Optional;
import taxi.lib.Inject;
import taxi.lib.Service;
import taxi.lib.exception.AuthenticationException;
import taxi.model.Driver;
import taxi.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Optional<Driver> driver = driverService.findByLogin(login);
        if (driver.isPresent() && driver.get().getPassword()
                .equals(hashPassword(password, driver.get().getSalt()))) {
            return driver.get();
        }
        throw new AuthenticationException("Incorrect username or password");
    }

    private String hashPassword(String password, byte[] salt) {
        return HashUtil.hashPassword(password, salt);
    }
}
