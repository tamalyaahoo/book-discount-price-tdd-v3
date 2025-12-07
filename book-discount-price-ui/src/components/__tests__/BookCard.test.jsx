import { render, screen, fireEvent } from "@testing-library/react";
import BookCard from "../BookCard";
import { vi } from "vitest";

describe("BookCard Component", () => {
  test("renders book details and handles increment/decrement", () => {
    const mockBook = {
      title: "Clean Code",
      author: "Robert Martin",
      price: 50,
    };

    const increment = vi.fn();
    const decrement = vi.fn();

    render(
      <BookCard
        book={mockBook}
        quantity={1}
        increment={increment}
        decrement={decrement}
      />
    );

    // Validate displayed text
    expect(screen.getByText("Clean Code")).toBeInTheDocument();
    expect(screen.getByText("Author: Robert Martin")).toBeInTheDocument();
    expect(screen.getByText("Price : ₹50")).toBeInTheDocument();

    // Click increment
    fireEvent.click(screen.getByText("+"));
    expect(increment).toHaveBeenCalledTimes(1);
    expect(increment).toHaveBeenCalledWith("Clean Code");

    // Click decrement
    fireEvent.click(screen.getByText("-"));
    expect(decrement).toHaveBeenCalledTimes(1);
    expect(decrement).toHaveBeenCalledWith("Clean Code");
  });
});
