import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { MemoryRouter, Routes, Route } from 'react-router-dom'
import Search from '../pages/Search'

const renderSearch = (initialPath = '/search') => {
	return render(
		<MemoryRouter initialEntries={[initialPath]}>
			<Routes>
				<Route path="/search" element={<Search />} />
			</Routes>
		</MemoryRouter>
	)
}

// =========================================================================
// Tests that the content is rendered correctly
// =========================================================================
test('renders search heading', () => {
	renderSearch()
	expect(screen.getByText('Customer Search')).toBeInTheDocument()
})

test('renders input field', () => {
	renderSearch()
	expect(screen.getByPlaceholderText('Enter customer number...')).toBeInTheDocument()
})

test('renders search button', () => {
	renderSearch()
	expect(screen.getByRole('button', { name: 'Search' })).toBeInTheDocument()
})

test('renders customer id hint', () => {
	renderSearch()
	expect(screen.getByText(/123, 456/)).toBeInTheDocument()
})

// =========================================================================
// Test that the input field updates when changed and that invalid submits do not navigate
// =========================================================================
test('updates input value as user types', async () => {
	renderSearch()
	const input = screen.getByPlaceholderText('Enter customer number...')
	await userEvent.type(input, '123')
	expect(input).toHaveValue('123')
})

test('does not navigate when input is empty', async () => {
	renderSearch()
	const button = screen.getByRole('button', { name: 'Search' })
	await userEvent.click(button)
	expect(screen.getByText('Customer Search')).toBeInTheDocument()
})

test('does not navigate when input is only whitespace', async () => {
	renderSearch()
	const input = screen.getByPlaceholderText('Enter customer number...')
	await userEvent.type(input, '   ')
	await userEvent.click(screen.getByRole('button', { name: 'Search' }))
	expect(screen.getByText('Customer Search')).toBeInTheDocument()
})

// =========================================================================
// Test the navigation wheen a valid ID with the URL search param
// =========================================================================
test('navigates to customer page on valid submit', async () => {
	render(
		<MemoryRouter initialEntries={['/search']}>
			<Routes>
				<Route path="/search" element={<Search />} />
				<Route path="/customer/:id" element={<div>Customer Page</div>} />
			</Routes>
		</MemoryRouter>
	)
	const input = screen.getByPlaceholderText('Enter customer number...')
	await userEvent.type(input, '123')
	await userEvent.click(screen.getByRole('button', { name: 'Search' }))
	expect(screen.getByText('Customer Page')).toBeInTheDocument()
})

test('navigates via url search param ?id=', async () => {
	render(
		<MemoryRouter initialEntries={['/search?id=456']}>
			<Routes>
				<Route path="/search" element={<Search />} />
				<Route path="/customer/:id" element={<div>Customer Page</div>} />
			</Routes>
		</MemoryRouter>
	)
	expect(screen.getByText('Customer Page')).toBeInTheDocument()
})
