import React from 'react';
import './logdisplay.css';

interface LogDisplayProps {
    logs: string[];
  }

const LogDisplay: React.FC<LogDisplayProps> = ({ logs }) => {
  return (
    <div className="log-display">
      <h2 className="log-display-title">Logs</h2>
      <ul className="log-list">
        {logs.map((log, index) => (
          <li className="log-item" key={index}>{log}</li>
        ))}
      </ul>
    </div>
  );
}

export default LogDisplay;