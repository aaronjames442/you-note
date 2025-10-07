<template>
  <div class="login">
    <h1>YouNote Login</h1>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <input
          type="text"
          placeholder="Username"
          v-model="username"
          required
        />
      </div>

      <div class="form-group">
        <input
          type="password"
          placeholder="Password"
          v-model="password"
          required
        />
      </div>

      <button type="submit">Log In</button>
    </form>

    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script>
import AuthService from "../services/AuthService";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  name: "LoginView",
  data() {
    return {
      username: "",
      password: "",
      error: ""
    };
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    return { store, router };
  },
  methods: {
    async handleLogin() {
      try {
                const response = await AuthService.login({
          username: this.username,
          password: this.password
        });

        // token + user comes from backend
        const token = response.token;
        const user = response.user;


        // Commit to Vuex store
        this.store.commit("SET_AUTH_TOKEN", token);
        this.store.commit("SET_USER", user);

        // Redirect to Notes page
        this.router.push({ name: "notes" });
      } catch (err) {
        this.error = "Invalid username or password.";
        console.error(err);
      }
    }
  }
};
</script>



<style>

.logo {
  display: block;
  text-align: center;
  font-size: 32px;
  font-weight: bold;
  font-family: 'Poppins', sans-serif;
  color: lightcoral;
  margin-bottom: 20px;
  text-decoration: none;
  transition: color 0.3s ease, transform 0.3s ease;
}



.login {
  background: aliceblue;
  border: 2px solid lightcoral;
  border-radius: 10px;
  padding: 30px;
  max-width: 400px;
  margin: 100px auto; /* center it */
  box-shadow: 0 4px 10px rgba(0,0,0,0.1);
}

.form-group {
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
}

input {
  padding: 10px;
  border: 1px solid lightgray;
  border-radius: 5px;
  font-size: 14px;
}

button {
  width: 100%;
  padding: 10px;
  background: lightcoral;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
}

button:hover {
  background: white;
  color: lightcoral;
  border: 2px solid lightcoral;
  transform: scale(1.05);
}

</style>