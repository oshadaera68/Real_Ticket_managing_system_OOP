import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ConfigurationManager = () => {
    const [config, setConfig] = useState({
        totalTickets: 0,
        ticketReleaseRate: 0,
        customerRetrievalRate: 0,
        maxTicketCapacity: 0
    });

    // Fetch configuration
    const fetchConfig = async () => {
        try {
            const response = await axios.get('/api/tickets/configuration');
            setConfig(response.data);
        } catch (error) {
            console.error('Error fetching configuration:', error);
        }
    };

    // Update configuration
    const updateConfig = async () => {
        try {
            await axios.put('/api/tickets/configuration', config);
            alert('Configuration updated!');
        } catch (error) {
            console.error('Error updating configuration:', error);
        }
    };

    useEffect(() => {
        fetchConfig();
    }, []);

    return (
        <div>
            <h2>Configuration Manager</h2>
            <div>
                <label>
                    Total Tickets:
                    <input
                        type="number"
                        value={config.totalTickets}
                        onChange={(e) =>
                            setConfig({ ...config, totalTickets: parseInt(e.target.value) })
                        }
                    />
                </label>
                <br />
                <label>
                    Ticket Release Rate:
                    <input
                        type="number"
                        value={config.ticketReleaseRate}
                        onChange={(e) =>
                            setConfig({ ...config, ticketReleaseRate: parseInt(e.target.value) })
                        }
                    />
                </label>
                <br />
                <label>
                    Customer Retrieval Rate:
                    <input
                        type="number"
                        value={config.customerRetrievalRate}
                        onChange={(e) =>
                            setConfig({
                                ...config,
                                customerRetrievalRate: parseInt(e.target.value)
                            })
                        }
                    />
                </label>
                <br />
                <label>
                    Max Ticket Capacity:
                    <input
                        type="number"
                        value={config.maxTicketCapacity}
                        onChange={(e) =>
                            setConfig({ ...config, maxTicketCapacity: parseInt(e.target.value) })
                        }
                    />
                </label>
                <br />
                <button onClick={updateConfig}>Save Configuration</button>
            </div>
        </div>
    );
};

export default ConfigurationManager;