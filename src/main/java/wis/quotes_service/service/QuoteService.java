package wis.quotes_service.service;

import wis.quotes_service.model.Quote;
import wis.quotes_service.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    public List<Quote> findAll() {
        return quoteRepository.findAll();
    }

    public Quote findById(String id) {
        return quoteRepository.findById(id);
    }

    public Quote save(Quote quote, String addedBy) {
        quote.setAddedBy(addedBy);
        quote.setAddedDate(LocalDateTime.now());
        return quoteRepository.save(quote);
    }

    public void deleteById(String id) {
        quoteRepository.deleteById(id);
    }

}
