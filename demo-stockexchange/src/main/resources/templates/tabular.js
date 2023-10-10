document.addEventListener("DOMContentLoaded", function () {
    const chat = document.getElementById("chat");
    const fetchButton = document.getElementById("fetch-button");
  
    // Function to update chat content
    function updateChat(buyerVolume, sellerVolume) {
      chat.innerHTML = `
        <p>Buyer Volume: ${buyerVolume}</p>
        <p>Seller Volume: ${sellerVolume}</p>
      `;
    }
  
    fetchButton.addEventListener("click", function () {
      // Fetch data from the Spring Boot API endpoint
      fetch("http://localhost:8081/transactions/indicator")
        .then(response => response.json())
        .then(data => {
          // Extract data from the JSON response
          const buyerVolume = data.buyerVolume;
          const sellerVolume = data.sellerVolume;
  
          // Update the chat with the extracted data
          updateChat(buyerVolume, sellerVolume);
        })
        .catch(error => {
          console.error("Error fetching data:", error);
        });
    });
  
    // Fetch and display data when the page loads
    fetch("http://localhost:8081/transactions/indicator")
      .then(response => response.json())
      .then(data => {
        // Extract data from the JSON response
        const buyerVolume = data.buyerVolume;
        const sellerVolume = data.sellerVolume;
  
        // Update the chat with the extracted data
        updateChat(buyerVolume, sellerVolume);
      })
      .catch(error => {
        console.error("Error fetching data:", error);
      });
  });
  