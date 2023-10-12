import { createApp } from "vue";
import App from "./components/App.vue";
import "./components/assets/tailwind.css";
import ElementPlus from "element-plus";
import "element-plus/theme-chalk/index.css";

// Fontawesome
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faCaretUp,
  faCaretDown,
  faSearch,
  faCheck,
  faTimes,
  faPlus
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

library.add(faCaretUp, faCaretDown, faSearch, faCheck, faTimes, faPlus);

const app = createApp(App); // Create your Vue app instance

app.component("font-awesome-icon", FontAwesomeIcon);
app.use(ElementPlus);

// Custom filters
app.config.globalProperties.$filters = {
  // percent
  price_negative(value) {
    if (value.toString().includes("-")) {
      return value;
    }
    return "";
  },
  // comma
  comma_separator(value) {
    // Add commas for dollar format, e.g., 1,000,000
    return value.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
  },
  // truncate string
  string_trunc(value, size) {
    if (!value) return "";
    value = value.toString();
    if (value.length <= size) {
      return value;
    }
    return value.substr(0, size);
  },
};

app.mount("#app"); // Mount your Vue app to the HTML element with id="app"