import React, { useState, useEffect } from 'react';
import axios from 'axios';

const TicketManager = () => {
    const [tickets, setTickets] = useState([]);
    const [eventName, setEventName] = useState('');
    const [loading, setLoading] = useState(false);
    const [actionLoading, setActionLoading] = useState(false);
    const [error, setError] = useState(null);

    const API_BASE_URL = 'http://localhost:8080/api/tickets';

    // Fetch all tickets
    const fetchTickets = async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.get(API_BASE_URL);
            setTickets(response.data);
        } catch (err) {
            console.error('Error fetching tickets:', err);
            setError('Failed to fetch tickets. Please try again later.');
        } finally {
            setLoading(false);
        }
    };

    // Create a new ticket
    const createTicket = async () => {
        if (!eventName.trim()) return; // Prevent empty event name
        setActionLoading(true);
        setError(null);
        try {
            await axios.post(API_BASE_URL, null, { params: { eventName } });
            setEventName('');
            fetchTickets();
        } catch (err) {
            console.error('Error creating ticket:', err);
            setError('Failed to create a ticket. Please try again.');
        } finally {
            setActionLoading(false);
        }
    };

    // Sell a ticket
    const sellTicket = async (id) => {
        setActionLoading(true);
        setError(null);
        try {
            await axios.post(`${API_BASE_URL}/${id}/sell`);
            fetchTickets();
        } catch (err) {
            console.error(`Error selling ticket with ID ${id}:`, err);
            setError(`Failed to sell ticket ID ${id}. Please try again.`);
        } finally {
            setActionLoading(false);
        }
    };

    useEffect(() => {
        fetchTickets();
    }, []);

    return (
        <div style={{ padding: '20px', fontFamily: 'Arial, sans-serif' }}>
            <h2>Ticket Manager</h2>
            <div style={{ marginBottom: '20px' }}>
                <input
                    type="text"
                    value={eventName}
                    onChange={(e) => setEventName(e.target.value)}
                    placeholder="Enter event name"
                    style={{
                        padding: '8px',
                        marginRight: '10px',
                        fontSize: '16px',
                        width: '300px',
                    }}
                />
                <button
                    onClick={createTicket}
                    disabled={!eventName.trim() || actionLoading}
                    style={{
                        padding: '10px 20px',
                        fontSize: '16px',
                        backgroundColor: eventName.trim() ? '#007BFF' : '#CCC',
                        color: 'white',
                        border: 'none',
                        cursor: eventName.trim() ? 'pointer' : 'not-allowed',
                    }}
                >
                    {actionLoading ? 'Creating...' : 'Create Ticket'}
                </button>
            </div>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {loading ? (
                <p>Loading...</p>
            ) : (
                <>
                    {tickets.length === 0 ? (
                        <p>No tickets available. Add tickets to see them here.</p>
                    ) : (
                        <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                            <thead>
                                <tr>
                                    <th style={{ border: '1px solid #DDD', padding: '10px' }}>ID</th>
                                    <th style={{ border: '1px solid #DDD', padding: '10px' }}>Event Name</th>
                                    <th style={{ border: '1px solid #DDD', padding: '10px' }}>Status</th>
                                    <th style={{ border: '1px solid #DDD', padding: '10px' }}>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {tickets.map((ticket) => (
                                    <tr key={ticket.id}>
                                        <td style={{ border: '1px solid #DDD', padding: '10px' }}>{ticket.id}</td>
                                        <td style={{ border: '1px solid #DDD', padding: '10px' }}>{ticket.eventName}</td>
                                        <td style={{ border: '1px solid #DDD', padding: '10px' }}>
                                            {ticket.sold ? 'Sold' : 'Available'}
                                        </td>
                                        <td style={{ border: '1px solid #DDD', padding: '10px' }}>
                                            {!ticket.sold && (
                                                <button
                                                    onClick={() => sellTicket(ticket.id)}
                                                    disabled={actionLoading}
                                                    style={{
                                                        padding: '8px 16px',
                                                        backgroundColor: '#28A745',
                                                        color: 'white',
                                                        border: 'none',
                                                        cursor: 'pointer',
                                                    }}
                                                >
                                                    {actionLoading ? 'Processing...' : 'Sell'}
                                                </button>
                                            )}
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    )}
                </>
            )}
        </div>
    );
};

export default TicketManager;
