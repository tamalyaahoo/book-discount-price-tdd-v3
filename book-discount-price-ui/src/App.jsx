import { BrowserRouter, Routes, Route } from "react-router-dom";
import BookListPage from "./pages/BookListPage";
import PriceSummaryPage from "./pages/PriceSummaryPage";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<BookListPage />} />
        <Route path="/summary" element={<PriceSummaryPage />} />
      </Routes>
    </BrowserRouter>
  );
}
