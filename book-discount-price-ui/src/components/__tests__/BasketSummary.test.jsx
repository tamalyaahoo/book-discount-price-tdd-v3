import { render, screen } from "@testing-library/react";
import BasketSummary from "../BasketSummary";

test("shows list of books when basket has items", () => {
  const basket = { "Clean Code": 2, "The Clean Coder": 1 };

  render(<BasketSummary basket={basket} />);

  expect(screen.getByText("Clean Code — Qty: 2")).toBeInTheDocument();
  expect(screen.getByText("The Clean Coder — Qty: 1")).toBeInTheDocument();
});

test("shows fallback message when basket is empty", () => {
  render(<BasketSummary basket={{}} />);

  expect(screen.getByText("No books selected.")).toBeInTheDocument();
});
