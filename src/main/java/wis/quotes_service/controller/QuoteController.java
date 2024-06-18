package wis.quotes_service.controller;

import wis.quotes_service.model.Quote;
import wis.quotes_service.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quotes")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    @GetMapping
    public List<Quote> getAllQuotes() {
        return quoteService.findAll();
    }

    @GetMapping("/{id}")
    public Quote getQuoteById(@PathVariable String id) {
        return quoteService.findById(id);
    }

    @PostMapping
    public Quote createQuote(@RequestBody Quote quote, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaim("username");
        return quoteService.save(quote, username);
    }

    @DeleteMapping("/{id}")
    public void deleteQuoteById(@PathVariable String id) {
        quoteService.deleteById(id);
    }

}
