import React, { useState } from "react";
import './configuration.css';
import axios from 'axios';

interface ConfigurationFormProps {
  onSubmit: (config: { [key: string]: string }) => void;
}

const ConfigurationForm: React.FC<ConfigurationFormProps> = ({ onSubmit }) => {
  const [config, setConfig] = useState<{ [key: string]: string }>({});

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setConfig({ ...config, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      // Send the config data to the backend using a POST request with axios
      const response = await axios.post('http://localhost:8080/api/initialize', config);

      if (response.status === 200) {
        // Handle success
        onSubmit(config);
        alert('Configuration saved successfully!');
      } else {
        alert('Failed to save configuration');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('An error occurred');
    }
  };

  return (
    <form className="configuration-form" onSubmit={handleSubmit}>
      <h2 className="form-title">Configuration</h2>
      <label className="form-label">
        Max Ticket Capacity:
        <input className="form-input" name="setting1" onChange={handleChange} />
      </label>
      <br />
      <label className="form-label">
        Total Tickets:
        <input className="form-input" name="setting2" onChange={handleChange} />
      </label>
      <br />
      <label className="form-label">
        Ticket Release Rate:
        <input className="form-input" name="setting3" onChange={handleChange} />
      </label>
      <br />
      <label className="form-label">
        Customer Retrieval Rate:
        <input className="form-input" name="setting4" onChange={handleChange} />
      </label>
      <br />
      <button className="form-button" type="submit">Submit</button>
    </form>
  );
};

export default ConfigurationForm;
