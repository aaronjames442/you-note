<template>
  <div class="notes-container">
    <h2>Notes</h2>

    <!-- Add note form -->
    <textarea v-model="newNote" placeholder="Write your note here..."></textarea>
    <button @click="addNote" class="add-btn">Add Note</button>

    <!-- Notes list -->
    <div class="notes-grid">
      <div
        v-for="note in notes"
        :key="note.id"
        class="note-card"
        :style="{ backgroundColor: getRandomColor(note.id) }"
      >
        <p class="note-content">{{ note.content }}</p>
        <p class="note-date">
          {{ formatDate(note.created_at || note.updated_at) }}
        </p>
        <div class="note-actions">
          <button @click="deleteNote(note.id)" class="delete-btn">🗑</button>
          <button @click="editNote(note)" class="edit-btn">✏️</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NoteService from "@/services/NoteService";

export default {
  data() {
    return {
      notes: [],
      newNote: "",
      colors: ["#a7f3d0", "#bfdbfe", "#fecaca", "#fde68a"], // green, blue, red, yellow
    };
  },
  methods: {
    async fetchNotes() {
      try {
        const response = await NoteService.getAllNotes();
        console.log("Fetched notes:", response.data); // 👀 Debugging
        this.notes = response.data;
      } catch (err) {
        console.error("Failed to fetch notes", err);
      }
    },
    async addNote() {
      if (!this.newNote.trim()) return;
      try {
        const note = { content: this.newNote };
        await NoteService.createNote(note);
        this.newNote = "";
        this.fetchNotes();
      } catch (err) {
        console.error("Failed to add note", err);
      }
    },
    async deleteNote(id) {
      try {
        await NoteService.deleteNote(id);
        this.fetchNotes();
      } catch (err) {
        console.error("Failed to delete note", err);
      }
    },
    editNote(note) {
      alert(`Edit feature not built yet. Note: ${note.content}`);
    },
    formatDate(date) {
      if (!date) return "";
      return new Date(date).toLocaleDateString();
    },
    getRandomColor(id) {
      return this.colors[id % this.colors.length];
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
  margin: 2rem auto;
  padding: 1rem;
}

textarea {
  width: 100%;
  height: 70px;
  margin-bottom: 0.5rem;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 6px;
}

.add-btn {
  display: block;
  width: 100%;
  background-color: #f87171;
  color: white;
  padding: 0.6rem;
  border: none;
  border-radius: 6px;
  font-weight: bold;
  cursor: pointer;
}

.notes-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 1rem;
  margin-top: 1.5rem;
}

.note-card {
  padding: 1rem;
  border-radius: 12px;
  color: #111;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.note-content {
  font-size: 1rem;
  margin-bottom: 0.5rem;
}

.note-date {
  font-size: 0.8rem;
  color: #333;
}

.note-actions {
  margin-top: 0.5rem;
  display: flex;
  gap: 0.5rem;
}

.delete-btn,
.edit-btn {
  border: none;
  background: transparent;
  cursor: pointer;
  font-size: 1.2rem;
}
</style>
