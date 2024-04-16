package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        Car car1 = new Car("model1", 1);
        Car car2 = new Car("model2", 2);

        User user1 = new User("user1_1", "user1_2", "ya@user1.ru");
        User user2 = new User("user2_1", "user2_2", "ya@user2.ru");

        user1.setCar(car1);
        user2.setCar(car2);

        userService.add(user1);
        userService.add(user2);

        List<User> users = userService.listUsers();

        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car: " + user.getCar().getModel() + " (" + user.getCar().getSeries() + ") ");
            System.out.println();
        }

        userService.getUserByCarModel("model1", 1).forEach(System.out::println);

        context.close();
    }
}
