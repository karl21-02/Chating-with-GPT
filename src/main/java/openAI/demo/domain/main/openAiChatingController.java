package openAI.demo.domain.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class openAiChatingController {

    @GetMapping("/")
    public String openAiChating() {
        return "index.html";
    }
}
