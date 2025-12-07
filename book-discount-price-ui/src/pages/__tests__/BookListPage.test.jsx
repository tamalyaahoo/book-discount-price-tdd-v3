import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import BookListPage from "../BookListPage";

test("loads book list", async () => {
  render(
    <MemoryRouter>
      <BookListPage />
    </MemoryRouter>
  );

  expect(await screen.findByText("Clean Code")).toBeInTheDocument();
});
