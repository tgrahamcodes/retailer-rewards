import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import PropTypes from 'prop-types'

// This component is to display each invoice in a card format, showing the items, prices, total, and points earned.
function InvoiceCard({ invoice }) {
	return (
		<div className="bg-white shadow-md rounded-2xl p-6 flex flex-col">
			<div className="flex justify-between items-center mb-4">
				<div>
					<h3 className="font-bold text-gray-800">TechNest Electronics</h3>
					<p className="text-xs text-gray-400">{invoice.date}</p>
				</div>
				<span className="bg-green-100 text-green-700 text-xs font-semibold px-3 py-1 rounded-full">
					PAID
				</span>
			</div>
			<table className="w-full text-sm mb-4">
				<thead>
					<tr className="border-b border-gray-200 text-gray-400 uppercase text-xs">
						<th className="text-left pb-2">Item</th>
						<th className="text-right pb-2">Price</th>
					</tr>
				</thead>
				<tbody>
					{invoice.products.map((p, i) => (
						<tr key={i} className="border-b border-gray-100">
							<td className="py-1.5 text-gray-700">{p.name}</td>
							<td className="py-1.5 text-right text-gray-700">
								${p.price.toFixed(2)}
							</td>
						</tr>
					))}
				</tbody>
				<tfoot>
					<tr className="font-bold text-gray-800">
						<td className="pt-3">Total</td>
						<td className="pt-3 text-right text-green-600">
							${invoice.total.toFixed(2)}
						</td>
					</tr>
				</tfoot>
			</table>
			<div className="mt-auto pt-3 border-t border-gray-100 flex justify-between text-xs text-gray-500">
				<span>Points earned</span>
				<span className="font-semibold text-green-600">{invoice.pointsEarned} pts</span>
			</div>
		</div>
	)
}

// This function is to call the API and gt the receipts from each customer.
function CustomerReceipts() {
	const { id } = useParams()
	const [summary, setSummary] = useState(null)
	const [error, setError] = useState(null)

	// Fetch the receipts from the API by customer ID, and handle loading and errors.
	useEffect(() => {
		fetch(`/api/customer/${id}/invoices`)
			.then((res) => res.json())
			.then((data) => {
				if (!data || data.months.length === 0)
					setError(`No receipts found for customer #${id}`)
				else setSummary(data)
			})
			.catch(() => setError('Failed to load receipts'))
	}, [id])

	if (error) return <p className="text-center mt-10 text-red-500">{error}</p>
	if (!summary) return <p className="text-center mt-10 text-gray-400">Loading...</p>

	return (
		<div className="max-w-5xl mx-auto">
			<div className="mb-8">
				<h2 className="text-2xl font-bold text-gray-800 mb-4">
					Receipts for <span className="text-green-600">{summary.customerName}</span>
				</h2>
				<div className="bg-white rounded-2xl shadow-md p-6 flex gap-12">
					<div>
						<p className="text-xs text-gray-400 uppercase mb-1">Total Spent</p>
						<p className="text-2xl font-bold text-gray-800">
							${summary.totalSpent.toFixed(2)}
						</p>
					</div>
					<div className="ml-auto">
						<p className="text-xs text-gray-400 uppercase mb-1">Total Rewards</p>
						<p className="text-2xl font-bold text-green-600">
							{summary.totalRewards} pts
						</p>
					</div>
				</div>
			</div>

			{summary.months.map((month, i) => (
				<div key={i} className="mb-10">
					<div className="flex items-center justify-between mb-4">
						<h3 className="text-lg font-semibold text-gray-700">{month.month}</h3>
						<div className="flex gap-6 text-sm text-gray-500">
							<span>
								Spent:{' '}
								<span className="font-semibold text-gray-800">
									${month.totalSpent.toFixed(2)}
								</span>
							</span>
							<span>
								Points:{' '}
								<span className="font-semibold text-green-600">
									{month.pointsEarned} pts
								</span>
							</span>
						</div>
					</div>
					<div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
						{month.invoices.map((invoice, j) => (
							<InvoiceCard key={j} invoice={invoice} />
						))}
					</div>
				</div>
			))}
		</div>
	)
}

export default CustomerReceipts
