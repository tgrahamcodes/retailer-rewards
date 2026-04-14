import { Link } from 'react-router-dom'

function Home() {
    return (
        <div className="flex flex-col items-center justify-center mt-24 text-center">
            <h1 className="text-4xl font-bold text-gray-800 mb-4">Welcome to RetailerRewards</h1>
            <p className="text-gray-500 mb-8">
                Enter a customer number to view their receipt history.
            </p>
            <Link
                to="/search"
                className="bg-green-600 text-white px-6 py-3 rounded-xl text-sm font-medium hover:bg-green-700 transition"
            >
                Search Customers
            </Link>
        </div>
    )
}

export default Home
