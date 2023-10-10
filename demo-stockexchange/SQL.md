CREATE TABLE Customer (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Stock (
  id INT NOT NULL AUTO_INCREMENT,
  exchange VARCHAR(255) NOT NULL,
  ticker VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Portfolio (
  id INT NOT NULL AUTO_INCREMENT,
  customerId INT NOT NULL,
  stocks JSON NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (customerId) REFERENCES Customer(id)
);

CREATE TABLE Orders (
  id INT NOT NULL AUTO_INCREMENT,
  customerId INT NOT NULL,
  stockId INT NOT NULL,
  type VARCHAR(255) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  status VARCHAR(255) NOT NULL,
  quantity INT NOT NULL,
  placedAt DATETIME NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (customerId) REFERENCES Customer(id),
  FOREIGN KEY (stockId) REFERENCES Stock(id)
);

CREATE TABLE Triggers (
  id INT NOT NULL AUTO_INCREMENT,
  customerId INT NOT NULL,
  stockId INT NOT NULL,
  type VARCHAR(255) NOT NULL,
  quantity INT NOT NULL,
  threshold DECIMAL(10,2) NOT NULL,
  condition VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (customerId) REFERENCES Customer(id),
  FOREIGN KEY (stockId) REFERENCES Stock(id)
);

CREATE TABLE StockPriceHistory (
  stockId INT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  updatedAt DATETIME NOT NULL,
  PRIMARY KEY (stockId, updatedAt),
  FOREIGN KEY (stockId) REFERENCES Stock(id)
);
