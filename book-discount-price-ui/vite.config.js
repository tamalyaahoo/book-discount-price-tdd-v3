import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],

  server: {
    port: 5173,
    strictPort: true,

    // Enable CORS
    cors: {
      origin: "*",
      methods: ["GET", "POST", "PUT", "DELETE", "OPTIONS"],
      allowedHeaders: ["Content-Type", "Authorization"],
    },

    // API Proxy to Spring Boot backend
    proxy: {
      "/api": {
        target: "http://localhost:8081",
        changeOrigin: true,
        secure: false,
      },
    },
  },

  // Vitest test configuration
  test: {
    globals: true,
    environment: "jsdom",
    setupFiles: "./src/test/setupTests.js",

    // Produce terminal + HTML coverage reports
    coverage: {
      provider: "c8",
      reporter: ["text", "html"],
      reportsDirectory: "./coverage",
      exclude: [
        "node_modules/",
        "src/main.jsx",
        "src/App.jsx",
        "src/test/",
      ],
    },
  },

  resolve: {
    alias: {
      "@": "/src",
    },
  },
});
