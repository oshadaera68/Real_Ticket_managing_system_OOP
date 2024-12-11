import React from 'react';
import './ControlPanel.css';

interface ControlPanelProps {
    isRunning: boolean;
    onStart: () => void;
    onStop: () => void;
  }

const ControlPanel: React.FC<ControlPanelProps> = ({ isRunning, onStart, onStop }) => {
  return (
    <div className="control-panel">
      <h2 className="control-panel-title">Control Panel</h2>
      <button className="control-button start-button" onClick={onStart} disabled={isRunning}>
        Start
      </button>
      <button className="control-button stop-button" onClick={onStop} disabled={!isRunning}>
        Stop
      </button>
    </div>
  );
}

export default ControlPanel;