export default function BasketSummary({ basket = {} }) {
    const items = Object.entries(basket);
  
    if (items.length === 0) return <p>No books selected.</p>;
  
    return (
      <ul>
        {items.map(([title, qty]) => (
          <li key={title}>
            <b>{title} — Qty: {qty}</b>
          </li>
        ))}
      </ul>
    );
  }
  