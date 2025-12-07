import { useLocation, useNavigate } from "react-router-dom";
import { calculatePrice } from "../api/bookApi";
import { useEffect, useState } from "react";

export default function PriceSummaryPage() {
  const { state } = useLocation();
  const navigate = useNavigate();
  const basket = state?.basket || {};

  const [result, setResult] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    const bookList = Object.entries(basket).map(([title, quantity]) => ({
      title,
      quantity,
    }));

    calculatePrice(bookList)
      .then(setResult)
      .catch((err) => setError(err.message));
  }, []);

  if (error) return <p style={{ color: "red" }}>{error}</p>;
  if (!result) return <p>Calculating price...</p>;

  return (
    <div style={{ padding: "20px" }}>
      <h1>🧾 Price Summary</h1>

      <ul>
        {result.bookList.map((b, i) => (
          <li key={i}>
           <b> {b.title} — Qty: {b.quantity} </b>
          </li>
        ))}
      </ul>

      <h3>Total Price: ${result.totalPrice}</h3>
      <h3>Discounted Price: ${result.discountPrice}</h3>

      <button
        onClick={() => navigate("/")}
        style={{
          marginTop: "20px",
          padding: "10px 20px",
          cursor: "pointer",
        }}
      >
        ← Back to Book List
      </button>
    </div>
  );
}
