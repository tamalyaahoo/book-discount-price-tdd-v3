# 📘 **Book Price UI — ReactJS Frontend**

A lightweight React + Vite frontend for the **Book Discount Price Kata**.
This UI allows users to:

* View all available books
* Increase/decrease quantities
* Build a shopping basket
* Calculate total price including discounts
* Display final pricing summary

This frontend communicates with the backend:

| API                          | Method | Description                                |
| ---------------------------- | ------ | ------------------------------------------ |
| `/api/books/getbooks`        | GET    | Fetch all available development books      |
| `/api/books/price/calculate` | POST   | Compute price, discount, and merged basket |

---

# 🚀 **Tech Stack**

* **React 18**
* **Vite** (super-fast frontend build tool)
* **React Router DOM v6**
* **Fetch API** for backend calls
* **CSS Modules or Plain CSS**
* **Node.js 18+**

---

# 📁 **Project Structure**

```
book-price-ui/
│
├── src/
│   ├── api/
│   │   └── bookApi.js
│   ├── components/
│   │   ├── BookCard.jsx
│   │   └── BasketSummary.jsx
│   ├── pages/
│   │   ├── BookListPage.jsx
│   │   └── PriceSummaryPage.jsx
│   ├── App.jsx
│   ├── main.jsx
│   ├── index.css
│   └── styles/
│
├── public/
├── package.json
├── vite.config.js
└── README.md  ← (this file)
```

---

# 🧩 **UI Flow**

### **1️⃣ Home Page — BookListPage**

* Fetches all books (`GET /api/books/getbooks`)
* Displays books using `<BookCard />`
* User can adjust quantity ( + / – buttons )
* Basket summary updates dynamically
* Click **"Proceed to Checkout"** → navigates to final summary page

### **2️⃣ Price Summary Page — PriceSummaryPage**

* Sends the basket to backend:

  ```
  POST /api/books/price/calculate
  ```
* Displays:

    * Final merged basket
    * Total Original Price
    * Discounted Price

---

# ⚙️ **Installation & Setup**

### **1. Clone Repository**

```bash
git clone https://github.com/tamalyaahoo/book-price-ui.git
cd book-price-ui
```

### **2. Install Dependencies**

```bash
npm install
```

### **3. Start the UI**

```bash
npm run dev
```

### **4. Open in Browser**

```
http://localhost:5173/
```

---

# 🔌 **API Configuration**

The UI expects backend running at **[http://localhost:8081](http://localhost:8081)**.

Update `/src/api/bookApi.js` if needed:

```js
export const BASE_URL = "http://localhost:8081/api/books";
```

---

# 🧪 **Test API Endpoints**

### ✔ Fetch books

```bash
curl http://localhost:8081/api/books/getbooks
```

### ✔ Calculate price

```bash
curl -X POST http://localhost:8081/api/books/price/calculate \
-H "Content-Type: application/json" \
-d '{
  "bookList": [
    {"title": "Clean Code", "quantity": 1},
    {"title": "The Clean Coder", "quantity": 1}
  ]
}'
```

---

# 🛠 **Scripts**

| Command           | Description               |
| ----------------- | ------------------------- |
| `npm install`     | Install dependencies      |
| `npm run dev`     | Run local Vite dev server |
| `npm run build`   | Production build          |
| `npm run preview` | Preview production build  |

---

# 🧩 **Troubleshooting**

### ❌ UI blank on load

Install missing packages:

```bash
npm install react react-dom @vitejs/plugin-react react-router-dom
```

### ❌ API not working

Check backend is running:

```
http://localhost:8081/api/books/getbooks
```