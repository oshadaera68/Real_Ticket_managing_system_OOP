import React, { useEffect, useState } from "react";
import { Container, Typography, Paper, Box } from "@mui/material";
import ConfigurationForm from "./components/ConfigurationForm";
import ControlPanel from "./components/ControlPanel";
import TicketDisplay from "./components/TicketDisplay";
import LogDisplay from "./components/LogDisplay";

const App = () => {
  // use states
  const [status, setStatus] = useState<string>("stopped");
  const [config, setConfig] = useState<string>("");
  const [tickets, setTickets] = useState<string[]>([]);
  const [logs, setLogs] = useState<string[]>([]);

  useEffect(() => {
    const ticketInterval = setInterval(() => {
      if (status === "Running") {
        const newTicket = `Ticket ${tickets.length + 1}`;
        setTickets((prev) => [...prev, newTicket]);
        setLogs((prev) => [...prev, `New Ticket added: ${newTicket}`]);
      }
    }, 3000);
    return () => clearInterval(ticketInterval);
  }, [status, tickets]);

  const handleStart = () => {
    setStatus("Running");
    setLogs((prev) => [...prev, "System started."]);
  };

  const handleStop = () => {
    setStatus("Stopped");
    setLogs((prev) => [...prev, "System stopped."]);
  };

  const handleConfigChange = (value: string) => {
    setConfig(value);
    setLogs((prev) => [...prev, `Configuration updated: ${value}`]);
  };
  return (
    <Container maxWidth="md">
      <Paper elevation={3} sx={{ p: 4, mt: 4 }}>
        <Typography variant="h4" gutterBottom>
          System Status:{" "}
          <span style={{ color: status === "Running" ? "green" : "red" }}>
            {status}
          </span>
        </Typography>
        <ConfigurationForm
          config={config}
          onConfigChange={handleConfigChange}
        />
        <ControlPanel onStart={handleStart} onStop={handleStop} />
        <Box sx={{ mt: 2 }}>
          <TicketDisplay tickets={tickets} />
          <LogDisplay logs={logs} />
        </Box>
      </Paper>
    </Container>
  );
};

export default App;
