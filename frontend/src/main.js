import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import { createStore } from "./store";
import axios from "axios";
axios.defaults.baseURL = import.meta.env.VITE_REMOTE_API;



// Load persisted credentials
let currentToken = localStorage.getItem("token");
let currentUser = JSON.parse(localStorage.getItem("user"));

if (currentToken) {
  axios.defaults.headers.common["Authorization"] = `Bearer ${currentToken}`;
}

// Create store with persisted data
const store = createStore(currentToken, currentUser);

const app = createApp(App);
app.use(store);
app.use(router);
app.mount("#app");
