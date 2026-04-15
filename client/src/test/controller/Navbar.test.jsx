import { render, screen } from '@testing-library/react'
import { MemoryRouter } from 'react-router-dom'
import Navbar from '../../components/Navbar'

const renderNavbar = () => {
	return render(
		<MemoryRouter>
			<Navbar />
		</MemoryRouter>
	)
}

// =========================================================================
// TEst that the navbar renders the store name and the home link.
// =========================================================================
test('renders brand name', () => {
	renderNavbar()
	expect(screen.getByText('RetailerRewards')).toBeInTheDocument()
})

test('renders home link', () => {
	renderNavbar()
	expect(screen.getByText('Home')).toBeInTheDocument()
})

// =========================================================================
// Test the navigation and that the links point to the correct paths
// =========================================================================
test('brand link points to /', () => {
	renderNavbar()
	const links = screen.getAllByRole('link')
	expect(links[0]).toHaveAttribute('href', '/')
})

test('home link points to /', () => {
	renderNavbar()
	const links = screen.getAllByRole('link')
	expect(links[1]).toHaveAttribute('href', '/')
})

test('renders two links', () => {
	renderNavbar()
	const links = screen.getAllByRole('link')
	expect(links).toHaveLength(2)
})
