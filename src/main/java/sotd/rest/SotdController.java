package sotd.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sotd.Executor;

@RestController
public class SotdController {

    private final Executor executor;

    @Autowired
    public SotdController(Executor executor) {
        this.executor = executor;
    }

    @GetMapping("/execute")
    public void execute() {
        executor.execute();
    }

}
