<template>
  <div class="top-right">
    <router-link to="/login">
      <button>Log in</button>
    </router-link>
    <router-link to="/register">
      <button>Register</button>
    </router-link>
  </div>

  <div class="card">
    <h1 id="logo" @click="goToNotes">YouNote</h1>
  </div>
</template>



<script>
export default {
  name: "HomeView",
  methods: {
    gotToNotes() {
      this.$router.push("/notes")
    }
  }

};
</script>

<style>
body {
  overflow-x:hidden;
 display: flex;
 justify-content: center;
 align-items: center;
 min-height: 100vh;
 background: white
}

.top-right {
  position:absolute;
  top: 1rem;
  right: 1rem;
  z-index: 10;
  display: flex;
  gap: 10px;
}

.top-right button {
   background-color: aliceblue;    /* coral background */
  color: lightcoral;                   /* white text */
   border: 2px solid lightcoral;
  border-radius: 5px;
  padding: 8px 15px;
  margin-left: 10px;
  cursor: pointer;
  font-size: 14px;
 transition: all 0.3s ease-in-out;
}

.top-right button:hover {
background-color: lightcoral;  /* swap background */
  color: aliceblue;              /* swap text */
  border: 2px solid aliceblue;
  
}

.card {
  background-color: aliceblue;   /* light background */
  color: lightcoral;             /* text color */
  border-radius: 15px;
  border: 3px solid lightcoral;
  padding: 40px;
  width: 80%;
  max-width: 800px;
  margin: 200px auto;
  text-align: center;
  transition: all 0.3s ease-in-out;
}

.card:hover {
 background-color: lightcoral;  /* swap background */
  color: aliceblue;              /* swap text */
  border: 3px solid aliceblue; 
}

#logo {
  font-family: 'Poppins', sans-serif;
  font-size: 80px;
  color: inherit;
  cursor: pointer;
   text-shadow: 2px 2px 5px rgba(0,0,0,0.2);
  margin: 0;
  transition: color 0.3s ease-in-out;
}

</style>