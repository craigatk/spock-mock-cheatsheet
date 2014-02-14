package cheatsheet

class UserController {
    UserService userService = new UserService()

    void createUser(String email, String name) {
        User user = userService.createUser(email, name)

        userService.sendWelcomeEmail(user)
    }
}
