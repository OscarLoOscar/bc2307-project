import { createApp } from 'vue';
import App from './components/App.vue';
import ElementPlus from 'element-plus';
import 'element-plus/lib/theme-chalk/index.css';

import { library } from '@fortawesome/fontawesome-svg-core';
import {
  faCaretUp,
  faCaretDown,
  faSearch,
  faCheck,
  faTimes,
  faPlus,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

library.add(faCaretUp, faCaretDown, faSearch, faCheck, faTimes, faPlus);

const app = createApp(App);

app.use(ElementPlus);
app.component('font-awesome-icon', FontAwesomeIcon);

// Custom filters
app.config.globalProperties.$filters = {
  // percent
  price_negative(value) {
    if (value.toString().includes('-')) {
      return value;
    }
    return '';
  },
  // comma
  comma_separator(value) {
    // Add commas for dollar format, e.g., 1,000,000
    return value.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',');
  },
  // truncate string
  string_trunc(value, size) {
    if (!value) return '';
    value = value.toString();
    if (value.length <= size) {
      return value;
    }
    return value.substr(0, size);
  },
};

// Mount the app to the DOM
app.mount('#app');
