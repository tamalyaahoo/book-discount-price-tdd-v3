import { fetchBooks, calculatePrice } from "../bookApi";
import { describe, it, expect } from "vitest";

describe("bookApi", () => {
  it("fetchBooks returns books", async () => {
    const books = await fetchBooks();
    expect(books.length).toBe(2);
    expect(books[0].title).toBe("Clean Code");
  });

  it("calculatePrice returns expected output", async () => {
    const result = await calculatePrice([{ title: "Clean Code", quantity: 2 }]);
    expect(result.totalPrice).toBe(100);
    expect(result.discountPrice).toBe(90);
  });
});
