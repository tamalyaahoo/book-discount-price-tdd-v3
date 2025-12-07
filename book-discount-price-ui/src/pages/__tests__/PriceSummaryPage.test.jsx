import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import PriceSummaryPage from "../PriceSummaryPage";
import { vi } from "vitest";

// Mock the API call
vi.mock("../../api/bookApi", () => ({
  calculatePrice: vi.fn().mockResolvedValue({
    bookList: [{ title: "Clean Code", quantity: 1 }],
    totalPrice: 100,
    discountPrice: 80,
  }),
}));

test("shows price summary", async () => {
  const mockBasketState = {
    basket: { "Clean Code": 1 },
  };

  render(
    <MemoryRouter
      initialEntries={[{ pathname: "/summary", state: mockBasketState }]}
    >
      <PriceSummaryPage />
    </MemoryRouter>
  );

  // While loading
  expect(screen.getByText("Calculating price...")).toBeInTheDocument();

  // After mock API resolves
  expect(
    await screen.findByText("Clean Code — Qty: 1")
  ).toBeInTheDocument();

  expect(
    await screen.findByText("Total Price: $100")
  ).toBeInTheDocument();

  expect(
    await screen.findByText("Discounted Price: $80")
  ).toBeInTheDocument();
});
