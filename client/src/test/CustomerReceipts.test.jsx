import { render, screen, waitFor, within } from '@testing-library/react'
import { MemoryRouter, Route, Routes } from 'react-router-dom'
import CustomerReceipts from '../pages/CustomerReceipts'

//========================================================================
// Mock data for API call, so it returns a successful response
//========================================================================
const mockSummary = {
	customerName: 'Jane Doe',
	totalSpent: 199.98,
	totalRewards: 149,
	months: [
		{
			month: 'January 2026',
			totalSpent: 199.98,
			pointsEarned: 149,
			invoices: [
				{
					date: '2026-01-08',
					total: 199.98,
					pointsEarned: 149,
					products: [
						{ name: 'Wireless Earbuds', price: 79.99 },
						{ name: 'Phone Case', price: 19.99 },
					],
				},
			],
		},
	],
}

// ========================================================================
// Helper function to render component with router context
// ========================================================================
const renderWithRouter = (id = '123') => {
	return render(
		<MemoryRouter initialEntries={[`/customer/${id}`]}>
			<Routes>
				<Route path="/customer/:id" element={<CustomerReceipts />} />
			</Routes>
		</MemoryRouter>
	)
}

// ========================================================================
// Setup and teardown, reset fetch mock before each test
// ========================================================================
beforeEach(() => {
	vi.stubGlobal('fetch', vi.fn())
})

afterEach(() => {
	vi.unstubAllGlobals()
})

// =========================================================================
// Test the loading state before the API call resolves
// =========================================================================
test('shows loading state initially', () => {
	global.fetch = vi.fn(() => new Promise(() => {})) // never resolves
	renderWithRouter()
	expect(screen.getByText('Loading...')).toBeInTheDocument()
})

// =========================================================================
// Test each success for the API call and that the data is rendered correctly
// =========================================================================
test('renders customer name after successful fetch', async () => {
	global.fetch = vi.fn(() => Promise.resolve({ json: () => Promise.resolve(mockSummary) }))
	renderWithRouter()
	await waitFor(() => {
		expect(screen.getByText('Jane Doe')).toBeInTheDocument()
	})
})

test('renders total spent and total rewards', async () => {
	global.fetch = vi.fn(() => Promise.resolve({ json: () => Promise.resolve(mockSummary) }))
	renderWithRouter()
	await waitFor(() => {
		const summary = screen.getByTestId('summary-card')
		expect(within(summary).getByText(/\$199\.98/)).toBeInTheDocument()
		expect(within(summary).getByText(/149\s*pts/)).toBeInTheDocument()
	})
}) -
	test('renders invoice card with product names', async () => {
		global.fetch = vi.fn(() => Promise.resolve({ json: () => Promise.resolve(mockSummary) }))
		renderWithRouter()
		await waitFor(() => {
			expect(screen.getByText('Wireless Earbuds')).toBeInTheDocument()
			expect(screen.getByText('Phone Case')).toBeInTheDocument()
		})
	})

// =========================================================================
// Test what happens when there are Errors
// =========================================================================
test('shows error when no receipts found', async () => {
	global.fetch = vi.fn(() => Promise.resolve({ json: () => Promise.resolve({ months: [] }) }))
	renderWithRouter('999')
	await waitFor(() => {
		expect(screen.getByText('No receipts found for customer #999')).toBeInTheDocument()
	})
})
