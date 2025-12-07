const API_BASE = "/api/books";

export async function fetchBooks() {
  const response = await fetch(`${API_BASE}/getbooks`);
  if (!response.ok) throw new Error("Failed to fetch books");
  return response.json();
}

export async function calculatePrice(bookList) {
  const response = await fetch(`${API_BASE}/price/calculate`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ bookList }),
  });

  if (!response.ok) throw new Error("Failed to calculate price");

  return response.json();
}
