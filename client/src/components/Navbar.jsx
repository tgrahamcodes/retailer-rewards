import { Link } from 'react-router-dom';

function Navbar() {
  return (
    <nav className="bg-white shadow-md px-8 py-4 flex items-center justify-between">
      <Link to="/" className="text-xl font-bold text-green-600 tracking-tight">
        RetailerRewards
      </Link>
      <Link to="/" className="text-xl font-bold text-green-600 tracking-tight">
        Home
      </Link>
    </nav>
  );
}

export default Navbar;
