package gr.aueb.budgettune.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiInfoController {

    @GetMapping("/api/info")
    public String getApiInfo() {
        return "BudgetTune API is up!";
    }
}
