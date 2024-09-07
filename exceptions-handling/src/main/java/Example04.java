import reactor.core.publisher.Flux;

public class Example04 {
    public static void main(String[] args) {
        Flux.just(2,0,9,19,17,29)
                .map(element -> {
                    if (element == 17)
                        throw new RuntimeException("Exception occurred!");
                    return element;
                }).onErrorMap(
                        ex  -> {
                            System.out.println("Exception: " + ex);
                            return new CustomException(ex.getMessage(), ex);
                        })
                .log()
                .subscribe();
    }


}
