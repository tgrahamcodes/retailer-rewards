import { render, screen } from '@testing-library/react'
import { MemoryRouter } from 'react-router-dom'
import Home from '../pages/Home'

const renderHome = () => {
	return render(
		<MemoryRouter>
			<Home />
		</MemoryRouter>
	)
}

// =========================================================================
// Test that the page content is rendered correctly
// =========================================================================
test('renders welcome heading', () => {
	renderHome()
	expect(screen.getByText('Welcome to RetailerRewards')).toBeInTheDocument()
})

test('renders description text', () => {
	renderHome()
	expect(
		screen.getByText('Enter a customer number to view their receipt history.')
	).toBeInTheDocument()
})

// =========================================================================
// Test that the search link is rendered and points to the correct path
// =========================================================================
test('renders search link', () => {
	renderHome()
	const link = screen.getByRole('link', { name: 'Search Customers' })
	expect(link).toBeInTheDocument()
})

test('search link points to /search', () => {
	renderHome()
	const link = screen.getByRole('link', { name: 'Search Customers' })
	expect(link).toHaveAttribute('href', '/search')
})
