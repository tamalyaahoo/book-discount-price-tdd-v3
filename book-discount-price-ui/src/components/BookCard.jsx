export default function BookCard({ book, quantity, increment, decrement }) {
    return (
      <div
        style={{
          border: "2px solid #ccc",
          padding: "10px",
          borderRadius: "10px",
          marginBottom: "10px",
        }}
      >
       <h3>{book.title}</h3>
       <p>Author: {book.author}</p>
       <p>Price : ₹{book.price}</p>
  
        <div style={{ marginTop: "5px" }}>
          <button onClick={() => decrement(book.title)}>-</button>
          <span style={{ margin: "0 10px" }}>{quantity || 0}</span>
          <button onClick={() => increment(book.title)}>+</button>
        </div>
      </div>
    );
  }
  