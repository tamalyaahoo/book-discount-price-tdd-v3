import { useEffect, useState } from "react";
import { fetchBooks } from "../api/bookApi";
import BookCard from "../components/BookCard";
import BasketSummary from "../components/BasketSummary";
import { useNavigate } from "react-router-dom";

export default function BookListPage() {
  const [books, setBooks] = useState([]);
  const [basket, setBasket] = useState({});
  const [error, setError] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    fetchBooks()
      .then(setBooks)
      .catch((err) => setError(err.message));
  }, []);

  const increment = (title) => {
    setBasket((prev) => ({
      ...prev,
      [title]: (prev[title] || 0) + 1,
    }));
  };

  const decrement = (title) => {
    setBasket((prev) => {
      const newQty = (prev[title] || 0) - 1;
      if (newQty <= 0) {
        const newBasket = { ...prev };
        delete newBasket[title];
        return newBasket;
      }
      return { ...prev, [title]: newQty };
    });
  };

  const proceedToCheckout = () => {
    navigate("/summary", { state: { basket } });
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>📚 Development Books</h1>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {books.map((book) => (
        <BookCard
          key={book.id}
          book={book}
          quantity={basket[book.title]}
          increment={increment}
          decrement={decrement}
        />
      ))}

      <h3>🧺 Basket Summary</h3>
      <BasketSummary basket={basket} />

      <button
        onClick={proceedToCheckout}
        style={{
          marginTop: "20px",
          padding: "10px 20px",
          cursor: "pointer",
          fontSize: "16px",
        }}
      >
        Proceed to Checkout →
      </button>
    </div>
  );
}
