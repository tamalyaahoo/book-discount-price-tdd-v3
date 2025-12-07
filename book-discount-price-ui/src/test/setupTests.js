import { expect, beforeAll, afterEach, afterAll } from "vitest";
import { server } from "./server";
import "@testing-library/jest-dom";

// Start MSW before all tests
beforeAll(() => {
  server.listen({ onUnhandledRequest: "error" });
});

// Reset handlers after each test
afterEach(() => {
  server.resetHandlers();
});

// Cleanup once tests finish
afterAll(() => {
  server.close();
});
