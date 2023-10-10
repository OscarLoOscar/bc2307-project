package com.example.demo.demostockexchange.model;

import java.util.*;
import java.util.logging.Logger;
import java.text.DecimalFormat;
import java.util.Random;

public class CTrader {
    private Map<String, Double> marketDataList;
    private Map<String, Double> symbolTable;
    private List<Map<String, Object>> positions;
    private List<Map<String, Object>> orders;

    private FIX fix;
    private Logger logger;

    public CTrader(String server, String account, String password, String currency, int clientId, double spread, boolean debug) {
        if (debug) {
            logger = Logger.getLogger("CTraderLogger");
        }
        
        String[] splitString = account.split("\\.");
        String broker = splitString[0] + "." + splitString[1];
        String login = splitString[2];

        positions = new ArrayList<>();
        orders = new ArrayList<>();

        fix = new FIX(server, broker, login, password, currency, clientId, this::positionListCallback, this::orderListCallback);

        marketDataList = new HashMap<>();
        symbolTable = SYMBOLSLIST.get("default");
    }

    public int trade(String symbol, String action, int type, String actionType, double volume, double stopLoss, double takeProfit, double price, Double deviation, String id) {
        String vAction = action;
        String vSymbol = symbol;
        String vTicket = (id != null) ? id : String.format("%.7f", System.currentTimeMillis()).replace(".", "") + String.valueOf(new Random().nextInt(90000) + 10000);
        String vType = String.valueOf(type);
        double vOpenPrice = price;
        double vLots = volume;
        double vSL = stopLoss;
        double vTP = takeProfit;

        logger.info(String.format("Action: %s, Symbol: %s, Lots: %s, Ticket: %s, Price: %s, Take Profit: %s, Stop Loss: %s, Type: %s",
                vAction, vSymbol, vLots, vTicket, vOpenPrice, vSL, vTP, vType));

        String oType = actionType;
        String symbolName = vSymbol.substring(0, 6);
        int size = (int) (vLots * 100000);
        int ticket = -1; // Initialize ticket as -1

        String command = "";
        if ("OPEN".equals(vAction)) {
            if (type > 1) {
                command = String.format("%s %s %s %s %s", oType, symbolName, size, vOpenPrice, vTicket);
                parseCommand(command, String.valueOf(fix.clientId));
            } else {
                command = String.format("%s %s %s %s", oType, symbolName, size, vTicket);
                parseCommand(command, String.valueOf(fix.clientId));

                if (vSL > 0 || vTP > 0) {
                    while (ticket == -1) {
                        try {
                            ticket = fix.originToPosId.get(vTicket);
                            if (ticket != -1) {
                                break;
                            }
                        } catch (Exception e) {
                            logger.info(e.toString());
                            continue;
                        }
                    }
                }

                if (ticket != -1) {
                    if (vSL > 0) {
                        String oTypeSL = (type == 0) ? "sell stop" : "buy stop";
                        command = String.format("%s %s %s %s %s %s", oTypeSL, symbolName, size, vSL, vTicket, ticket);
                        parseCommand(command, String.valueOf(fix.clientId));
                    }
                    if (vTP > 0) {
                        List<Integer> ticketOrders = getOrdersIdByOriginId(vTicket, String.valueOf(fix.clientId));
                        String oTypeTP = (type == 0) ? "sell limit" : "buy limit";
                        command = String.format("%s %s %s %s %s %s", oTypeTP, symbolName, size, vTP, vTicket, ticket);
                        parseCommand(command, String.valueOf(fix.clientId));
                    }
                }
            }
        } else if ("CLOSED".equals(vAction) || "PCLOSED".equals(vAction)) {
            if (type > 1) {
                // ORDEM
                fix.cancelOrder(vTicket);
                List<Integer> ticketOrders = getOrdersIdByOriginId(vTicket, String.valueOf(fix.clientId));
                cancelOrdersByOriginId(ticketOrders, String.valueOf(fix.clientId));
                parseCommand(command, String.valueOf(fix.clientId));
                return -1;
            } else {
                // POSICAO
                fix.closePosition(vTicket, size);
                List<Integer> ticketOrders = getOrdersIdByOriginId(vTicket, String.valueOf(fix.clientId));
                cancelOrdersByOriginId(ticketOrders, String.valueOf(fix.clientId));
                parseCommand(command, String.valueOf(fix.clientId));
                return -1;
            }
        }
        return Integer.parseInt(vTicket);
    }

    public int buy(String symbol, double volume, double stopLoss, double takeProfit, double price) {
        return trade(symbol, "OPEN", 0, "buy", volume, stopLoss, takeProfit, price, null, null);
    }

    public int sell(String symbol, double volume, double stopLoss, double takeProfit, double price) {
        return trade(symbol, "OPEN", 1, "sell", volume, stopLoss, takeProfit, price, null, null);
    }

    // Add other trade methods (buyLimit, sellLimit, buyStop, sellStop) similarly

    private void parseCommand(String command, String clientId) {
        String[] parts = command.split(" ");
        logger.info(Arrays.toString(parts));
        logger.info(String.format("Command: %s ", command));

        if (!fix.logged) {
            logger.info("Waiting for logging...");
            return;
        }

        if ("sub".equals(parts[0])) {
            try {
                int subId = Integer.parseInt(parts[1]);
                fix.marketRequest(subId - 1, parts[2].toUpperCase(), this::quoteCallback);
            } catch (NumberFormatException e) {
                logger.error("Invalid subscription ID");
            }
        }
        // Implement other command parsing logic as needed
    }

    private String floatFormat(String fmt, double num, boolean forceSign) {
        String formatted = (forceSign ? String.format("{:+}", num) : String.valueOf(num));
        return (formatted.length() > fmt.length()) ? formatted : String.format(fmt, num);
    }

    private void positionListCallback(Map<String, Map<String, Object>> data, Map<String, Map<String, Double>> priceData, String clientId) {
        positions.clear();
        for (Map.Entry<String, Map<String, Object>> entry : data.entrySet()) {
            String posId = entry.getKey();
            Map<String, Object> kv = entry.getValue();
            String name = (String) kv.get("name");
            String side = (Double) kv.get("long") > 0 ? "Buy" : "Sell";
            double amount = (Double) kv.get("long") > 0 ? (Double) kv.get("long") : (Double) kv.get("short");
            double priceValue = (Double) kv.get("price");
            String priceStr = floatFormat(String.format("{:.%df}", kv.get("digits")), priceValue, false);
            Map<String, Double> price = priceData.get(name);
            String actualPrice = "";
            String diffStr = "";
            String plStr = "";
            String gainStr = "";

            if (price != null) {
                double p = (side.equals("Buy")) ? price.get("bid") : price.get("ask");
                actualPrice = floatFormat(String.format("{:.%df}", kv.get("digits")), p, false);
                double diff = p - priceValue;
                if (side.equals("Sell")) {
                    diff = -diff;
                }
                diffStr = floatFormat(String.format("{:+.%df}", kv.get("digits")), diff, true);
                double pl = amount * diff;
                plStr = floatFormat("{:+.2f}", pl, true);
                String convert = (String) kv.get("convert");
                boolean convertDir = (Boolean) kv.get("convert_dir");
                Map<String, Double> convertPrice = priceData.get(convert);

                if (convertPrice != null) {
                    double rate = (convertDir) ? 1 / convertPrice.get("ask") : convertPrice.get("bid");
                    double plBase = pl * rate;
                    gainStr = floatFormat("{:+.2f}", plBase, true);
                }
            }
            Map<String, Object> positionMap = new HashMap<>();
            positionMap.put("pos_id", posId);
            positionMap.put("name", name);
            positionMap.put("side", side);
            positionMap.put("amount", amount);
            positionMap.put("price", priceStr);
            positionMap.put("actual_price", actualPrice);
            positionMap.put("diff", diffStr);
            positionMap.put("pl", plStr);
            positionMap.put("gain", gainStr);
            positions.add(positionMap);
        }
        // Update positions in the client
        // Assuming client is an object that holds positions
        // client.updatePositions(positions);
        logger.debug("Client ID %s positions: %s", clientId, positions);
    }

    private void orderListCallback(Map<String, Map<String, Object>> data, Map<String, Map<String, Double>> priceData, String clientId) {
        orders.clear();
        for (Map.Entry<String, Map<String, Object>> entry : data.entrySet()) {
            String ordId = entry.getKey();
            Map<String, Object> kv = entry.getValue();
            String name = (String) kv.get("name");
            String side = (kv.get("side").equals(Side.Buy)) ? "Buy" : "Sell";
            double amount = (Double) kv.get("amount");
            int orderType = (Integer) kv.get("type");
            String priceStr = "";
            if (orderType > 1) {
                priceStr = floatFormat(String.format("{:.%df}", kv.get("digits")), (Double) kv.get("price"), false);
            }
            Map<String, Double> price = priceData.get(name);
            String actualPrice = "";
            if (price != null) {
                double p = (side.equals("Buy")) ? price.get("ask") : price.get("bid");
                actualPrice = floatFormat(String.format("{:.%df}", kv.get("digits")), p, false);
            }
            String posId = (String) kv.get("pos_id");

            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("ord_id", ordId);
            orderMap.put("name", name);
            orderMap.put("side", side);
            orderMap.put("amount", amount);
            orderMap.put("price", priceStr);
            orderMap.put("actual_price", actualPrice);
            orderMap.put("pos_id", posId);
            orderMap.put("clid", kv.get("clid"));
            orders.add(orderMap);
        }
        // Update orders in the client
        // Assuming client is an object that holds orders
        // client.updateOrders(orders);
        logger.debug("Client ID %s orders: %s", clientId, orders);
    }

    private void quoteCallback(String name, int digits, Map<Integer, Map<String, Object>> data) {
        if (data.isEmpty()) {
            return;
        }
        List<Map<String, Object>> ask = new ArrayList<>();
        List<Map<String, Object>> bid = new ArrayList<>();
        for (Map.Entry<Integer, Map<String, Object>> entry : data.entrySet()) {
            Map<String, Object> e = entry.getValue();
            if ((Integer) e.get("type") == 0) {
                bid.add(e);
            } else {
                ask.add(e);
            }
        }
        ask.sort(Comparator.comparingDouble(e -> (Double) e.get("price")));
        bid.sort((e1, e2) -> Double.compare((Double) e2.get("price"), (Double) e1.get("price")));

        String bidStr = floatFormat(String.format("{:.%df}", digits), (Double) bid.get(0).get("price"), false);
        String offerStr = floatFormat(String.format("{:.%df}", digits), (Double) ask.get(0).get("price"), false);
        String spreadStr = floatFormat(String.format("{:.%df}", digits), (Double) ask.get(0).get("price") - (Double) bid.get(0).get("price"), false);

        marketDataList.put(name, Double.parseDouble(bidStr));
        marketDataList.put(name, Double.parseDouble(offerStr));
        marketDataList.put(name, Double.parseDouble(spreadStr));
    }

    private List<Integer> getOrdersIdByOriginId(String ordId, String clientId) {
        if (fix.originToOrdId.containsKey(ordId)) {
            return fix.originToOrdId.get(ordId);
        } else {
            return null;
        }
    }

    private void cancelOrdersByOriginId(List<Integer> clIdArr, String clientId) {
        if (clIdArr == null) {
            return;
        }
        for (Integer clId : clIdArr) {
            fix.cancelOrder(clId);
        }
    }

    public void subscribe(String... symbols) {
        for (String symbol : symbols) {
            fix.spotMarketRequest(symbol);
        }
    }

    public Object quote(String symbol) {
        if (!fix.spotPriceList.containsKey(symbol)) {
            return "Symbol not Subscribed";
        } else if (symbol != null) {
            return fix.spotPriceList.get(symbol);
        }
        return fix.spotPriceList;
    }

    public void closeAll() {
        fix.closeAll();
    }

    public void cancelAll() {
        fix.cancelAll();
    }

    public String logout() {
        if (isConnected()) {
            fix.logout();
            return "Logged out";
        } else {
            return "Not logged in";
        }
    }

    public boolean isConnected() {
        return fix.logged;
    }
}
