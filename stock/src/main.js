import { createApp } from "vue";
import App from "./components/App.vue";
import "./components/assets/tailwind.css";
// import ElementPlus from 'element-plus'
import ElementPlus from "element-plus";
import "element-plus/theme-chalk/index.css"; // Import Element UI CSS

// fontawesome
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faCaretUp,
  faCaretDown,
  faSearch,
  faCheck, faTimes, faPlus
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
library.add(faCaretUp, faCaretDown, faSearch, faCheck, faTimes, faPlus);

// app.use(ElementPlus)
createApp(App).component("font-awesome-icon", FontAwesomeIcon).use(ElementPlus).mount("#app");

// custom filter
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
    // Add comma for dollar format, i.e. 1,000,000
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