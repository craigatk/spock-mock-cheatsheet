package cheatsheet

import spock.lang.Specification

class UserControllerArgMatchSpec extends Specification {
    UserController userController

    UserService userService

    def setup() {
        userService = Mock()

        userController = new UserController(
                userService: userService
        )
    }

    def 'wildcard-based method argument matching'() {
        given:
        User user = new User()

        when:
        userController.createUser("test@email.com", "John Doe")

        then:
        1 * userService.createUser(_, _) >> user
        1 * userService.sendWelcomeEmail(user)
    }

    def 'class-based method argument matching'() {
        given:
        User user = new User()

        when:
        userController.createUser("test@email.com", "John Doe")

        then:
        1 * userService.createUser(_ as String, _ as String) >> user
        1 * userService.sendWelcomeEmail(user)
    }

    def 'closure-based argument matching'() {
        given:
        String email = "test@email.com"
        String name = "John Doe"

        when:
        userController.createUser(email, name)

        then:
        1 * userService.createUser(email, name) >> new User(email: email, name: name)
        1 * userService.sendWelcomeEmail({ User u -> u?.email == email && u?.name == name })
    }

    def 'exact argument matching'() {
        given:
        User user = new User()

        String email = "test@email.com"
        String name = "John Doe"

        when:
        userController.createUser(email, name)

        then:
        1 * userService.createUser(email, name) >> user
        1 * userService.sendWelcomeEmail(user)
    }

}
