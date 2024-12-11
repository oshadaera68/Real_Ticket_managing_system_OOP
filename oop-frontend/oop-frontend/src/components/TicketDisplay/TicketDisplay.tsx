import React, { useEffect, useState } from 'react';
import './ticketdisplay.css';

interface TicketDisplayProps {
    tickets: number;
  }
  
  const TicketDisplay: React.FC<TicketDisplayProps> = () => {
    const [tickets, setTickets] = useState<number>(0);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
  
    useEffect(() => {
      const fetchTickets = async () => {
        try {
          const response = await fetch('http://localhost:8080/api/tickets');
          if (!response.ok) {
            throw new Error(`Error: ${response.status}`);
          }
          const data = await response.json();
          setTickets(data.tickets);
          setLoading(false);
        } catch (err) {
          setError(err.message);
          setLoading(false);
        }
      };
  
      fetchTickets();
    }, []); // Empty dependency array ensures this runs only once when the component mounts
  
    if (loading) return <p>Loading tickets...</p>;
    if (error) return <p>Error: {error}</p>;
  
    return (
      <div className="ticket-display">
        <h2 className="ticket-display-title">Tickets Available</h2>
        <p className="ticket-count">{tickets}</p>
      </div>
    );
  };

export default TicketDisplay;