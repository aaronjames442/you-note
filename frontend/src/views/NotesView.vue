<template>
  <div class="notes-container">
    <!-- Header with user info -->
    <div class="header">
      <span v-if="user">Welcome, {{ user.username }}</span>
      <button @click="logout">Logout</button>
    </div>

    <h2>YouNote</h2>

    <!-- Add Note Form -->
    <div class="note-input">
      <textarea
        v-model="newNote"
        placeholder="Write your note here..."
      ></textarea>
      <button @click="addNote">Add Note</button>
      <p v-if="error" class="error">{{ error }}</p>
    </div>

    <!-- Notes Grid -->
    <div class="notes-grid">
      <div v-for="note in notes" :key="note.id" class="note-card">
        <!-- Editing mode -->
        <div v-if="note.editing">
          <textarea v-model="note.content"></textarea>
          <button @click="saveNote(note)">Save</button>
          <button @click="note.editing = false">Cancel</button>
        </div>

        <!-- Display mode -->
        <div v-else>
          <p class="content">{{ note.content }}</p>
          <p class="date">{{ formatDate(note.createdAt) }}</p>
          <div class="actions">
            <button @click="note.editing = true">✏️</button>
            <button v-if="isAdmin" @click="deleteNote(note.id)">🗑️</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NoteService from "../services/NoteService";
import { mapState } from "vuex";

export default {
  name: "NotesView",
  data() {
    return {
      notes: [],
      newNote: "",
      error: "",
    };
  },
  computed: {
    ...mapState(["user"]),
    isAdmin() {
      return this.user?.role === "ROLE_ADMIN";
    },
  },
  methods: {
    async fetchNotes() {
      try {
        const response = await NoteService.getAllNotes();
        this.notes = response.data.map((n) => ({ ...n, editing: false }));
      } catch (err) {
        this.error = "Failed to load notes.";
        console.error(err);
      }
    },
    async addNote() {
      if (!this.newNote.trim()) {
        this.error = "Note cannot be empty.";
        return;
      }
      try {
        const note = { content: this.newNote };
        await NoteService.createNote(note);
        this.newNote = "";
        this.error = "";
        this.fetchNotes();
      } catch (err) {
        this.error = "Failed to add note.";
        console.error(err);
      }
    },
    async saveNote(note) {
      if (!note.content.trim()) {
        this.error = "Note cannot be empty.";
        return;
      }
      try {
        await NoteService.updateNote(note.id, { content: note.content });
        note.editing = false;
        this.error = "";
        this.fetchNotes();
      } catch (err) {
        this.error = "Failed to update note.";
        console.error(err);
      }
    },
    async deleteNote(id) {
      if (!confirm("Are you sure you want to delete this note?")) return;
      try {
        await NoteService.deleteNote(id);
        this.fetchNotes();
      } catch (err) {
        this.error = "Failed to delete note.";
        console.error(err);
      }
    },
    logout() {
      this.$store.commit("SET_AUTH_TOKEN", "");
      this.$store.commit("SET_USER", null);
      localStorage.removeItem("token");
      this.$router.push({ name: "login" });
    },
    formatDate(date) {
      if (!date) return "";
      return new Date(date).toLocaleDateString();
    },
  },
  mounted() {
    this.fetchNotes();
  },
};
</script>

<style scoped>
.notes-container {
  max-width: 900px;
  margin: auto;
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.note-input {
  margin-bottom: 20px;
}

textarea {
  width: 100%;
  min-height: 80px;
  margin-bottom: 10px;
  padding: 10px;
  font-size: 1rem;
  border-radius: 6px;
  border: 1px solid #ccc;
}

button {
  padding: 8px 15px;
  background: #ff6b6b;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  margin-right: 5px;
  transition: 0.3s;
}

button:hover {
  background: #ff4c4c;
}

.error {
  color: red;
  margin-top: 5px;
}

.notes-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 16px;
}

.note-card {
  background: #fff8a6; /* sticky note yellow */
  border: 1px solid #e0c97f;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.2);
  transition: transform 0.2s;
}

.note-card:hover {
  transform: scale(1.02);
}

.note-card .content {
  font-size: 1rem;
  margin-bottom: 10px;
}

.note-card .date {
  font-size: 0.8rem;
  color: #666;
}

.note-card .actions {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
}
</style>
