atAuctionOrders.vue
<template>
  <div>
    <h2>At-Auction Orders Preview</h2>
    <label for="stockSelect">Select Stock:</label>
    <select id="stockSelect" v-model="selectedStock">
      <option value="AAPL">AAPL</option>
      <option value="TSLA">TSLA</option>
      <option value="MSFT">MSFT</option>
    </select>
    
    <table v-if="atAuctionOrders[selectedStock]" class="table table-striped">
      <!-- Table content -->
    </table>
    <div v-else-if="isLoading">
      <span>Loading...</span>
    </div>
    <div v-else>
      <span>Error fetching at-auction orders.</span>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue';

async function fetchAtAuctionOrders(stockId) {
  const response = await fetch(`http://localhost:8081/transactions/atAuctionOrders?stockId=${stockId}`);
  const data = await response.json();
  return data;
}

export default {
  props: {},
  setup() {
    const atAuctionOrders = ref({});
    const isLoading = ref(true);
    const selectedStock = ref('AAPL');

    const loadStockData = async () => {
      isLoading.value = true;
      atAuctionOrders.value[selectedStock.value] = await fetchAtAuctionOrders(selectedStock.value);
      isLoading.value = false;
    };

    onMounted(() => {
      loadStockData();
    });

    watch(selectedStock, () => {
      loadStockData();
    });

    return {
      atAuctionOrders,
      isLoading,
      selectedStock,
    };
  },
};
</script>
