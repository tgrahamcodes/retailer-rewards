import { Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Home from "./pages/Home";
import Search from "./pages/Search";
import CustomerReceipts from "./pages/CustomerReceipts";

function App() {
  return (
    <div className="min-h-screen bg-gray-100">
      <Navbar />
      <div className="p-6">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/search" element={<Search />} />
          <Route path="/customer/:id" element={<CustomerReceipts />} />
        </Routes>
      </div>
    </div>
  );
}

export default App;