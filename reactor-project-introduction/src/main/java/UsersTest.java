import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class UsersTest {
    public static final Logger log = LoggerFactory.getLogger(UsersTest.class);
    public static void main(String[] args) {
        Flux<String> names = Flux.just(
                "Zaraki Kenpachi",
                "Gojo Satoru",
                "Hitokiri Battösai",
                "Madara Uchiha",
                "Ken Kaneki",
                "Ichigo cjava");
        Flux<User> users = names.map(
                name -> new User(name.split(" ")[0].toUpperCase(), name.split(" ")[1].toUpperCase())
            ).filter(user -> !user.getLastname().equalsIgnoreCase("cjava"))
                .doOnNext(user -> {
                    if(user == null)
                        throw new RuntimeException("Names cannot be empty");
                    System.out.println(user.getFirstname().concat(" ").concat(user.getLastname()));
                }
                ).map(
                        user -> {
                            String name = user.getFirstname().toLowerCase();
                            user.setFirstname(name);
                            return user;
                        }
                );
        users.subscribe(
                e -> log.info(e.toString()),
                err -> log.error(err.getMessage()),
                new Runnable() {
                    @Override
                    public void run() {
                        log.info("Observable execution has been exited successfully");}
                    }
                );


    }
}
