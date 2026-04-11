import { useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { useEffect } from "react";

function Search() {
  const [query, setQuery] = useState("");
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  useEffect(() => {
    const id = searchParams.get("id");
    if (id) navigate(`/customer/${id}`);
  }, [searchParams, navigate]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (query.trim()) navigate(`/customer/${query.trim()}`);
  };

  return (
    <div className="flex flex-col items-center mt-24">
      <h2 className="text-2xl font-bold text-gray-800 mb-6">Customer Search</h2>
      <form onSubmit={handleSubmit} className="flex gap-3">
        <input
          type="text"
          placeholder="Enter customer number..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          className="border border-gray-300 rounded-xl px-4 py-2 w-64 focus:outline-none focus:ring-2 focus:ring-green-400"
        />
        <button
          type="submit"
          className="bg-green-600 text-white px-5 py-2 rounded-xl hover:bg-green-700 transition"
        >
          Search
        </button>
      </form>
      <p className="mt-16 text-xs italic hover:text-green-600 transition"> Customer id that exist are: 123, 456.</p>
    </div>
  );
}

export default Search;