package wis.quotes_service.repository;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import wis.quotes_service.model.Quote;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class QuoteRepository {
    private final DynamoDbTable<Quote> quoteTable;

    public QuoteRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.quoteTable = dynamoDbEnhancedClient.table("Quotes", TableSchema.fromBean(Quote.class));
    }

    public List<Quote> findAll() {
        return StreamSupport.stream(quoteTable.scan().items().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Quote findById(String id) {
        return quoteTable.getItem(r -> r.key(k -> k.partitionValue(id)));
    }

    public Quote save(Quote quote) {
        if (quote.getId() == null) {
            quote.setId(UUID.randomUUID().toString());
        }
        quoteTable.putItem(quote);
        return quote;
    }

    public void deleteById(String id) {
        quoteTable.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }
}
