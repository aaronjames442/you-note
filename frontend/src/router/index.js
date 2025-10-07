import { createRouter, createWebHistory } from "vue-router";
import { useStore } from "vuex";

import HomeView from "../views/HomeView.vue";
import LoginView from "../views/LoginView.vue";
import RegisterView from "../views/RegisterView.vue";
import NotesView from "../views/NotesView.vue";

const routes = [
  { path: "/", name: "home", component: HomeView },
  { path: "/login", name: "login", component: LoginView },
  { path: "/register", name: "register", component: RegisterView },
  {
    path: "/notes",
    name: "notes",
    component: NotesView,
    meta: { requiresAuth: true }
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to) => {
  const store = useStore();
  const requiresAuth = to.matched.some((x) => x.meta.requiresAuth);

  if (requiresAuth && store.state.token === "") {
    return { name: "login" };
  }
});

export default router;
