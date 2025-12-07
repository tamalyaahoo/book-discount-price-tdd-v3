import { setupServer } from "msw/node";
import { http, HttpResponse } from "msw";

export const server = setupServer(

  http.get("/api/books/getbooks", () => {
    return HttpResponse.json([
      { id: 1, title: "Clean Code", author: "Robert Martin", year: 2008, price: 50 },
      { id: 2, title: "The Clean Coder", author: "Robert Martin", year: 2011, price: 50 },
    ]);
  }),

  http.post("/api/books/price/calculate", async ({ request }) => {
    const body = await request.json();
    return HttpResponse.json({
      bookList: body.bookList,
      totalPrice: 100,
      discountPrice: 90,
    });
  })
);
