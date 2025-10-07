# 🗒️ YouNote – Notes Application

YouNote is a full-stack notes application that allows users to securely register, log in, and manage personal notes through a clean and responsive interface.  
Built with **Spring Boot** and **Vue.js**, this project demonstrates my ability to design, develop, and integrate both back-end and front-end technologies into a seamless user experience.

---

## 💡 About This Project
I built YouNote as a personal project to strengthen my full-stack development skills after completing my Java bootcamp.  
My goal was to create a real-world application that showcases my understanding of authentication, RESTful APIs, and reactive front-end design.  
I continue to improve its design, styling, and functionality as I grow as a developer.

---

## 🛠 Tech Stack
**Backend:** Java, Spring Boot, PostgreSQL, JWT Authentication  
**Frontend:** Vue.js, Vue Router, Vuex, Axios  
**Tools:** IntelliJ IDEA, VS Code, Postman, Git, GitHub  

---

## ⚙️ Features
- 🔐 User authentication (register/login) with JWT tokens  
- 📝 Create, read, update, and delete notes  
- 💾 Persistent storage using PostgreSQL  
- 🔄 RESTful API built with Spring Boot  
- ⚡ Responsive and dynamic front-end built with Vue.js  
- 🎨 Continuously improving front-end styling and responsive design skills  

---

## 🚀 Getting Started

Follow these steps to run YouNote locally:

### Backend & Frontend Setup
```bash
# Start the backend (Spring Boot)
cd backend
./mvnw spring-boot:run

# In a separate terminal, start the frontend (Vue.js)
cd frontend
npm install
npm run serve
```
Then open:
- Backend → [http://localhost:8080](http://localhost:8080)
- Frontend → [http://localhost:5173](http://localhost:5173)
---

## 📂 API Endpoints

| Method | Endpoint | Description |
|--------|-----------|-------------|
| `POST` | `/auth/register` | Register a new user |
| `POST` | `/auth/login` | Authenticate a user and return JWT token |
| `GET` | `/notes` | Retrieve all notes for logged-in user |
| `POST` | `/notes` | Create a new note |
| `PUT` | `/notes/{id}` | Update an existing note |
| `DELETE` | `/notes/{id}` | Delete a note |

---

## 👤 Author
**Aaron Holt**  
📍 Dallas, TX  
📧 [aaronholt442@gmail.com](mailto:aaronholt442@gmail.com)  
🔗 [GitHub](https://github.com/aaronjames442)  
🔗 [LinkedIn](#)

---

## 🧭 Future Improvements
- Enhanced UI design and note organization features  
- Add note search and filtering  
- Dark mode support  
- Deploy app using Docker and cloud hosting  
- User profile settings  

---

## 🧰 Acknowledgments
This project was built to reinforce my understanding of **Java full-stack development**, combining back-end REST APIs with a reactive Vue.js front-end.  
I plan to continue enhancing YouNote as I learn more about performance optimization, design systems, and deployment pipelines.

---


