import DataStorage.User;
import InteractionWithUser.InteractionWithUser;

import java.io.IOException;

public class Main {
    static InteractionWithUser interactionWithUser = new InteractionWithUser();
    static User user = new User();

    public static void main(String[] args) throws IOException, InterruptedException {
        setDefaultUserData();
        /*===Create user===*/
        System.out.println("Create user");
        System.out.println(interactionWithUser.add(user));
        //=====================//

        /*===Update User===*/
        User updatedUser = user;
        updatedUser.setUsername("daniil1982");
        updatedUser.setWebsite("updatedUser.com");
        System.out.println("Update user");
        System.out.println(interactionWithUser.update(9, updatedUser));
        //=====================//

        /*===Delete user===*/
        System.out.println("Delete user");
        System.out.println(interactionWithUser.delete(user));
        //=====================//

        /*===Get users==*/
        System.out.println("Get users");
        System.out.println(interactionWithUser.getUsers());
        //=====================//

        /*===Get user by obtained ID===*/
        System.out.println("Get user by obtained ID");
        System.out.println(interactionWithUser.getUserID(9));
        //=====================//

        /*===Get user by obtained username===*/
        System.out.println("Get user by obtained username");
        System.out.println(interactionWithUser.getUserByUsername("Bret"));
        //=====================//

        /*===Get last post of user by obtained ID===*/
        System.out.println("Get last  post of user by obtained ID");
        System.out.println(interactionWithUser.getLastPostByUserID(9));
        //=====================//

        /*===Get all comments for post bt obtained post ID
                                         and write in file===*/
        System.out.println("Get all comments for post bt obtained post ID and write in file");
        System.out.println(interactionWithUser.getCommentsByPIDAndWInFile(9));
        //=====================//

        /*===Get all active todos for user===*/
        System.out.println("Get all active todos for user");
        System.out.println(interactionWithUser.getTodosByObtainedUser(9));
        //=====================//
    }

    public static void setDefaultUserData(){
        user.setName("Daniil");
        user.setAddress("Street", "Suite", "City", "90000-1112");
        user.setEmail("user.email@user.com");
        user.setPhone("111342155");
        user.setUsername("daniil");
        user.setCompany("Company", "My company", "bs");
        user.setWebsite("user.com");
        user.setId(11);
    }
}
