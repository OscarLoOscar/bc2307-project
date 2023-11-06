package com.example.demo.demostockexchange.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.demostockexchange.entity.User;
import com.example.demo.demostockexchange.entity.Transaction;
import com.example.demo.demostockexchange.infra.TransactionType;
import com.example.demo.demostockexchange.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> combineTransactions(User user) {
        List<Transaction> transactions = transactionRepository.findByUserId(user.getId());

        // Create a map to combine transactions by stock symbol and quantity
        Map<String, Integer> stockQuantityMap = new HashMap<>();
        List<Transaction> combinedTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            String stockSymbol = transaction.getStockSymbol();
            int quantity = transaction.getQuantity();
            int type = transaction.getTransactionType().getTransactionType();

            if (!stockQuantityMap.containsKey(stockSymbol)) {
                stockQuantityMap.put(stockSymbol, 0);
            }

            if (type == TransactionType.BUY.getTransactionType()) {
                stockQuantityMap.put(stockSymbol, stockQuantityMap.get(stockSymbol) + quantity);
            } else if (type == TransactionType.SELL.getTransactionType()) {
                int remainingQuantity = stockQuantityMap.get(stockSymbol);

                if (remainingQuantity >= quantity) {
                    // Combine the transactions
                    stockQuantityMap.put(stockSymbol, remainingQuantity - quantity);

                    Transaction combinedTransaction = new Transaction();
                    combinedTransaction.setStockSymbol(stockSymbol);
                    combinedTransaction.setQuantity(quantity);
                    combinedTransactions.add(combinedTransaction);
                } else {
                    // Handle partial sells, or you can record the remaining quantity
                }
            }
        }

        return combinedTransactions;
    }
}
