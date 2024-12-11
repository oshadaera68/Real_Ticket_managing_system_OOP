import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ConfigurationForm from './components/Configuration/ConfigurationForm';
import TicketDisplay from './components/TicketDisplay/TicketDisplay';
import ControlPanel from './components/ControlPanel/ControlPanel';
import LogDisplay from './components/LogDisplay/LogDisplay';

const App: React.FC = () => {
  const [tickets, setTickets] = useState<number>(0);
  const [logs, setLogs] = useState<string[]>([]);
  const [isRunning, setIsRunning] = useState<boolean>(false);

  // Simulate fetching data via polling
  useEffect(() => {
    const interval = setInterval(() => {
      axios.get('/api/tickets').then((response) => setTickets(response.data));
    }, 5000);

    return () => clearInterval(interval);
  }, []);

  const handleStart = () => {
    setIsRunning(true);
    setLogs((prev) => [...prev, 'System started.']);
  };

  const handleStop = () => {
    setIsRunning(false);
    setLogs((prev) => [...prev, 'System stopped.']);
  };

  const handleConfigSubmit = (config: { [key: string]: string }) => {
    console.log('Configuration submitted:', config);
    setLogs((prev) => [...prev, 'Configuration updated.']);
  };

  return (
    <div>
      <h1>Ticket System</h1>
      <TicketDisplay tickets={tickets} />
      <ControlPanel isRunning={isRunning} onStart={handleStart} onStop={handleStop} />
      <ConfigurationForm onSubmit={handleConfigSubmit} />
      <LogDisplay logs={logs} />
    </div>
  );
};

export default App;
